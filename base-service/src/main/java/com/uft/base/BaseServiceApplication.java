package com.uft.base;

import com.uft.base.sync.oracle.dao.SeatsDao;
import com.uft.base.sync.oracle.model.Seats;
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
public class BaseServiceApplication {

    public static void main(String[] args){
        ApplicationContext applicationContext  = SpringApplication.run(BaseServiceApplication.class,args);

        SeatsDao seatsDao = applicationContext.getBean(SeatsDao.class);
        Seats seats = new Seats();
        seats.setBranchNo("20190625");
        List<Seats> list = seatsDao.queryConditionSeats(seats);
        System.out.println(list);
    }
}
