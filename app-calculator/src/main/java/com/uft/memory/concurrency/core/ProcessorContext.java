package com.uft.memory.concurrency.core;

import com.uft.facade.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static class DataProcessorWrapper implements DataProcessor {

        private DataProcessor dataProcessor;

        private int nodes;

        public DataProcessorWrapper(int nodes, DataProcessor dataProcessor) {
            this.dataProcessor = dataProcessor;
            this.nodes = nodes;
        }

        @Override
        public void doProcess(ProcessorContext context, PageData pageData) {
            this.dataProcessor.doProcess(context, pageData);
        }

        @Override
        public String dealModule() {
            return this.dataProcessor.dealModule();
        }
    }

    //key为
    private final Map<UniqueId, LinkedBlockingQueue<Object>> moduleData;

    private final static Map<String, DataProcessor> processorMap = new ConcurrentHashMap<>();

    public ProcessorContext(int module) {
        this.moduleData = new ConcurrentHashMap<>(module);
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
    public boolean pushData(UniqueId uniqueId, Object data) {
        LinkedBlockingQueue<Object> queue = this.moduleData.get(uniqueId);
        if (queue == null) {
            queue = new LinkedBlockingQueue<>(1000);
        }

        try {
            queue.put(data);
            return true;
        } catch (InterruptedException e) {
            logger.error("等待key={}将数据[{}]放入队列失败", uniqueId, data);
        }

        return false;
    }

}
