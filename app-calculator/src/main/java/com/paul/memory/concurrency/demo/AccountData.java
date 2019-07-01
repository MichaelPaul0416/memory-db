package com.paul.memory.concurrency.demo;

import com.paul.memory.concurrency.core.data.AbstractData;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:
 * @Resource:
 */
public class AccountData extends AbstractData implements Serializable {

    private static final long serialVersionUID = 1L;

    public AccountData(){
//        super.setCurrentNodeDone(true);
    }

    private String accountId;
    private String accountName;
    private String apartment;

    private String dealNode;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String dealNode() {
        return this.dealNode;
    }

    @Override
    public void setDealNode(String node) {
        this.dealNode = node;
    }
}
