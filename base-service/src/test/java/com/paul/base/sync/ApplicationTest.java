package com.paul.base.sync;

import com.paul.base.BaseServiceApplication;
import com.paul.base.sync.oracle.secu.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/24
 * @Description:
 * @Resource:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BaseServiceApplication.class})
public class ApplicationTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    StudentMapper studentMapper;

    @Test
    public void test(){
        Student student = new Student();
        student.setId(1);
        student.setName("hello");
        student.setSex(1);
        student.setAddr("china");

//        studentMapper.insert(student);
    }
}
