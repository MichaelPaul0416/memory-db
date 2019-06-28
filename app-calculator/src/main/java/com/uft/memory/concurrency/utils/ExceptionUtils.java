package com.uft.memory.concurrency.utils;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:
 * @Resource:
 */
public class ExceptionUtils {

    public static void checkAndThrowIfNecessory(Object object){
        if(object == null){
            throw new NullPointerException("null object");
        }
    }
}
