package com.sh.carexx.bean.order;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;

public class MappCustomerOrderScheduleFormBean extends BasicFormBean {

	@NotBlank
	@Size(max = 20)
	private String orderNo;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private String serviceStaffId;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private String workTypeSettleId;
	
	private String scheduleRemark;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getServiceStaffId() {
		if (StringUtils.isNotBlank(serviceStaffId)) {
			return Integer.parseInt(serviceStaffId);
		}
		return null;
	}

	public void setServiceStaffId(String serviceStaffId) {
		this.serviceStaffId = serviceStaffId;
	}

	public Integer getWorkTypeSettleId() {
		if (StringUtils.isNotBlank(workTypeSettleId)) {
			return Integer.parseInt(workTypeSettleId);
		}
		return null;
	}

	public void setWorkTypeSettleId(String workTypeSettleId) {
		this.workTypeSettleId = workTypeSettleId;
	}

	public String getScheduleRemark() {
		return scheduleRemark;
	}

	public void setScheduleRemark(String scheduleRemark) {
		this.scheduleRemark = scheduleRemark;
	}

	
}
