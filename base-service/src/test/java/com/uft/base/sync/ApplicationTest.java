package com.uft.base.sync;

import com.uft.base.BaseServiceApplication;
import com.uft.base.sync.oracle.dao.SeatsDao;
import com.uft.base.sync.oracle.model.Seats;
import com.uft.base.sync.oracle.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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

    @Autowired
    SeatsDao seatsDao;

    @Test
    public void testOracle(){
        Seats seats = new Seats();
        seats.setBranchNo("20190625");
        List<Seats> list = seatsDao.queryConditionSeats(seats);
        logger.info("data:{}",list);
    }

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
