package com.paul.base.sync.h2.dao;

import com.paul.base.sync.h2.model.Book;
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
public interface BookDao {

    void insertBook(Book book);

    List<Book> queryConditionBooks(Book book);
}
