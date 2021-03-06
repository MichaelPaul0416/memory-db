package com.paul.memory.concurrency.config;

import com.paul.facade.MemoryConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
@Component
public class RabbitConfig implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Properties properties;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        ConfigurableListableBeanFactory factory = configurableListableBeanFactory;
        String[] modules = readConfig("memory.modules").split("\\|");
        Map<String, Exchange> exchanges = registerExchanges(factory, modules);
        Map<String, Queue> queues = registerQueues(factory, modules);

        for (String module : modules) {
            String name = chooseExchange(module, exchanges.keySet());
            if (StringUtils.isEmpty(name)) {
                throw new NullPointerException("empty exchange for module[" + module + "]");
            }

            Exchange exchange = exchanges.get(name);

            int number = Integer.parseInt(readConfig("memory." + module + "_number"));
            if (number > MemoryConstant.SINGLE_MODULE_CONCURRENT) {
                throw new IllegalArgumentException("memory." + module + "_number的参数最大值应为["
                        + MemoryConstant.SINGLE_MODULE_CONCURRENT + "],当前为[" + number + "]");
            }
//            int routings = getQueues(number);
            int routings = MemoryConstant.MAX_QUEUE_NUMBER;

            for (int i = 0; i < routings; i++) {
                String queueName = chooseQueue(module, i);
                if (StringUtils.isEmpty(queueName)) {
                    throw new NullPointerException("queue not register for module[" + module + "] and exchange[" + exchange + "]");
                }

                Queue queue = queues.get(queueName);
                String key = module + MemoryConstant.RABBITMQ_ROUTING_PREFIX + i;


                Binding binding = BindingBuilder.bind(queue).to(exchange).with(key).noargs();
                logger.info("为module[{}]将queue[{}]绑定到exchange[{}],binding[{}]", modules, queue, exchange, module + MemoryConstant.RABBIT_BINDING_PREFIX + i);
                factory.registerSingleton(module + MemoryConstant.RABBIT_BINDING_PREFIX + i, binding);
            }

            //构建RabbitMqConsumer
            boolean write = MemoryConstant.STRING_TRUE.equals(readConfig("memory.enableAsynWrite"));
            if(MemoryConstant.MODULE_ACCOUNT.equals(module) && !write){
                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) factory;
                defaultListableBeanFactory.removeBeanDefinition("accountConsumer");
                defaultListableBeanFactory.removeBeanDefinition("accountModuleDoneConsumer");
                logger.info("当前节点没有开启写入数据，不注册RabbitMq队列监听者");

            }
        }
    }

    private Map<String, Exchange> registerExchanges(ConfigurableListableBeanFactory beanFactory, String[] modules) {
        Map<String, Exchange> exchanges = new HashMap<>(modules.length);

        for (String module : modules) {
            DirectExchange directExchange = new DirectExchange(module, true, false);
            beanFactory.registerSingleton(module + "Exchange", directExchange);
            exchanges.put(module, directExchange);
        }

        return exchanges;
    }

    private Map<String, Queue> registerQueues(ConfigurableListableBeanFactory beanFactory, String[] modules) {

        Map<String, Queue> queueNames = new HashMap<>();

        logger.info("开始注册RabbitMQ 的Queue");

        for (String module : modules) {
            int moduleProcessors = Integer.parseInt(readConfig("memory." + module + "_number"));
            if (moduleProcessors > MemoryConstant.SINGLE_MODULE_CONCURRENT) {
                throw new IllegalArgumentException("memory." + module + "_number的参数最大值应为["
                        + MemoryConstant.SINGLE_MODULE_CONCURRENT + "],当前为[" + moduleProcessors + "]");
            }
//            int queues = getQueues(moduleProcessors);
            int queues = MemoryConstant.MAX_QUEUE_NUMBER;

            //多个Processor发送消息到同一个queue中
            for (int i = 0; i < queues; i++) {
                String queueName = module + MemoryConstant.RABBITMQ_QUEUE_PREFIX + i;
                logger.info("为[{}]module注册队列[{}]", module, queueName);

                Queue queue = new Queue(queueName, true, false, false);

                beanFactory.registerSingleton(queueName, queue);
                queueNames.put(queueName, queue);
            }
        }

        return queueNames;
    }

//    private int getQueues(int moduleProcessors) {
//        int queues = moduleProcessors % MemoryConstant.QUEUE_WITH_PROCESSORS == 0 ?
//                moduleProcessors / MemoryConstant.QUEUE_WITH_PROCESSORS : moduleProcessors / MemoryConstant.QUEUE_WITH_PROCESSORS + 1;
//
//        queues = queues <= MemoryConstant.MAX_QUEUE_NUMBER ? queues : MemoryConstant.MAX_QUEUE_NUMBER;
//        return queues;
//    }

    private String chooseQueue(String module, int batch) {
        return module + MemoryConstant.RABBITMQ_QUEUE_PREFIX + batch;
    }

    private String chooseExchange(String module, Set<String> exchanges) {
        for (String exchange : exchanges) {
            if (exchange.contains(module)) {
                logger.info("为module[{}]获取到exchange[{}]", module, exchange);
                return exchange;
            }
        }

        return null;
    }

    private String readConfig(String key) {
        return this.properties.getProperty(key);
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

        this.properties = new Properties();

        Resource resource = resourceLoader.getResource(MemoryConstant.PROJECT_CONFIG);
        try {
            this.properties.load(resource.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("读取application.properties异常");
        }
    }
}
