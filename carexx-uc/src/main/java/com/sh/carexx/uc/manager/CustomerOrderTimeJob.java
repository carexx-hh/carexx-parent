package com.sh.carexx.uc.manager;

import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.SpringUtil;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by scchen
 * Created time on 2019/1/21
 * Description 描述
 */
public class CustomerOrderTimeJob implements Job {

    private Logger log = Logger.getLogger(CustomerOrderTimeJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("定时任务开始");
        try {
            log.info("getMergedJobDataMap:" + context.getMergedJobDataMap());
            log.info("instId:" + context.getMergedJobDataMap().getInt("instId"));
            CustomerOrderScheduleManager customerOrderScheduleManager = (CustomerOrderScheduleManager) SpringUtil.getBean("customerOrderScheduleManager");
            CustomerOrderManager customerOrderManager = (CustomerOrderManager) SpringUtil.getBean("customerOrderManager");
            customerOrderScheduleManager.timingShedule(context.getMergedJobDataMap().getInt("instId"));
            customerOrderManager.modifyOrderAmtAndHoliday(context.getMergedJobDataMap().getInt("instId"));
        } catch (BizException e) {
            e.printStackTrace();
        }
        log.info("定时任务结束");
        log.info(new Date());
    }

}