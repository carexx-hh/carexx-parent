package com.sh.carexx.bean.order;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class CustomerOrderTimeFormBean extends BasicFormBean {

    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String id;

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String instId;

    @NotBlank
    private String jobType;

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.TIME)
    private String startTime;

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.TIME)
    private String endTime;

    public Long getId() {
        if (StringUtils.isNotBlank(id)) {
            return Long.parseLong(id);
        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInstId() {
        if (StringUtils.isNotBlank(instId)) {
            return Integer.parseInt(instId);
        }
        return null;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public Byte getJobType() {
        if (ValidUtils.isInteger(jobType)) {
            return Byte.parseByte(jobType);
        }
        return null;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
