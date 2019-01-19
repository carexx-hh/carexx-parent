package com.sh.carexx.bean.user;

import com.sh.carexx.bean.BasicFormBean;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccountDetailFormBean extends BasicFormBean {

    private String openId;

    private Integer userId;

    private String payNo;

    private String orderNo;

    private Byte payType;

    private BigDecimal payAmt;

    private Byte payChnl;

    private String payChnlTransNo;

    private Byte payStatus;

    private Date payTime;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
        this.payNo = payNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
        this.payChnlTransNo = payChnlTransNo;
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
