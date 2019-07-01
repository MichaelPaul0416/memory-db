package com.paul.memory.concurrency.mq.consumer;

import com.rabbitmq.client.Channel;
import com.paul.facade.MemoryConstant;
import com.paul.memory.concurrency.config.TaskConfig;
import com.paul.memory.concurrency.core.FinalDataContainer;
import com.paul.memory.concurrency.core.UniqueId;
import com.paul.memory.concurrency.core.data.AbstractData;
import com.paul.memory.concurrency.demo.AccountData;
import com.paul.memory.concurrency.utils.CommonUtils;
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
 * @Description:从mq消费数据，并且写入FinalDataContainer
 * @Resource:
 */
@Component
public class AccountConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskConfig config;

    @RabbitListener(queues = {"account_queue_0","account_queue_1","account_queue_2","account_queue_3"},
            containerFactory = "rabbitListenerContainerFactory")
    public void dealMqData(List<AccountData> message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception{

        //开启写的节点，在同一个module计算下，目前不会出现多节点计算同一个module
        if (message == null) {
            throw new NullPointerException("本次消费的数据异常，为null");
        }

        int modules = config.getModules().split("\\|").length;
        logger.info("从MQ中消费数据[{}]",message.size());
        for(AbstractData data : message){
            UniqueId uniqueId = new UniqueId(CommonUtils.generateUniqueId(data),
                    MemoryConstant.MODULE_ACCOUNT,Integer.parseInt(config.getAccount_number()));
//            logger.info("生成uniqueId[{}]",uniqueId);
//            if(modules > 1) {
                FinalDataContainer.putModulesData(uniqueId, data);
//            }else {
//                FinalDataContainer.putModuleData(uniqueId,data);
//            }
        }
        channel.basicAck(deliveryTag,false);

    }
}
