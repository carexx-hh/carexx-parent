package com.sh.carexx.common.quartz;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Created by scchen
 * Created time on 2019/1/21
 * Description 描述
 */
public class QuartzManager {

    private Logger log = Logger.getLogger(QuartzManager.class);

    private SchedulerFactory sf = new StdSchedulerFactory();

    /**
     * @Description: 方法描述
     * @Author: csc
     * @param: jobName 任务名
     * @param: jobGroupName 组名
     * @param: triggerName 任务名
     * @param: triggerGroupName 组名
     * @param: job 需要执行的任务
     * @param: time 间隔时间
     * @param: startTime 开始时间
     * @param: endTime 结束时间
     * @return:
     * @date
     */
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                       Job job, int time, Date startTime, Date endTime,
                       int instId, int jobType) {
        //创建一个JodDetail实例
        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, jobGroupName)
                .usingJobData("instId", instId)
                .usingJobData("jobType", jobType)
                .build();
        //定义一个Trigger,定义执行的规则
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                .startAt(startTime).endAt(endTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(time).repeatForever()).build();
        //创建scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            //将trigger和jobdetail加入这个调度
            scheduler.scheduleJob(jobDetail, trigger);
            //启动scheduler
            scheduler.start();
            log.info(new Date() + " Job start");
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

    /**
     * @Description: 方法描述
     * @Author: csc
     * @param: jobName 任务名
     * @param: jobGroupName 组名
     * @param: triggerName 任务名
     * @param: triggerGroupName 组名
     * @param: job 需要执行的任务
     * @param: cron 执行规则 * * * * * ? *
     * @param: startTime 开始时间
     * @param: endTime 结束时间
     * @return:
     * @date
     */
    public void addJobByCronExpressions(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                                        Job job, String cron, Date startTime, Date endTime,
                                        int instId, int jobType) {
        //创建一个JodDetail实例
        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, jobGroupName)
                .usingJobData("instId", instId)
                .usingJobData("jobType", jobType)
                .build();
        //定义一个Trigger,定义执行的规则
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                .startAt(startTime).endAt(endTime)
                .withSchedule(
                        //定义任务调度的时间间隔和次数
                        CronScheduleBuilder.cronSchedule(cron)
                ).build();
        //创建scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            //将trigger和jobdetail加入这个调度
            scheduler.scheduleJob(jobDetail, trigger);
            //启动scheduler
            scheduler.start();
            log.info(new Date() + " CronJob start");
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间 间隔时间
     * @param: jobName 任务名
     * @param: jobGroupName 组名
     * @param: triggerName 任务名
     * @param: triggerGroupName 组名
     * @param: job 需要执行的任务
     * @param: time 间隔时间
     * @param: startTime 开始时间
     * @param: endTime 结束时间
     */
    public void modifyJobTime(String triggerName, String triggerGroupName,
                              int time, Date startTime, Date endTime) {
        try {
            Scheduler scheduler = sf.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            //定义一个Trigger,定义执行的规则
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                    .startAt(startTime).endAt(endTime)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(time).repeatForever()).build();
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间 cron 执行规则
     * @param: jobName 任务名
     * @param: jobGroupName 组名
     * @param: triggerName 任务名
     * @param: triggerGroupName 组名
     * @param: job 需要执行的任务
     * @param: cron 执行规则 * * * * * ? *
     * @param: startTime 开始时间
     * @param: endTime 结束时间
     */
    public void modifyJobTimeByCronExpressions(String triggerName, String triggerGroupName,
                                               String cron, Date startTime, Date endTime) {
        try {
            Scheduler scheduler = sf.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                //定义一个Trigger,定义执行的规则
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                        .startAt(startTime).endAt(endTime)
                        .withSchedule(
                                //定义任务调度的时间间隔和次数
                                CronScheduleBuilder.cronSchedule(cron)
                        ).build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

    /**
     * @Description: 删除job
     * @param: jobName 任务名
     * @param: jobGroupName 组名
     */
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            Scheduler scheduler = sf.getScheduler();
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
            log.info(new Date() + " Job stop");
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

}