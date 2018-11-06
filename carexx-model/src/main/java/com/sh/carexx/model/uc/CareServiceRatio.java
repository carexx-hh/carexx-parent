package com.sh.carexx.model.uc;

import java.math.BigDecimal;
import java.util.Date;

public class CareServiceRatio {
    private Integer id;

    private Byte serviceAddress;

    private BigDecimal serviceRatio;

    private Byte serviceRatioStatus;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(Byte serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public BigDecimal getServiceRatio() {
        return serviceRatio;
    }

    public void setServiceRatio(BigDecimal serviceRatio) {
        this.serviceRatio = serviceRatio;
    }

    public Byte getServiceRatioStatus() {
        return serviceRatioStatus;
    }

    public void setServiceRatioStatus(Byte serviceRatioStatus) {
        this.serviceRatioStatus = serviceRatioStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}