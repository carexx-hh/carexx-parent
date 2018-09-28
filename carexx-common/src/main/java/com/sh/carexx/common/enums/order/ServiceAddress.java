package com.sh.carexx.common.enums.order;

/**
 * 
 * ClassName: ServiceAddress <br/> 
 * Function: 定义服务地址类型< <br/> 
 * Reason: 枚举定义  <br/> 
 * Date: 2018年9月28日 上午10:23:31 <br/> 
 * 
 * @author zhoulei 
 * @since JDK 1.8
 */
public enum ServiceAddress {

	INST((byte) 1, "医院"), 
	COMMUNITY((byte) 2, "社区");

	private Byte value;
	private String desc;

	ServiceAddress(Byte value, String desc) {
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
