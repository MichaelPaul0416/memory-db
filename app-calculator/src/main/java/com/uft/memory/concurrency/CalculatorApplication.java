package com.uft.memory.concurrency;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.uft.memory.concurrency.demo.ServiceHolderDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDubbo
public class CalculatorApplication {

    public static void main(String[] args){
        ApplicationContext applicationContext
                = SpringApplication.run(CalculatorApplication.class,args);

        ServiceHolderDemo holderDemo = applicationContext.getBean(ServiceHolderDemo.class);
        holderDemo.showService();
    }
}
