package com.sh.carexx.bean.user;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class UserAccountDetailFormBean extends BasicFormBean {

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String accountId;

    private String orderNo;

    @NotBlank
    private String payType;

    @NotBlank
    @DecimalMin(value = CarexxConstant.DecimalMin.MIN_AMT)
    private String payAmt;

    private String payChnl;

    private String payChnlTransNo;

    private String payStatus;

    private Date payTime;

    public Integer getAccountId() {
        if (StringUtils.isNotBlank(accountId)) {
            return Integer.parseInt(accountId);
        }
        return null;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Byte getPayType() {
        if (ValidUtils.isInteger(payType)) {
            return Byte.parseByte(payType);
        }
        return null;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public Byte getPayChnl() {
        if (ValidUtils.isInteger(payChnl)) {
            return Byte.parseByte(payChnl);
        }
        return null;
    }

    public void setPayChnl(String payChnl) {
        this.payChnl = payChnl;
    }

    public String getPayChnlTransNo() {
        return payChnlTransNo;
    }

    public void setPayChnlTransNo(String payChnlTransNo) {
        this.payChnlTransNo = payChnlTransNo;
    }

    public Byte getPayStatus() {
        if (ValidUtils.isInteger(payStatus)) {
            return Byte.parseByte(payStatus);
        }
        return null;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
