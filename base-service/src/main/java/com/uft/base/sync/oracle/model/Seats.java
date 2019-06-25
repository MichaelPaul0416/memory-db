package com.uft.base.sync.oracle.model;

import java.io.Serializable;

/**
 * @Author: wangqiang20995
 * @Date:2019/6/25
 * @Description:
 * @Resource:
 */
public class Seats implements Serializable {

    private static final long serialVersionUID = 1l;

    private String exchangeType;
    private String seatNo;
    private String firmId;
    private String branchNo;
    private String seatProp;
    private String seatVipFlag;
    private String enStockType;
    private String enClientGroup;
    private String defaultMark;
    private String remark;
    private String cashReportFlag;
    private String seatOrganFlag;
    private String icsVoucherFlag;


    @Override
    public String toString() {
        return "Seats{" +
                "exchangeType='" + exchangeType + '\'' +
                ", seatNo='" + seatNo + '\'' +
                ", firmId='" + firmId + '\'' +
                ", branchNo='" + branchNo + '\'' +
                ", seatProp='" + seatProp + '\'' +
                ", seatVipFlag='" + seatVipFlag + '\'' +
                ", enStockType='" + enStockType + '\'' +
                ", enClientGroup='" + enClientGroup + '\'' +
                ", defaultMark='" + defaultMark + '\'' +
                ", remark='" + remark + '\'' +
                ", cashReportFlag='" + cashReportFlag + '\'' +
                ", seatOrganFlag='" + seatOrganFlag + '\'' +
                ", icsVoucherFlag='" + icsVoucherFlag + '\'' +
                '}';
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getSeatProp() {
        return seatProp;
    }

    public void setSeatProp(String seatProp) {
        this.seatProp = seatProp;
    }

    public String getSeatVipFlag() {
        return seatVipFlag;
    }

    public void setSeatVipFlag(String seatVipFlag) {
        this.seatVipFlag = seatVipFlag;
    }

    public String getEnStockType() {
        return enStockType;
    }

    public void setEnStockType(String enStockType) {
        this.enStockType = enStockType;
    }

    public String getEnClientGroup() {
        return enClientGroup;
    }

    public void setEnClientGroup(String enClientGroup) {
        this.enClientGroup = enClientGroup;
    }

    public String getDefaultMark() {
        return defaultMark;
    }

    public void setDefaultMark(String defaultMark) {
        this.defaultMark = defaultMark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCashReportFlag() {
        return cashReportFlag;
    }

    public void setCashReportFlag(String cashReportFlag) {
        this.cashReportFlag = cashReportFlag;
    }

    public String getSeatOrganFlag() {
        return seatOrganFlag;
    }

    public void setSeatOrganFlag(String seatOrganFlag) {
        this.seatOrganFlag = seatOrganFlag;
    }

    public String getIcsVoucherFlag() {
        return icsVoucherFlag;
    }

    public void setIcsVoucherFlag(String icsVoucherFlag) {
        this.icsVoucherFlag = icsVoucherFlag;
    }
}
