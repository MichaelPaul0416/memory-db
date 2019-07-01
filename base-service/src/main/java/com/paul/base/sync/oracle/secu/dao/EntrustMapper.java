package com.paul.base.sync.oracle.secu.dao;


import com.paul.base.sync.oracle.Pager;
import com.paul.base.sync.oracle.secu.model.Entrust;
import com.paul.base.sync.oracle.secu.model.TransferPageMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EntrustMapper {

    List<Entrust> queryByCondition(Entrust entrust);

    List<Entrust> queryByPage(Pager pager);

    Integer totalNumber();

    List<TransferPageMap> transferPageDataToH2(Pager pager);
}