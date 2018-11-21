package com.sh.carexx.common.enums.staff;

/**
 *
 * ClassName: JobStatus <br/>
 * Function: 定义服务班次 <br/>
 * Reason: 枚举定义 <br/>
 * Date: 2018年4月24日 下午5:43:53 <br/>
 *
 * @author ht
 * @since JDK 1.8
 */
public enum JobType {
    DAY_JOB((byte) 1, "白班"),
    NIGHT_JOB((byte) 2, "夜班");
	
    private Byte value;
    private String desc;

    JobType(Byte value, String desc) {
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
