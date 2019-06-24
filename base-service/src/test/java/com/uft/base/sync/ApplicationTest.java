package com.uft.base.sync;

import com.uft.base.BaseServiceApplication;
import com.uft.base.sync.mapper.StudentMapper;
import com.uft.base.sync.oracle.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BaseServiceApplication.class,DataSourceAutoConfiguration.class})
public class ApplicationTest {

    @Autowired
    StudentMapper studentMapper;

    @Test
    public void test(){
        Student student = new Student();
        student.setId(1);
        student.setName("hello");
        student.setSex(1);
        student.setAddr("china");

        studentMapper.insert(student);
    }
}
