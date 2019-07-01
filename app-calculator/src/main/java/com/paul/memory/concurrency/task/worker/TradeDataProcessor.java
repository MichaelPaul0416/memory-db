//package com.paul.memory.concurrency.task.worker;
//
//import com.paul.facade.MemoryConstant;
//import com.paul.facade.PageData;
//import com.paul.memory.concurrency.core.DataProcessor;
//import com.paul.memory.concurrency.core.ProcessorContext;
//import com.paul.memory.concurrency.core.RabbitObject;
//import com.paul.memory.concurrency.core.data.AbstractData;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @Author: wangqiang20995
// * @Date:2019/6/28
// * @Description:
// * @Resource:
// */
//@Component
//public class TradeDataProcessor implements DataProcessor {
//    @Override
//    public List<AbstractData> doProcess(ProcessorContext context, PageData pageData, RabbitObject rabbitObject) {
//        return null;
//    }
//
//    @Override
//    public String dealModule() {
//        return MemoryConstant.MODULE_TRADE;
//    }
//}
