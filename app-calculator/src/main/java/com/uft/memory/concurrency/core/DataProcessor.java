package com.uft.memory.concurrency.core;

import com.uft.facade.PageData;
import com.uft.memory.concurrency.core.data.AbstractData;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
public interface DataProcessor {

    List<AbstractData> doProcess(ProcessorContext context, PageData pageData, RabbitObject rabbitObject);

    String dealModule();
}
