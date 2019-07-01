package com.paul.base.sync.mapper;

import com.paul.base.sync.oracle.secu.model.Student;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
//@Component
//@Mapper
public interface StudentMapper {

//    @Insert("INSERT INTO student (name, sex, addr) VALUES (#{name}, #{sex}, #{addr})")
    int insert(Student student);
}
