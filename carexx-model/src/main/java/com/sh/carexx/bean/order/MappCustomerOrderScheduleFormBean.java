package com.sh.carexx.bean.order;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

	private Integer userId;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.INTEGER_POSITIVE)
	private Integer schedulingType;//排班状态 0是当前排班 1是新增一条排班

	public Integer getSchedulingType() {
		return schedulingType;
	}

	public void setSchedulingType(Integer schedulingType) {
		this.schedulingType = schedulingType;
	}

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
