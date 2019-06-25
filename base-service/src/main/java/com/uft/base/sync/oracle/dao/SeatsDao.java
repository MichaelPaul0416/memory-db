package com.uft.base.sync.oracle.dao;

import com.uft.base.sync.oracle.model.Seats;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:
 */
@Repository
@Mapper
public interface SeatsDao {

    List<Seats> queryConditionSeats(Seats seats);
}
