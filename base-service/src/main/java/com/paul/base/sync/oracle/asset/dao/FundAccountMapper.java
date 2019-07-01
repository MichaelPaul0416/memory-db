package com.paul.base.sync.oracle.asset.dao;

import com.paul.base.sync.oracle.asset.model.FundAccount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/29
 * @Description:
 * @Resource:
 */
@Repository
@Mapper
public interface FundAccountMapper {

    List<FundAccount> queryByCondition(FundAccount fundAccount);
}
