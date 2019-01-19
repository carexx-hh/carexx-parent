package com.sh.carexx.model.uc;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccountDetails {
    private Long id;

    private Integer userId;

    private String payNo;

    private String orderNo;

    private Byte payType;

    private BigDecimal payAmt;

    private BigDecimal payAmtAfter;

    private Byte payChnl;

    private String payChnlTransNo;

    private Byte payStatus;

    private Date payTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public BigDecimal getPayAmtAfter() {
        return payAmtAfter;
    }

    public void setPayAmtAfter(BigDecimal payAmtAfter) {
        this.payAmtAfter = payAmtAfter;
    }

    public Byte getPayChnl() {
        return payChnl;
    }

    public void setPayChnl(Byte payChnl) {
        this.payChnl = payChnl;
    }

    public String getPayChnlTransNo() {
        return payChnlTransNo;
    }

    public void setPayChnlTransNo(String payChnlTransNo) {
        this.payChnlTransNo = payChnlTransNo == null ? null : payChnlTransNo.trim();
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}