package com.sh.carexx.bean.user;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.util.ValidUtils;

public class UserAccountDetailQueryFormBean extends BasicFormBean {

    private Integer userId;

    private String payType;

    private String payTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
