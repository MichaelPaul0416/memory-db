package com.uft.memory.concurrency.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
@Component
public class AccountConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = {"account_queue_0","account_queue_1","account_queue_2","account_queue_3"},
            containerFactory = "rabbitListenerContainerFactory")
    public void dealMqData(String message) throws Exception{
        logger.info("receive message:{}",message);

        Thread.sleep(1000 * 1);
    }
}
