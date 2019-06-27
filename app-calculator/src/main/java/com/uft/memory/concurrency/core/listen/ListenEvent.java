package com.uft.memory.concurrency.core.listen;

import com.uft.memory.concurrency.core.ProcessorContext;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public interface ListenEvent {

    ActionEvents eventType();//事件类型


    ProcessorContext getProcessorContext();//获取当前jvm进程所有DataProcessor的上下文信息
}
