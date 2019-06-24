package com.uft.base.sync.oracle.dao;

import com.uft.base.sync.oracle.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
@Repository
public interface StudentDao {

    List<Student> queryAll();
}
