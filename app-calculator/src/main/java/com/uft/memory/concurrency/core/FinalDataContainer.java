package com.uft.memory.concurrency.core;

import com.uft.facade.MemoryConstant;
import com.uft.memory.concurrency.core.data.AbstractData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:各个module计算完之后，从mq拉取的数据汇总到这里
 * @Resource:
 */
public class FinalDataContainer {

    private static final Logger logger = LoggerFactory.getLogger(FinalDataContainer.class);

    private static final Map<UniqueId,AbstractData> accountFinalData = new ConcurrentHashMap<>(1000);

    private static final Map<UniqueId,AbstractData> tradeFinalData = new ConcurrentHashMap<>(1000);

    private static final Map<UniqueId,AbstractData> orderFinalData = new ConcurrentHashMap<>(10000);

    private static final Map<String,Map<UniqueId,AbstractData>> relation = new ConcurrentHashMap<>(3);

    private static final Map<UniqueId,Boolean> writeNodeForModule = new ConcurrentHashMap<>();

    private static final ReentrantLock lock = new ReentrantLock();

    static {
        relation.put(MemoryConstant.MODULE_TRADE,tradeFinalData);
        relation.put(MemoryConstant.MODULE_ACCOUNT,accountFinalData);
        relation.put(MemoryConstant.MODULE_ORDER,orderFinalData);
    }

    //执行该方法的一般是DataProcessor线程
    public static boolean putModuleData(UniqueId uniqueId,AbstractData data){
        String module = uniqueId.getModule();
        try{
//            lock.lockInterruptibly();
            lock.lock();
            relation.get(module).put(uniqueId,data);
            writeNodeForModule.put(uniqueId,true);

            return true;
        }
//        catch (InterruptedException e){
//            logger.error("等待获取可重入锁时发生中断异常,请重新操作",e.getMessage());
//            return false;
//        }
        finally {
            lock.unlock();
        }
    }
    public static <T extends AbstractData> T getModuleData(UniqueId uniqueId){
        String module = uniqueId.getModule();
        return (T) relation.get(module).get(uniqueId);
    }

    //执行这个方法的只有一个线程，就是异步写入数据到文件的线程
    public static <T extends AbstractData> T getAndRemoveModuleData(UniqueId uniqueId){
        String module = uniqueId.getModule();
        try{
            lock.lock();
            AbstractData abstractData = relation.get(module).remove(uniqueId);
            writeNodeForModule.remove(uniqueId);
            return (T) abstractData;
        }finally {
            lock.unlock();
        }
    }

    public static Map<UniqueId,Boolean> currentAllDataKeys(){
        return writeNodeForModule;
    }
}
