package com.paul.memory.concurrency.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
@Component
public class ApplicationUtils implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<Class<?>,Set<?>> beanTypes = new ConcurrentHashMap<>(8);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> Set<T> getBeansByType(Class<T> clazz){
        if(this.applicationContext == null){
            logger.error("application empty...");
            throw new NullPointerException("empty spring application context");
        }

        if(this.beanTypes.get(clazz) == null){
            Set<T> set = new HashSet<>();
            this.beanTypes.put(clazz,set);
        }else {
            return (Set<T>) this.beanTypes.get(clazz);
        }

        Set<T> beans = (Set<T>) this.beanTypes.get(clazz);
        String[] beanNames = this.applicationContext.getBeanNamesForType(clazz);

        for(String beanName : beanNames) {
            beans.add((T) this.applicationContext.getBean(beanName));
        }

        return beans;
    }

    public String[] getBeanNamesByType(Class clazz){
        if(this.applicationContext == null){
            logger.error("application empty...");
            throw new NullPointerException("empty spring application context");
        }

        return this.applicationContext.getBeanNamesForType(clazz);
    }

    public <T> T getBean(String name){
        return (T) this.applicationContext.getBean(name);
    }
}
