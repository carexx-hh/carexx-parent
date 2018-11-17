package com.sh.carexx.uc.manager;

import com.sh.carexx.common.util.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class test {

    @Scheduled(cron = "10 * * * * ?")//秒 分 小时 日期 月份 星期 年(可选)
    public void test2(){
        System.out.println("当前时间:"+DateUtils.toString(new Date(),DateUtils.YYYY_MM_DD_HH_MM_SS));
    }
}
