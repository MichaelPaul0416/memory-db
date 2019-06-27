package com.uft.memory.concurrency.core;

import com.uft.facade.MemoryConstant;
import com.uft.facade.PageData;
import com.uft.memory.concurrency.core.data.AbstractData;
import com.uft.memory.concurrency.utils.ApplicationUtils;
import com.uft.memory.concurrency.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

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

        @Override
        public List<AbstractData> doProcess(ProcessorContext context, PageData pageData,RabbitObject object) {
            //先根据条件生成对应的RabbitObject对象
            context.buildRabbitObject(this.dataProcessor.dealModule(),object);
            List<AbstractData> list = this.dataProcessor.doProcess(context, pageData,object);

            //当前节点开启了异步写入功能,不使用rabbitmq，直接推送到本节点的Container中
            if(context.asynWrite){
                logger.debug("当前节点开启了异步写入CSV文件功能");
                for(AbstractData data : list) {
                    UniqueId uniqueId = new UniqueId(CommonUtils.generateUniqueId(data),this.dataProcessor.dealModule(),this.nodes);
                    logger.info("生成uniqueId[{}]",uniqueId);
                    FinalDataContainer.putModuleData(uniqueId,data);
                }
            }else {
                //当前节点不是负责异步写入，所以需要将数据发送到MQ相应的队列中
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

    private final boolean asynWrite ;

    private ApplicationUtils applicationUtils;

    public void setApplicationUtils(ApplicationUtils applicationUtils) {
        this.applicationUtils = applicationUtils;
    }

    public ProcessorContext(int module, boolean asynWrite) {
//        this.moduleData = new ConcurrentHashMap<>(module);
        this.asynWrite = asynWrite;
    }

    public static void registerProcessor(String module, int nodes, DataProcessor dataProcessor) {
        ProcessorContext.processorMap.put(module, new DataProcessorWrapper(nodes, dataProcessor));
    }

    public static DataProcessor dataProcessor(String module){
        return ProcessorContext.processorMap.get(module);
    }

    public static boolean initializedDone(String[] modules){
        if(modules == null || modules.length == 0){
            return false;
        }

        boolean done = true;
        for(String module : modules){
            if(!processorMap.containsKey(module)){
                return false;
            }
        }

        return done;
    }

    private void buildRabbitObject(String module,RabbitObject rabbitObject){
        Exchange exchange = applicationUtils.getBean(module + "Exchange");
        rabbitObject.setExchange(exchange);

        Queue queue = applicationUtils.getBean(module + MemoryConstant.RABBITMQ_QUEUE_PREFIX + rabbitObject.getBatch());
        rabbitObject.setQueue(queue);
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
