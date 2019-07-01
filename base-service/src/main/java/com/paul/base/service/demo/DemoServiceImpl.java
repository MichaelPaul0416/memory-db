package com.paul.base.service.demo;

import com.alibaba.dubbo.config.annotation.Service;
import com.paul.facade.MemoryConstant;
import com.paul.facade.TransferData;
import com.paul.facade.demo.rpc.DemoService;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public TransferData display(String info) {
        String response = "hello:" + info;

        TransferData transferData = new TransferData();
        transferData.setData(response);
        transferData.setError("ok");
        transferData.setModel("demo");
        transferData.setErrorCode(MemoryConstant.SUCCESS);

        return transferData;
    }
}
