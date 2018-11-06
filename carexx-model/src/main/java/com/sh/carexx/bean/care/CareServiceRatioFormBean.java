package com.sh.carexx.bean.care;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;

public class CareServiceRatioFormBean extends BasicFormBean {

	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private String id;

	private String serviceAddress;

	@NotBlank
	@DecimalMin(value = CarexxConstant.DecimalMin.MIN_PERCENT)
	private String serviceRatio;

	private String serviceRatioStatus;

	public Integer getId() {
		if (StringUtils.isNotBlank(id)) {
			return Integer.parseInt(id);
		}
		return null;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Byte getServiceAddress() {
		if (ValidUtils.isInteger(serviceAddress)) {
			return Byte.parseByte(serviceAddress);
		}
		return null;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public String getServiceRatio() {
		return serviceRatio;
	}

	public void setServiceRatio(String serviceRatio) {
		this.serviceRatio = serviceRatio;
	}

	public Byte getServiceRatioStatus() {
		if (ValidUtils.isInteger(serviceRatioStatus)) {
			return Byte.parseByte(serviceRatioStatus);
		}
		return null;
	}

	public void setServiceRatioStatus(String serviceRatioStatus) {
		this.serviceRatioStatus = serviceRatioStatus;
	}

}
