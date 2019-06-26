package com.uft.base;

//import com.uft.base.sync.h2.dao.BookDao;
//import com.uft.base.sync.h2.model.Book;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.uft.base.sync.h2.dao.BookDao;
import com.uft.base.sync.h2.model.Book;
import com.uft.base.sync.oracle.dao.SeatsDao;
import com.uft.base.sync.oracle.model.Seats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDubbo(scanBasePackages = "com.uft.base.service")
public class BaseServiceApplication {

    private static Logger logger = LoggerFactory.getLogger(BaseServiceApplication.class);

    public static void main(String[] args){
        ApplicationContext applicationContext  = SpringApplication.run(BaseServiceApplication.class,args);

//        showAllBean(applicationContext);
//        SeatsDao seatsDao = applicationContext.getBean(SeatsDao.class);
//        Seats seats = new Seats();
//        seats.setBranchNo("20190625");
//        List<Seats> list = seatsDao.queryConditionSeats(seats);
//        System.out.println(list);
//
//        BookDao bookDao = applicationContext.getBean(BookDao.class);
//        List<Book> books = bookDao.queryConditionBooks(new Book());
//        logger.info("books:{}",books);
    }

    private static void showAllBean(ApplicationContext applicationContext){
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for(String bean : beanNames){
            logger.info("bean:{}",bean);
        }
    }
}
