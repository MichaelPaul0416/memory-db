package com.uft.memory.concurrency.core;

import com.uft.facade.PageData;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
public interface DataProcessor {

    void doProcess(ProcessorContext context, PageData pageData);

    String dealModule();
}
