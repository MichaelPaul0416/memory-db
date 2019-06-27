package com.uft.memory.concurrency.utils;

import com.uft.facade.MemoryConstant;
import com.uft.memory.concurrency.config.TaskConfig;
import com.uft.memory.concurrency.core.data.AbstractData;
import org.springframework.util.StringUtils;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public class CommonUtils {

    public static String generateUniqueId(AbstractData abstractData){
        return null;
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
}
