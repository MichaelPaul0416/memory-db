package com.paul.base.sync.h2.dao;

import com.paul.base.sync.h2.model.TransferIntoDataWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/30
 * @Description:
 * @Resource:
 */
@Repository
@Mapper
public interface TransferDataInsertDao {

    void insertTransferBatchData(TransferIntoDataWrapper wrapper);
}
