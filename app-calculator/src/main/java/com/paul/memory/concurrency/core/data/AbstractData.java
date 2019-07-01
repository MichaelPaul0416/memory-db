package com.paul.memory.concurrency.core.data;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/27
 * @Description:各个模块的数据类型父类
 * @Resource:
 */
public abstract class AbstractData implements Serializable {

    public AbstractData(){}

    //标示这个data是哪个node处理的
    public abstract String dealNode();

    public abstract void setDealNode(String node);

    private boolean currentNodeDone;

    public boolean isCurrentNodeDone() {
        return currentNodeDone;
    }

    public void setCurrentNodeDone(boolean currentNodeDone) {
        this.currentNodeDone = currentNodeDone;
    }
}
