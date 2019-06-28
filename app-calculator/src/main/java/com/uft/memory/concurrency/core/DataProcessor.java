package com.uft.memory.concurrency.core;

import com.uft.facade.PageData;
import com.uft.memory.concurrency.core.data.AbstractData;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:实现类需要保证线程安全,发型消息给消息队列，由装饰器类实现，业务类无须关心
 */
public interface DataProcessor {

    List<AbstractData> doProcess(ProcessorContext context, PageData pageData, RabbitObject rabbitObject);

    String dealModule();
}
