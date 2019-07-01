package com.paul.memory.concurrency.core;

import com.paul.facade.MemoryConstant;
import com.paul.facade.PageData;
import com.paul.memory.concurrency.core.data.AbstractData;
import com.paul.memory.concurrency.dao.h2.bean.NodeInfo;
import com.paul.memory.concurrency.utils.ApplicationUtils;
import com.paul.memory.concurrency.utils.CommonUtils;
import com.paul.memory.concurrency.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:data processor上下文，包含一些公共信息
 * @Resource:
 */
public class ProcessorContext {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorContext.class);

    /**
     * 装饰器模式，如果需要的话可以在每个DataProcessor做增强
     */
    private static class DataProcessorWrapper implements DataProcessor {

        private DataProcessor dataProcessor;

        private int nodes;

        public DataProcessorWrapper(int nodes, DataProcessor dataProcessor) {
            this.dataProcessor = dataProcessor;
            this.nodes = nodes;
        }

        /**
         * 该方法可能被多个线程执行
         *
         * @param context
         * @param pageData
         * @param object
         * @return
         */
        @Override
        public List<AbstractData> doProcess(ProcessorContext context, PageData pageData, RabbitObject object) {
            //先根据条件生成对应的RabbitObject对象
            context.buildRabbitObject(this.dataProcessor.dealModule(), object);
            List<AbstractData> list = this.dataProcessor.doProcess(context, pageData, object);

            if (list == null) {
                logger.warn("module[{}]返回的数据为null，请关注检查", this.dataProcessor.dealModule());
                return null;
            }

            //当前节点开启了异步写入功能,不使用rabbitmq，直接推送到本节点的Container中
            if (context.asynWrite) {
                logger.debug("当前节点开启了异步写入CSV文件功能");
                for (AbstractData data : list) {
                    UniqueId uniqueId = new UniqueId(CommonUtils.generateUniqueId(data), this.dataProcessor.dealModule(), this.nodes);
                    logger.info("生成uniqueId[{}]", uniqueId);
//                    if (context.modules > 1) {
                        FinalDataContainer.putModulesData(uniqueId, data);
//                    } else {
//                        FinalDataContainer.putModuleData(uniqueId, data);
//                    }
                }

                AbstractData data = list.get(list.size() - 1);
                if(data.isCurrentNodeDone()){
                    NodeInfo nodeInfo = CommonUtils.getNodeInfo();
                    logger.debug("当前节点[{}]负责异步写数据，直接发送done消息",nodeInfo);
                    FinalDataContainer.singleModuleNodesDone(nodeInfo);
                }
            } else {
                //当前节点不是负责异步写入，所以需要将数据发送到MQ相应的队列中
                logger.debug("当前节点尚未开启异步写入文件，只需要计算相应模块数据，并写入MQ");
                RabbitTemplate rabbitTemplate = context.applicationUtils.getBean("rabbitTemplate");
                logger.info("本次向exchange[{}]-queue[{}](routingKey:{})发送消息[{}]条",
                        object.getExchange().getName(), object.getQueue().getName(), object.getBinding().getRoutingKey(), list.size());
                rabbitTemplate.convertAndSend(object.getExchange().getName(), object.getBinding().getRoutingKey(), list);

                //判断本批次最后一条数据是否是代表了这个processor处理完了当前节点的数据
                AbstractData data = list.get(list.size() - 1);
                if (data.isCurrentNodeDone()) {
                    logger.info("当前节点[{}]处理module[{}]完毕，发送done消息",dealModule(),CommonUtils.getNodeInfo());
                    rabbitTemplate.convertAndSend(MemoryConstant.DONE_EXCHANGE,MemoryConstant.DONE_BINDING,CommonUtils.getNodeInfo());
                }

            }

            return list;
        }

        @Override
        public String dealModule() {
            return this.dataProcessor.dealModule();
        }
    }

    //key为
//    private final Map<UniqueId, LinkedBlockingQueue<Object>> moduleData;

    private final static Map<String, DataProcessor> processorMap = new ConcurrentHashMap<>();

    private final boolean asynWrite;

    private ApplicationUtils applicationUtils;

    public void setApplicationUtils(ApplicationUtils applicationUtils) {
        this.applicationUtils = applicationUtils;
    }

    private final int modules;

    public ProcessorContext(int module, boolean asynWrite) {
        this.modules = module;
        this.asynWrite = asynWrite;
    }

    public static DataProcessor registerProcessor(String module, int nodes, DataProcessor dataProcessor) {
        DataProcessor wrapper = new DataProcessorWrapper(nodes, dataProcessor);
        ProcessorContext.processorMap.put(module, wrapper);
        return wrapper;
    }

    public static DataProcessor dataProcessor(String module) {
        return ProcessorContext.processorMap.get(module);
    }

    public static boolean initializedDone(String[] modules) {
        if (modules == null || modules.length == 0) {
            return false;
        }

        boolean done = true;
        for (String module : modules) {
            if (!processorMap.containsKey(module)) {
                return false;
            }
        }

        return done;
    }

    private void buildRabbitObject(String module, RabbitObject rabbitObject) {
        Exchange exchange = applicationUtils.getBean(module + "Exchange");
        ExceptionUtils.checkAndThrowIfNecessory(exchange);
        rabbitObject.setExchange(exchange);

        Queue queue = applicationUtils.getBean(module + MemoryConstant.RABBITMQ_QUEUE_PREFIX + rabbitObject.getBatch());
        ExceptionUtils.checkAndThrowIfNecessory(queue);
        rabbitObject.setQueue(queue);

        Binding binding = applicationUtils.getBean(module + MemoryConstant.RABBIT_BINDING_PREFIX + rabbitObject.getBatch());
        ExceptionUtils.checkAndThrowIfNecessory(binding);
        rabbitObject.setBinding(binding);
    }
//    public boolean pushData(UniqueId uniqueId, Object data) {
//        LinkedBlockingQueue<Object> queue = this.moduleData.get(uniqueId);
//        if (queue == null) {
//            queue = new LinkedBlockingQueue<>(1000);
//        }
//
//        try {
//            queue.put(data);
//            return true;
//        } catch (InterruptedException e) {
//            logger.error("等待key={}将数据[{}]放入队列失败", uniqueId, data);
//        }
//
//        return false;
//    }

}
