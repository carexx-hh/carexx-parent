package com.sh.carexx.bean.order;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerAppointOrderFormBean extends BasicFormBean {

	private String userId;

	@Pattern(regexp = "[0,1,2]")
	private String sex;

	@NotBlank
	private String patientName;

	@NotBlank
	@Size(max = 20)
	private String phone;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private String instId;

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

	@Pattern(regexp = CarexxConstant.Regex.DATETIME)
	private String serviceEndTime;

	@Size(max = 255)
	private String orderRemark;

	public Integer getUserId() {
		if (StringUtils.isNotBlank(userId)) {
			return Integer.parseInt(userId);
		}
		return null;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Byte getSex() {
		if (ValidUtils.isInteger(sex)) {
			return Byte.parseByte(sex);
		}
		return null;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
		if (StringUtils.isNotBlank(instId)) {
			return Integer.parseInt(instId);
		}
		return null;
	}

	public void setInstId(String instId) {
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

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

}
