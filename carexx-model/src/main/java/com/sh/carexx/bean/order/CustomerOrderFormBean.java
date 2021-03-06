package com.sh.carexx.bean.order;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerOrderFormBean extends BasicFormBean {
    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String id;

    private String orderType;

    private String userId;

    private String customerId;
    
    @Size(max = 20)
    private String phone;

    private String patientName;

    private Integer instId;

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String serviceId;

    @Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
    private String inpatientAreaId;

    @Size(max = 20)
    private String accurateAddress;

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.DATETIME)
    private String serviceStartTime;

    @NotBlank
    @Pattern(regexp = CarexxConstant.Regex.DATETIME)
    private String serviceEndTime;

    private Integer operatorId;

    @Size(max = 255)
    private String orderRemark;

    private String orderStatus;

    public Long getId() {
        if (StringUtils.isNotBlank(id)) {
            return Long.parseLong(id);
        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Byte getOrderType() {
        if (ValidUtils.isInteger(orderType)) {
            return Byte.parseByte(orderType);
        }
        return null;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public Integer getCustomerId() {
        if (StringUtils.isNotBlank(customerId)) {
            return Integer.parseInt(customerId);
        }
        return null;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public Integer getServiceId() {
        if (StringUtils.isNotBlank(serviceId)) {
            return Integer.parseInt(serviceId);
        }
        return null;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getInpatientAreaId() {
        if (StringUtils.isNotBlank(inpatientAreaId)) {
            return Integer.parseInt(inpatientAreaId);
        }
        return null;
    }

    public void setInpatientAreaId(String inpatientAreaId) {
        this.inpatientAreaId = inpatientAreaId;
    }

    public String getAccurateAddress() {
        return accurateAddress;
    }

    public void setAccurateAddress(String accurateAddress) {
        this.accurateAddress = accurateAddress;
    }

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public Byte getOrderStatus() {
        if (ValidUtils.isInteger(orderStatus)) {
            return Byte.parseByte(orderStatus);
        }
        return null;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
