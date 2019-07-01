package com.paul.memory.concurrency.core;

import com.paul.facade.MemoryConstant;
import com.paul.memory.concurrency.core.data.AbstractData;
import com.paul.memory.concurrency.dao.h2.bean.NodeInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:各个module计算完之后，从mq拉取的数据汇总到这里
 * @Resource:
 */
public class FinalDataContainer {

//    private static final Logger logger = LoggerFactory.getLogger(FinalDataContainer.class);

    private static final Map<UniqueId,AbstractData> accountFinalData = new ConcurrentHashMap<>(1000);

//    private static final Map<UniqueId,AbstractData> tradeFinalData = new ConcurrentHashMap<>(1000);

//    private static final Map<UniqueId,AbstractData> orderFinalData = new ConcurrentHashMap<>(10000);

    private static final Map<String,Map<UniqueId,AbstractData>> relation = new ConcurrentHashMap<>(3);

    //存储完整数据的集合，只会被负责写的node操作
    private static final Map<String,Boolean> writeNodeForModule = new ConcurrentHashMap<>();

    //同一个module下面的所有node的完成情况，当map的size=所有node的数目时，代表发送mq完成
    private static Map<String,NodeInfo> complete = new ConcurrentHashMap<>();

    private static final ReentrantLock lock = new ReentrantLock();

    private static AtomicBoolean startConsume = new AtomicBoolean(false);

    static {
//        relation.put(MemoryConstant.MODULE_TRADE,tradeFinalData);
        relation.put(MemoryConstant.MODULE_ACCOUNT,accountFinalData);
//        relation.put(MemoryConstant.MODULE_ORDER,orderFinalData);
    }


    public static void singleModuleNodesDone(NodeInfo nodeInfo){
        complete.put(nodeInfo.getNodeName(),nodeInfo);
    }

    //单一模块的某个节点是否计算完毕
    public static boolean isSingleModuleDone(int registerNode){
        return complete.size() == registerNode;
    }

    public static boolean emptyModuleData(String module){
        try{
            lock.lock();
            return relation.get(module).size() == 0 && startConsume.get();
        }finally {
            lock.unlock();
        }
    }
    public static boolean dataComplete(UniqueId uniqueId,boolean allPart,String part){
        if(allPart){
            for(Map.Entry<String,Map<UniqueId,AbstractData>> entry : relation.entrySet()){
                if(!entry.getValue().containsKey(uniqueId)){
                    return false;
                }
            }

            return true;
        }else {
            Map<UniqueId,AbstractData> map = relation.get(part);
            return map.containsKey(uniqueId);
        }
    }
    //执行该方法的一般是DataProcessor线程
    public static boolean putModulesData(UniqueId uniqueId, AbstractData data){
        String module = uniqueId.getModule();
        try{
            lock.lock();
            startConsume.set(true);
            relation.get(module).put(uniqueId,data);
            writeNodeForModule.put(uniqueId.getModule(),true);

            return true;
        }finally {
            lock.unlock();
        }
    }

//    public static boolean putModuleData(UniqueId uniqueId,AbstractData data){
//        String module = uniqueId.getModule();
//        startConsume.set(true);
//        relation.get(module).put(uniqueId,data);
//        return true;
//    }

//    public static <T extends AbstractData> T getModuleData(UniqueId uniqueId){
//        String module = uniqueId.getModule();
//        return (T) relation.get(module).get(uniqueId);
//    }

    //执行这个方法的只有一个线程，就是异步写入数据到文件的线程
    public static <T extends AbstractData> T getAndRemoveModulesData(UniqueId uniqueId){
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

    public static <T extends AbstractData> T getAndRemoveModuleData(UniqueId uniqueId){
        AbstractData data = relation.get(uniqueId.getModule()).remove(uniqueId);
        return (T) data;
    }

    public static Map<UniqueId,AbstractData> moduleData(String module){
        return relation.get(module);
    }

    //多模块计算时，负责写入模块的所有key，不同模块之间的key是一样的，所以以负责写入模块的key为准
    public static Map<String,Boolean> currentAllDataKeys(){
        return writeNodeForModule;
    }
}
