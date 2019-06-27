package com.uft.memory.concurrency.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.uft.facade.TransferData;
import com.uft.facade.demo.rpc.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
@Component
public class ServiceHolderDemo {

//    @Reference
    private DemoService demoService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void showService(){
        TransferData response = demoService.display("hello");
        logger.info("rpc service:{}",response);
    }
}
