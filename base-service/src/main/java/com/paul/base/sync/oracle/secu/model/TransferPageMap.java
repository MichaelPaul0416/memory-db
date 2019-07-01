package com.paul.base.sync.oracle.secu.model;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/30
 * @Description:
 * @Resource:
 */
public class TransferPageMap implements Serializable {
    private static final long serialVersionUID = 1L;

    private String opEntrustWay;
    private Long reportTime;
    private Long fareKind;
    private String entrustType;
    private Long entrustNo;
    private Long roomCode;
    private String fundAccount;
    private String stockAccount;
    private String entrustProp;
    private String orderId;
    private String entrustBs;
    private String reportAccount;
    private String exchangeType;


    private String fareKindStr;
    private Long discountModel;
    private Long branchNo;


    @Override
    public String toString() {
        return "TransferPageMap{" +
                "opEntrustWay='" + opEntrustWay + '\'' +
                ", reportTime=" + reportTime +
                ", fareKind=" + fareKind +
                ", entrustType='" + entrustType + '\'' +
                ", entrustNo=" + entrustNo +
                ", roomCode=" + roomCode +
                ", fundAccount='" + fundAccount + '\'' +
                ", stockAccount='" + stockAccount + '\'' +
                ", entrustProp='" + entrustProp + '\'' +
                ", orderId='" + orderId + '\'' +
                ", entrustBs='" + entrustBs + '\'' +
                ", reportAccount='" + reportAccount + '\'' +
                ", exchangeType='" + exchangeType + '\'' +
                ", fareKindStr='" + fareKindStr + '\'' +
                ", discountModel=" + discountModel +
                ", branchNo=" + branchNo +
                '}';
    }

    public String getFareKindStr() {
        return fareKindStr;
    }

    public void setFareKindStr(String fareKindStr) {
        this.fareKindStr = fareKindStr;
    }

    public Long getDiscountModel() {
        return discountModel;
    }

    public void setDiscountModel(Long discountModel) {
        this.discountModel = discountModel;
    }

    public Long getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(Long branchNo) {
        this.branchNo = branchNo;
    }

    public String getOpEntrustWay() {
        return opEntrustWay;
    }

    public void setOpEntrustWay(String opEntrustWay) {
        this.opEntrustWay = opEntrustWay;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }

    public Long getFareKind() {
        return fareKind;
    }

    public void setFareKind(Long fareKind) {
        this.fareKind = fareKind;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public Long getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(Long entrustNo) {
        this.entrustNo = entrustNo;
    }

    public Long getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Long roomCode) {
        this.roomCode = roomCode;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getStockAccount() {
        return stockAccount;
    }

    public void setStockAccount(String stockAccount) {
        this.stockAccount = stockAccount;
    }

    public String getEntrustProp() {
        return entrustProp;
    }

    public void setEntrustProp(String entrustProp) {
        this.entrustProp = entrustProp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEntrustBs() {
        return entrustBs;
    }

    public void setEntrustBs(String entrustBs) {
        this.entrustBs = entrustBs;
    }

    public String getReportAccount() {
        return reportAccount;
    }

    public void setReportAccount(String reportAccount) {
        this.reportAccount = reportAccount;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
