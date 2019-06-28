package com.uft.memory.concurrency.task;

import com.uft.memory.concurrency.CalculatorApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
@SpringBootTest(classes = {CalculatorApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class LoadDataStarterTest {

    @Autowired
    private LoadDataStarter loadDataStarter;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void startLoad(){
        loadDataStarter.startLoad();
        while (true){

        }
    }

    @Test
    public void sendMessage() {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("account", "account_routing_" + (i % 4), "hello world，中国-" + i);
        }
    }

    @Test
    public void dealMessage() {
        try {
            Thread.sleep(1000 * 2000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}