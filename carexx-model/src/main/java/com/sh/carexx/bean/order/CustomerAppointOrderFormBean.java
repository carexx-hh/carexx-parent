package com.sh.carexx.bean.order;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerAppointOrderFormBean extends BasicFormBean {

	private String userId;

	@NotBlank
	private String patientName;

	@Size(max = 20)
	private String phone;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private String instId;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private String serviceId;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private String inpatientAreaId;

	@Size(max = 20)
	private String accurateAddress;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.DATETIME)
	private String serviceStartTime;

	@Size(max = 255)
	private String orderRemark;

	public Integer getuserId() {
		if (StringUtils.isNotBlank(userId)) {
			return Integer.parseInt(userId);
		}
		return null;
	}

	public void setuserId(String userId) {
		this.userId = userId;
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

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

}
