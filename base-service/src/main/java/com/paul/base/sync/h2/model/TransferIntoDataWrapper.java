package com.paul.base.sync.h2.model;

import com.paul.base.sync.oracle.secu.model.TransferPageMap;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/30
 * @Description:
 * @Resource:
 */
public class TransferIntoDataWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tableName;

    private List<TransferPageMap> list;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TransferPageMap> getList() {
        return list;
    }

    public void setList(List<TransferPageMap> list) {
        this.list = list;
    }
}
