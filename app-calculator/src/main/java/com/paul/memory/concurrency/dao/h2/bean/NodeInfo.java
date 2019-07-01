package com.paul.memory.concurrency.dao.h2.bean;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/28
 * @Description:
 * @Resource:
 */
public class NodeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nodeId;
    private String nodeName;
    private String tableName;
    private int processors;

    @Override
    public String toString() {
        return "NodeInfo{" +
                "nodeId='" + nodeId + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", processors=" + processors +
                '}';
    }

    public int getProcessors() {
        return processors;
    }

    public void setProcessors(int processors) {
        this.processors = processors;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
