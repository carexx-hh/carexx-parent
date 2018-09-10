package com.sh.carexx.common.enums.pay;

/**
 * 
 * ClassName: PayMethod <br/>
 * Function: 定义支付方式<br/>
 * Reason: 枚举定义 <br/>
 * Date: 2018年4月24日 下午5:43:53 <br/>
 * 
 * @author WL
 * @since JDK 1.8
 */
public enum PayMethod {
	ONLINE_PAY((byte) 1, "线上支付"), 
	SCAN_PAY((byte) 2, "扫码支付"),
	COMPANY_TURN_ACCOUNT((byte) 3, "公司转账"),
	CASH_PAY((byte) 4, "现金支付");

	private Byte value;
	private String desc;

	PayMethod(Byte value, String desc) {
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
