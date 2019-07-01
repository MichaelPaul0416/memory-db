package com.paul.memory.concurrency.task.worker;

import com.paul.facade.MemoryConstant;
import com.paul.facade.PageData;
import com.paul.memory.concurrency.core.DataProcessor;
import com.paul.memory.concurrency.core.ProcessorContext;
import com.paul.memory.concurrency.core.RabbitObject;
import com.paul.memory.concurrency.core.data.AbstractData;
import com.paul.memory.concurrency.demo.AccountData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:目前只有账户模块，所以只需要这个实现，pageData中告诉rpc提供方需要查那几张表，查哪几段数据
 * @Resource:
 */
@Component
public class AccountDataProcessor implements DataProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static AtomicInteger integer = new AtomicInteger(0);

    @Override
    public List<AbstractData> doProcess(ProcessorContext context, PageData pageData, RabbitObject rabbitObject) {
        //调用rpc从h2的service获取基础数据，然后计算

        List<AbstractData> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            AccountData data = new AccountData();
            data.setAccountId("a-1");
            data.setAccountName("hello-AAAAA" + Thread.currentThread().getName());
            data.setApartment("G1");
            list.add(data);

            AccountData account = new AccountData();
            account.setAccountId("a-2");
            account.setAccountName("world-AAAAA" + Thread.currentThread().getName());
            account.setApartment("G2");
            list.add(account);

            logger.info("module[{}]调用rpc返回账户数据[{}]条", dealModule(), list.size());

            //实际改为rpc服务返回的数据，判断是否是最后一条，是的话走进if分支
            if (integer.addAndGet(2) == 50000) {
                account.setCurrentNodeDone(true);
            }
        }


        return list;
    }

    @Override
    public String dealModule() {
        return MemoryConstant.MODULE_ACCOUNT;
    }
}
