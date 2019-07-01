package com.paul.base.sync.oracle.secu.dao;

import com.paul.base.BaseServiceApplication;
import com.paul.base.sync.h2.dao.TransferDataInsertDao;
import com.paul.base.sync.h2.model.TransferIntoDataWrapper;
import com.paul.base.sync.oracle.Pager;
import com.paul.base.sync.oracle.secu.model.TransferPageMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/30
 * @Description:
 * @Resource:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BaseServiceApplication.class})
public class EntrustMapperTest {

    @Autowired
    private EntrustMapper entrustMapper;

    @Autowired
    private TransferDataInsertDao dataInsertDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void insertH2(){
        Pager pager = new Pager();
        pager.setStart(0);
        List<TransferPageMap> list = entrustMapper.transferPageDataToH2(pager);
        logger.info("list:{}",list.size());

        TransferIntoDataWrapper wrapper = new TransferIntoDataWrapper();
        wrapper.setTableName("TRANSFER_MIDDLE_DATA_1 ");
        wrapper.setList(list);

        logger.info("data:{}",list);
        dataInsertDao.insertTransferBatchData(wrapper);
    }
}