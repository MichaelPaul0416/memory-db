package com.uft.memory.concurrency.task;

import com.uft.facade.MemoryConstant;
import com.uft.facade.PageData;
import com.uft.memory.concurrency.config.TaskConfig;
import com.uft.memory.concurrency.core.DataProcessor;
import com.uft.memory.concurrency.core.ProcessorContext;
import com.uft.memory.concurrency.core.RabbitObject;
import com.uft.memory.concurrency.core.listen.TaskListenerRegistry;
import com.uft.memory.concurrency.utils.ApplicationUtils;
import com.uft.memory.concurrency.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
@Component
public class LoadDataStarter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskConfig taskConfig;

    @Autowired
    private ApplicationUtils applicationUtils;

    @Resource
    private ExecutorService asynThreadPool;

    @Autowired
    private TaskListenerRegistry listenerRegistry;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void startLoad() {
        //读取配置文件中需要进行数据同步的模块
        String[] modules = modules();

        if (modules == null) {
            return;
        }

        boolean write = "true".equals(taskConfig.getEnableAsynWrite());
        //构建数据收集器对象
        ProcessorContext context = prepareCollection(modules.length, write);

        //data需要实现根据节点动态的进行数据分配，如当前节点配置了什么module，并且module的数量是几个，动态计算一个module的一个processor需要查询多少数据
        //数据收集器分发给不同的processor[ProcessorContext+DataProcessor]，真正的数据查询需要放在processor中查询

        /**
         * 这里还需要考虑一个分布式计算的问题，也就是当一个module有多个jvm进程来计算它数据的时候，数据如何分片
         * 这一版先默认一个module只在一个jvm中计算处理
         */
        prepareProcessors(modules, context);

        //发送数据开始处理通知
        listenerRegistry.broadcastEvent(new DealDataEvent());

        if (write) {
            //当前jvm负责写入数据到文件中，那么开启异步写入数据到文件通知
            listenerRegistry.broadcastEvent(new AsynWriteFile());
        }

        //本次数据加载结束

    }

    private void prepareProcessors(String[] modules, ProcessorContext context) {

        //定时任务后序执行时，DataProcessor就不需要重新从ApplicationContext中获取，直接从ProcessorContext中获取
        if (ProcessorContext.initializedDone(modules)) {
            for (String module : modules) {
                DataProcessor dataProcessor = ProcessorContext.dataProcessor(module);

                if (dataProcessor == null) {
                    logger.error("module[{}]对应的DataProcessor已经不存在，出现异常", module);
                    throw new NullPointerException("module[" + module + "]对应的dataProcessor不存在，出现异常");
                }
                int number = CommonUtils.moduleNodes(module, taskConfig);
                asynStartModuleProcessor(context, number, dataProcessor);
            }

            return;
        }

        for (String module : modules) {
            //根据module获取对应的节点数量
            //根据module+节点数获取对应的记录批数

            int number = CommonUtils.moduleNodes(module, taskConfig);
            Set<DataProcessor> registerProcessors = applicationUtils.getBeansByType(DataProcessor.class);

            //找出当前的processor，然后根据当前模块的节点数量，进行并发操作
            for (DataProcessor processor : registerProcessors) {
                if (module.equals(processor.dealModule())) {//当前这个processor是处理这个module的
                    if (number == 0) {
                        logger.info("当前模块[{}]尚未配置处理的processor数量，不启用", module);
                        continue;
                    }

                    ProcessorContext.registerProcessor(module, number, processor);
                    asynStartModuleProcessor(context, number, processor);
                }
            }
        }
    }

    private void asynStartModuleProcessor(ProcessorContext context, int number, DataProcessor processor) {
        for (int i = 0; i < number; i++) {
            PageData pageData = new PageData(PageData.PAGE_SIZE * i, i + 1);
            int finalI = i;
            asynThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    RabbitObject rabbitObject = new RabbitObject();
                    rabbitObject.setBatch(finalI % MemoryConstant.QUEUE_WITH_PROCESSORS == 0 ?
                            finalI / MemoryConstant.QUEUE_WITH_PROCESSORS : finalI / MemoryConstant.QUEUE_WITH_PROCESSORS + 1);
                    processor.doProcess(context, pageData, rabbitObject);
                }
            });
        }
    }


    private ProcessorContext prepareCollection(int modules, boolean asynWrite) {
        ProcessorContext processorContext = new ProcessorContext(modules, asynWrite);
        processorContext.setApplicationUtils(applicationUtils);
        return processorContext;
    }


    private String[] modules() {
        String modelConfig = taskConfig.getModules();

        if (StringUtils.isEmpty(modelConfig)) {
            logger.warn("尚未配置memory.modules的值,不进行数据同步...");
            return null;
        }

        return modelConfig.split("\\|");
    }
}
