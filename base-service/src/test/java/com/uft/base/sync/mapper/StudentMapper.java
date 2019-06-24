package com.uft.base.sync.mapper;

import com.uft.base.sync.oracle.model.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
@Component
@Mapper
public interface StudentMapper {

    @Insert("INSERT INTO student (name, sex, addr) VALUES (#{name}, #{sex}, #{addr})")
    int insert(Student student);
}
