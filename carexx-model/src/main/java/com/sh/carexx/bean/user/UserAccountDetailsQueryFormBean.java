package com.sh.carexx.bean.user;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.util.ValidUtils;
import org.apache.commons.lang.StringUtils;

public class UserAccountDetailsQueryFormBean extends BasicFormBean {

    private String accountId;

    private String payType;

    private String payTime;

    public Integer getAccountId() {
        if (StringUtils.isNotBlank(accountId)) {
            return Integer.parseInt(accountId);
        }
        return null;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
