package com.paul.memory.concurrency.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/26
 * @Description:
 * @Resource:
 */
public class UniqueId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String globalSerial;//关联其他模块[如管理订单+交易],与当前模块组成一个完整的交易数据

    private String module;//分块计算时，模块id

    private int distributeId;//同一个模块下，节点Id

    public UniqueId(){}

    public UniqueId(String globalSerial,String module,int distributeId){
        this.globalSerial = globalSerial;
        this.module = module;
        this.distributeId = distributeId;
    }

    @Override
    public String toString() {
        return "UniqueId{" +
                "globalSerial='" + globalSerial + '\'' +
                ", module='" + module + '\'' +
                ", distributeId=" + distributeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniqueId)) return false;
        UniqueId uniqueId = (UniqueId) o;
        return getDistributeId() == uniqueId.getDistributeId() &&
                Objects.equals(getGlobalSerial(), uniqueId.getGlobalSerial()) &&
                Objects.equals(getModule(), uniqueId.getModule());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getGlobalSerial(), getModule(), getDistributeId());
    }

    public int getDistributeId() {

        return distributeId;
    }

    public void setDistributeId(int distributeId) {
        this.distributeId = distributeId;
    }

    public String getModule() {

        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getGlobalSerial() {
        return globalSerial;
    }

    public void setGlobalSerial(String globalSerial) {
        this.globalSerial = globalSerial;
    }

}
