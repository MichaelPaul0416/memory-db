package com.paul.memory.concurrency.demo;

import com.paul.facade.TransferData;
import com.paul.facade.demo.rpc.DemoService;
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
