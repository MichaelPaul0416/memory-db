package com.paul.base.worker;

import com.paul.base.sync.h2.dao.TransferDataInsertDao;
import com.paul.base.sync.h2.model.TransferIntoDataWrapper;
import com.paul.base.sync.oracle.Pager;
import com.paul.base.sync.oracle.secu.dao.EntrustMapper;
import com.paul.base.sync.oracle.secu.model.TransferPageMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:任务单元
 */
@Component
public class SynchronizedAccountWorker implements DataWorker {

    private final Logger logger = LoggerFactory.getLogger(SynchronizedAccountWorker.class);

    private final String model = "account";

    //100 批
    private volatile int totalBatch = 1000 / (Pager.PAGE_SIZE / 10000);

    private final byte[] monitor = new byte[0];

    @Autowired
    private ExecutorService asynThreadPool;

    @Autowired
    private EntrustMapper entrustMapper;

    @Autowired
    private TransferDataInsertDao dataInsertDao;

    @Override
    public void doWorker() {

        while (totalBatch > 0)

            asynThreadPool.execute(() -> {
                try {

                    Pager pager = new Pager();
                    int currentBatch;
                    synchronized (monitor) {
                        currentBatch = 100 - totalBatch;
                        totalBatch--;
                    }
                    pager.setStart(currentBatch * Pager.PAGE_SIZE);
                    List<TransferPageMap> list = entrustMapper.transferPageDataToH2(pager);
                    logger.info("list:{}/left batch:{}", list.size(), currentBatch);

                    TransferIntoDataWrapper wrapper = new TransferIntoDataWrapper();
                    String tableName = "TRANSFER_MIDDLE_DATA_" + (currentBatch % 10 + 1);
                    wrapper.setTableName(tableName);
                    wrapper.setList(list);

//                logger.info("data:{}",list);
                    dataInsertDao.insertTransferBatchData(wrapper);
                    logger.info("insert batch data to h2 successful:currentBatch[{}]/table[{}]/length[{}]", currentBatch, tableName, list.size());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            });

    }

    @Override
    public String modelName() {
        return model;
    }
}
