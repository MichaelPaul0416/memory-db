package com.uft.memory.concurrency.task.worker;

import com.uft.facade.MemoryConstant;
import com.uft.facade.PageData;
import com.uft.memory.concurrency.core.DataProcessor;
import com.uft.memory.concurrency.core.ProcessorContext;
import com.uft.memory.concurrency.core.RabbitObject;
import com.uft.memory.concurrency.core.data.AbstractData;
import com.uft.memory.concurrency.demo.AccountData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:
 * @Resource:
 */
@Component
public class AccountDataProcessor implements DataProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<AbstractData> doProcess(ProcessorContext context, PageData pageData, RabbitObject rabbitObject) {
        //调用rpc从h2的service获取基础数据，然后计算

        List<AbstractData> list = new ArrayList<>();
        AccountData data = new AccountData();
        data.setAccountId("a-1");
        data.setAccountName("hello-" + Thread.currentThread().getName());
        data.setApartment("G1");
        list.add(data);

        AccountData account = new AccountData();
        account.setAccountId("a-2");
        account.setAccountName("world" + Thread.currentThread().getName());
        account.setApartment("G2");
        list.add(account);

        logger.info("module[{}]调用rpc返回账户数据[{}]条", dealModule(), list.size());
        return list;
    }

    @Override
    public String dealModule() {
        return MemoryConstant.MODULE_ACCOUNT;
    }
}
