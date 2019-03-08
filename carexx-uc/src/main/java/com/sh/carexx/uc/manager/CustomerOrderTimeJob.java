package com.sh.carexx.uc.manager;

import com.sh.carexx.common.exception.BizException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by scchen
 * Created time on 2019/1/21
 * Description 描述
 */
public class CustomerOrderTimeJob implements Job {

    @Autowired
    private CustomerOrderManager customerOrderManager;

    @Autowired
    private CustomerOrderScheduleManager customerOrderScheduleManager;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("定时任务开始");
        try {
            this.customerOrderScheduleManager.timingShedule(context.getMergedJobDataMap().getInt("instId"));
            this.customerOrderManager.modifyOrderAmtAndHoliday(context.getMergedJobDataMap().getInt("instId"));
        } catch (BizException e) {
            e.printStackTrace();
        }
        System.out.println(context.getMergedJobDataMap().getInt("instId"));
        System.out.println("定时任务结束");
        System.out.println(new Date());
    }

}