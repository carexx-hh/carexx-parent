package com.sh.carexx.bean.user;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

public class UserAccountDetailsFormBean extends BasicFormBean {

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String accountId;

    private String orderNo;

    @NotBlank
    private String payType;

    @NotBlank
    @DecimalMin(value = CarexxConstant.DecimalMin.MIN_AMT)
    private String payAmt;

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

}
