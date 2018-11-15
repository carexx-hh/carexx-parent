package com.sh.carexx.bean.order;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;

public class CustomerOrderAdjustFormBean extends BasicFormBean {

	@NotBlank
	private String orderNo;

	@NotBlank
	private String orderType;
	
	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.DOUBLE)
	private String adjustAmt;
	
	@NotBlank
	@Pattern(regexp = "[1,2]")
	private String proofType;

	@NotBlank
	@Size(max = 20)
	private String proofNo;
	
	@NotBlank
	@Pattern(regexp = "[1,2,3,4]")
	private String payType;
	
	@Size(max = 255)
	private String orderRemark;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getAdjustAmt() {
		return adjustAmt;
	}

	public void setAdjustAmt(String adjustAmt) {
		this.adjustAmt = adjustAmt;
	}
	
	public Byte getProofType() {
		if (ValidUtils.isInteger(proofType)) {
			return Byte.parseByte(proofType);
		}
		return null;
	}

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	public String getProofNo() {
		return proofNo;
	}

	public void setProofNo(String proofNo) {
		this.proofNo = proofNo;
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

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
}
