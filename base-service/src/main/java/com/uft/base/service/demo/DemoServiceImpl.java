package com.uft.base.service.demo;

import com.alibaba.dubbo.config.annotation.Service;
import com.uft.facade.demo.rpc.DemoService;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String display(String info) {
        return "hello:" + info;
    }
}
