package com.paul.memory.concurrency.utils;

import com.paul.facade.MemoryConstant;
import com.paul.memory.concurrency.config.TaskConfig;
import com.paul.memory.concurrency.core.data.AbstractData;
import com.paul.memory.concurrency.dao.h2.bean.NodeInfo;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public class CommonUtils {

    private static Holder<NodeInfo> holder ;

    private static final class Holder<T>{
        private final T data;

        public Holder(T t){
            this.data = t;
        }

        public T getData(){
            return this.data;
        }
    }

    public static String generateUniqueId(AbstractData abstractData){
        return String.valueOf(abstractData.hashCode());
    }

    public static  int moduleNodes(String module,TaskConfig taskConfig) {
        if (MemoryConstant.MODULE_ACCOUNT.equals(module)) {
            String account = taskConfig.getAccount_number();
            return StringUtils.isEmpty(account) ? 0 : Integer.parseInt(account);
        }

        if (MemoryConstant.MODULE_ORDER.equals(module)) {
            String order = taskConfig.getOrder_number();
            return StringUtils.isEmpty(order) ? 0 : Integer.parseInt(order);
        }

        if (MemoryConstant.MODULE_TRADE.equals(module)) {
            String trade = taskConfig.getTrade_number();
            return StringUtils.isEmpty(trade) ? 0 : Integer.parseInt(trade);
        }

        return 0;
    }

    public static String randomId(String name){
        UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());

        return uuid.toString().replaceAll("\\-","").substring(0,16);
    }

    public static void setNodeInfo(NodeInfo nodeInfo){
        CommonUtils.holder = new Holder<>(nodeInfo);
    }

    public static NodeInfo getNodeInfo(){
        return CommonUtils.holder.getData();
    }


}
