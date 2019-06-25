package com.uft.base.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:任务单元
 */
@Component
public class SynchronizedAccountWorker implements DataWorker{

    private final Logger logger = LoggerFactory.getLogger(SynchronizedAccountWorker.class);

    private final String model = "account";

    @Override
    public void doWorker() {
        logger.info("从oracle查询数据，然后清洗转换，最后插入到h2数据库");
    }

    @Override
    public String modelName() {
        return model;
    }
}
