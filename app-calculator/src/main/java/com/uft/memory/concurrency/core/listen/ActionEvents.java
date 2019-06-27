package com.uft.memory.concurrency.core.listen;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:
 * @Resource:
 */
public enum ActionEvents {

    SYNC_WRITE((byte) 0),

    ASYNC_WRITE((byte) 1),

    DEAL_DATA((byte) 2);

    private byte action;

    ActionEvents(byte action){
        this.action = action;
    }


}
