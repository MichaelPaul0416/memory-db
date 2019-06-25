package com.uft.base.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:
 */
@Component
public class BeanRegisterListener implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("beanName:{}",beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

