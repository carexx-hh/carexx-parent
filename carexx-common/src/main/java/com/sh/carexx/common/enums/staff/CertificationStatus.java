package com.sh.carexx.common.enums.staff;

/**
 * 
 * ClassName: CertificationStatus <br/> 
 * Function: 定义认证状态<br/> 
 * Reason: 枚举定义 <br/> 
 * Date: 2018年8月27日 下午4:15:04 <br/> 
 * 
 * @author zhoulei 
 * @since JDK 1.8
 */
public enum CertificationStatus {
	NO_CERTIFICATION((byte) 0, "未认证"), 
	IN_CERTIFICATION((byte) 1, "认证中"),
	HAS_CERTIFICATION((byte) 2, "已认证"), 
	REFUSED_CERTIFICATION((byte) 3, "已拒绝");

	private Byte value;
	private String desc;

	CertificationStatus(Byte value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public byte getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}