package com.sh.carexx.bean.user;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

public class UserAccountFormBean extends BasicFormBean {

    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String id;

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String userId;

    @NotBlank
    @DecimalMin(value = CarexxConstant.DecimalMin.MIN_AMT)
    private String accountBalance;

    public Integer getId() {
        if (StringUtils.isNotBlank(id)) {
            return Integer.parseInt(id);
        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        if (StringUtils.isNotBlank(userId)) {
            return Integer.parseInt(userId);
        }
        return null;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }
}
