package com.uft.memory.concurrency.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
@Component
public class ApplicationLifeListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();

        Map<String,Queue> queueMap = applicationContext.getBeansOfType(Queue.class);
        Map<String,Exchange> exchangeMap = applicationContext.getBeansOfType(Exchange.class);
        Map<String,Binding> bindingMap = applicationContext.getBeansOfType(Binding.class);

        for(Map.Entry<String,Queue> entry : queueMap.entrySet()){
            String name = entry.getKey();
            Queue queue = entry.getValue();

            logger.debug("create queue[{}] in rabbitmq...",name);
            this.rabbitAdmin.declareQueue(queue);
        }

        for(Map.Entry<String,Exchange> entry : exchangeMap.entrySet()){
            String name = entry.getKey();
            Exchange exchange = entry.getValue();

            logger.debug("create exchange[{}] in rabbitmq...",name);
            this.rabbitAdmin.declareExchange(exchange);
        }

        for(Map.Entry<String,Binding> entry : bindingMap.entrySet()){
            String name = entry.getKey();
            Binding binding = entry.getValue();

            logger.debug("create binding relation[{}] in rabbitmq...",name);
            this.rabbitAdmin.declareBinding(binding);
        }
    }
}
