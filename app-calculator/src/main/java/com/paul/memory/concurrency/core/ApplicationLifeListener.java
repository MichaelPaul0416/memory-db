package com.paul.memory.concurrency.core;

import com.paul.facade.MemoryConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
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

        Map<String, Queue> queueMap = applicationContext.getBeansOfType(Queue.class);
        Map<String, Exchange> exchangeMap = applicationContext.getBeansOfType(Exchange.class);
        Map<String, Binding> bindingMap = applicationContext.getBeansOfType(Binding.class);

        for (Map.Entry<String, Queue> entry : queueMap.entrySet()) {
            String name = entry.getKey();
            Queue queue = entry.getValue();

            logger.debug("create queue[{}] in rabbitmq...", name);
            this.rabbitAdmin.declareQueue(queue);
        }

        for (Map.Entry<String, Exchange> entry : exchangeMap.entrySet()) {
            String name = entry.getKey();
            Exchange exchange = entry.getValue();

            logger.debug("create exchange[{}] in rabbitmq...", name);
            this.rabbitAdmin.declareExchange(exchange);
        }

        for (Map.Entry<String, Binding> entry : bindingMap.entrySet()) {
            String name = entry.getKey();
            Binding binding = entry.getValue();

            logger.debug("create binding relation[{}] in rabbitmq...", name);
            this.rabbitAdmin.declareBinding(binding);
        }

        Exchange exchange = new DirectExchange(MemoryConstant.DONE_EXCHANGE);
        Queue queue = new Queue(MemoryConstant.DONE_QUEUE, true, false, false);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(MemoryConstant.DONE_BINDING).noargs();
        this.rabbitAdmin.declareExchange(exchange);
        this.rabbitAdmin.declareQueue(queue);
        this.rabbitAdmin.declareBinding(binding);
        logger.debug("监听同一模块各个节点队列创建成功[{}]",binding);
    }
}
