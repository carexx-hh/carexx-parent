package com.sh.carexx.bean.order;

import com.sh.carexx.bean.BasicFormBean;

public class MappCustomerOrderQueryFormBean extends BasicFormBean {

	private String orderStatus;
	
	private String serviceStatus;
	
	private Integer instId;
	
	private Integer serviceStaffId;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public Integer getServiceStaffId() {
		return serviceStaffId;
	}

	public void setServiceStaffId(Integer serviceStaffId) {
		this.serviceStaffId = serviceStaffId;
	}
	
	
}
