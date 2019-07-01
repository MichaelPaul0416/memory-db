package com.paul.memory.concurrency.mq.consumer;

import com.rabbitmq.client.Channel;
import com.paul.facade.MemoryConstant;
import com.paul.memory.concurrency.core.FinalDataContainer;
import com.paul.memory.concurrency.dao.h2.bean.NodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:
 * @Resource:
 */
@Component
public class AccountModuleDoneConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = MemoryConstant.DONE_QUEUE,containerFactory = "rabbitListenerContainerFactory")
    public void doDoneAdvice(NodeInfo nodeInfo, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        logger.info("收到node[{}]的处理完毕消息，通知给容器",nodeInfo);

        FinalDataContainer.singleModuleNodesDone(nodeInfo);

        channel.basicAck(deliveryTag,false);
    }
}
