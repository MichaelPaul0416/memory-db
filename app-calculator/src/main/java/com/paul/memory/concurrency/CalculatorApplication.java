package com.paul.memory.concurrency;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.paul.memory.concurrency.demo.ServiceHolderDemo;
import com.paul.memory.concurrency.task.LoadDataStarter;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
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
//@EnableDubbo
public class CalculatorApplication {

    public static void main(String[] args){
        ApplicationContext applicationContext
                = SpringApplication.run(CalculatorApplication.class,args);

//        ServiceHolderDemo holderDemo = applicationContext.getBean(ServiceHolderDemo.class);
//        holderDemo.showService();

//        showBeansByType(applicationContext,Queue.class);
//        showBeansByType(applicationContext,Exchange.class);

        LoadDataStarter loadDataStarter = applicationContext.getBean(LoadDataStarter.class);
        loadDataStarter.startLoad();
    }

//    private static void showBeansByType(ApplicationContext applicationContext,Class clazz){
//        String[] names = applicationContext.getBeanNamesForType(clazz);
//        for(String name : names) {
//            System.out.println(name);
//        }
//
//    }
}
