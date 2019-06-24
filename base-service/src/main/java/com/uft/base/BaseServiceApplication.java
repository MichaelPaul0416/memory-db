package com.uft.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
@SpringBootApplication
@EnableAutoConfiguration
public class BaseServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(BaseServiceApplication.class,args);
    }
}
