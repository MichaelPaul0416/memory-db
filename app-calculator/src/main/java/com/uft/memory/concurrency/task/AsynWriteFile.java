package com.uft.memory.concurrency.task;

import com.uft.memory.concurrency.core.ProcessorContext;
import com.uft.memory.concurrency.core.listen.ActionEvents;
import com.uft.memory.concurrency.core.listen.ListenEvent;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public class AsynWriteFile implements ListenEvent {
    @Override
    public ActionEvents eventType() {
        return ActionEvents.ASYNC_WRITE;
    }

    @Override
    public ProcessorContext getProcessorContext() {
        return null;
    }
}
