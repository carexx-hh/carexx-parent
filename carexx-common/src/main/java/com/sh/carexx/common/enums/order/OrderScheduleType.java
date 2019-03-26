package com.sh.carexx.common.enums.order;

/**
 * 
 * ClassName: OrderScheduleStatus <br/>
 * Function: 定义订单排班状态<br/>
 * Reason: 枚举定义 <br/>
 * Date: 2018年4月24日 下午5:43:53 <br/>
 * 
 * @author WL
 * @since JDK 1.8
 */
public enum OrderScheduleType {
	PRESENT((byte) 0, "当前"),
	POSTPONE((byte) 1, "延后");

	private Byte value;
	private String desc;

	OrderScheduleType(Byte value, String desc) {
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
