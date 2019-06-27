package com.uft.memory.concurrency.core;

import com.uft.facade.MemoryConstant;
import com.uft.memory.concurrency.core.data.AbstractData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    static {
        relation.put(MemoryConstant.MODULE_TRADE,tradeFinalData);
        relation.put(MemoryConstant.MODULE_ACCOUNT,accountFinalData);
        relation.put(MemoryConstant.MODULE_ORDER,orderFinalData);
    }

    public static void putModuleData(UniqueId uniqueId,AbstractData data){
        String module = uniqueId.getModule();
        relation.get(module).put(uniqueId,data);
    }
    public static <T extends AbstractData> T getModuleData(UniqueId uniqueId){
        String module = uniqueId.getModule();
        return (T) relation.get(module).get(uniqueId);
    }

    public static <T extends AbstractData> T getAndRemoveModuleData(UniqueId uniqueId){
        String module = uniqueId.getModule();

        AbstractData abstractData = relation.get(module).remove(uniqueId);
        return (T) abstractData;
    }
}
