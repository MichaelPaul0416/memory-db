package com.uft.memory.concurrency.mq.consumer;

import com.rabbitmq.client.Channel;
import com.uft.facade.MemoryConstant;
import com.uft.memory.concurrency.config.TaskConfig;
import com.uft.memory.concurrency.core.FinalDataContainer;
import com.uft.memory.concurrency.core.UniqueId;
import com.uft.memory.concurrency.core.data.AbstractData;
import com.uft.memory.concurrency.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
//@Component
public class AccountConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskConfig config;

    @RabbitListener(queues = {"account_queue_0","account_queue_1","account_queue_2","account_queue_3"},
            containerFactory = "rabbitListenerContainerFactory")
    public void dealMqData(List<AbstractData> message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception{

        //开启写的节点，在同一个module计算下，目前不会出现多节点计算同一个module
        if(MemoryConstant.STRING_TRUE.equals(config.getEnableAsynWrite())){
            logger.info("当前module处理节点[{}],开启了异步写，不处理MQ中获取的消息",MemoryConstant.MODULE_ACCOUNT);
            return;
        }

        if (message == null) {
            throw new NullPointerException("本次消费的数据异常，为null");
        }

        logger.info("从MQ中消费数据[{}]",message.size());
        for(AbstractData data : message){
            UniqueId uniqueId = new UniqueId(CommonUtils.generateUniqueId(data),
                    MemoryConstant.MODULE_ACCOUNT,Integer.parseInt(config.getAccount_number()));
            logger.info("生成uniqueId[{}]",uniqueId);
            FinalDataContainer.putModuleData(uniqueId,data);
        }
        channel.basicAck(deliveryTag,false);

    }
}
