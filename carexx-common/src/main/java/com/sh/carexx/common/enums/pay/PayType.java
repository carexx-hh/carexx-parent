package com.sh.carexx.common.enums.pay;

/**
 *
 * ClassName: PayChnl <br/>
 * Function: 定义支付渠道<br/>
 * Reason: 枚举定义 <br/>
 * Date: 2018年4月24日 下午5:43:53 <br/>
 *
 * @author WL
 * @since JDK 1.8
 */
public enum PayType {
    RECHARGE((byte) 1, "充值"),
    ReFund((byte) 2, "提现"),
    ORDERPAY((byte) 3, "订单支付");

    private Byte value;
    private String desc;

    PayType(Byte value, String desc) {
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
