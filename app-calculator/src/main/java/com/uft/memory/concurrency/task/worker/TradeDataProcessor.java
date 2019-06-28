package com.uft.memory.concurrency.task.worker;

import com.uft.facade.MemoryConstant;
import com.uft.facade.PageData;
import com.uft.memory.concurrency.core.DataProcessor;
import com.uft.memory.concurrency.core.ProcessorContext;
import com.uft.memory.concurrency.core.RabbitObject;
import com.uft.memory.concurrency.core.data.AbstractData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:
 * @Resource:
 */
@Component
public class TradeDataProcessor implements DataProcessor {
    @Override
    public List<AbstractData> doProcess(ProcessorContext context, PageData pageData, RabbitObject rabbitObject) {
        return null;
    }

    @Override
    public String dealModule() {
        return MemoryConstant.MODULE_TRADE;
    }
}
