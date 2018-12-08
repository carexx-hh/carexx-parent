package com.sh.carexx.common.enums;

/**
 * 
 * ClassName: Identity <br/> 
 * Function: 身份类型定义 <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * Date: 2018年12月5日 下午2:41:08 <br/> 
 * 
 * @author zhoulei 
 * @since JDK 1.8
 */
public enum Identity {
	PATIENT((byte) 1, "患者"), 
	CAREGIVERS((byte) 2, "护工"), 
	NURSING_SUPERVISOR((byte) 3, "护工管理员");

	private Byte value;
	private String desc;

	Identity(Byte value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public Byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
