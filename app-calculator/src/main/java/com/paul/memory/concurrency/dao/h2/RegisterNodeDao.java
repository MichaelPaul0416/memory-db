package com.paul.memory.concurrency.dao.h2;

import com.paul.memory.concurrency.dao.h2.bean.NodeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:
 * @Resource:
 */
@Repository
@Mapper
public interface RegisterNodeDao {

    void truncateRegistry();

    void insertCurrentNode(NodeInfo nodeInfo);

    List<NodeInfo> timedAllNodesBatch();
}
