package com.paul.memory.concurrency.task;

import com.paul.memory.concurrency.core.ProcessorContext;
import com.paul.memory.concurrency.core.listen.ActionEvents;
import com.paul.memory.concurrency.core.listen.ListenEvent;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public class DealDataEvent implements ListenEvent {
    @Override
    public ActionEvents eventType() {
        return ActionEvents.DEAL_DATA;
    }

    @Override
    public ProcessorContext getProcessorContext() {
        return null;
    }
}
