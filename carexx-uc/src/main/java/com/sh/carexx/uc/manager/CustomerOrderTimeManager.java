package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.order.CustomerOrderTimeFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.staff.JobType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.quartz.QuartzManager;
import com.sh.carexx.common.util.DateUtils;
import com.sh.carexx.model.uc.CustomerOrderTime;
import com.sh.carexx.uc.service.CustomerOrderTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerOrderTimeManager {

    @Autowired
    private CustomerOrderTimeService customerOrderTimeService;
    private QuartzManager quartzManager = new QuartzManager();

    public void add(CustomerOrderTimeFormBean customerOrderTimeFormBean) throws BizException {
        long time = DateUtils.toDate(customerOrderTimeFormBean.getStartTime(), DateUtils.HH_MM_SS).getTime();
        if (time % 3600000 != 0) {
            throw new BizException(ErrorCode.INST_JOB_TIME_ERROR);
        }
        int startHour = DateUtils.getHourOfDay(DateUtils.toDate(customerOrderTimeFormBean.getStartTime(), DateUtils.HH_MM_SS));
        if (customerOrderTimeFormBean.getJobType() == JobType.DAY_JOB.getValue() && startHour > 11) {
            throw new BizException(ErrorCode.INST_JOB_DAY_JOB_ERROR);
        }
        if (customerOrderTimeFormBean.getJobType() == JobType.NIGHT_JOB.getValue() && startHour < 12) {
            throw new BizException(ErrorCode.INST_JOB_NIGHT_JOB_ERROR);
        }
        CustomerOrderTime customerOrderTime = customerOrderTimeService.queryJobTypeExistence(customerOrderTimeFormBean.getInstId(),
                customerOrderTimeFormBean.getJobType());
        if (customerOrderTime != null) {
            throw new BizException(ErrorCode.INST_JOB_TYPE_EXISTS_ERROR);
        }
        customerOrderTime = new CustomerOrderTime();
        customerOrderTime.setInstId(customerOrderTimeFormBean.getInstId());
        customerOrderTime.setJobType(customerOrderTimeFormBean.getJobType());
        Date startTime = DateUtils.toDate(customerOrderTimeFormBean.getStartTime(),
                DateUtils.HH_MM_SS);
        Date endTime = DateUtils.toDate(customerOrderTimeFormBean.getEndTime(),
                DateUtils.HH_MM_SS);
        customerOrderTime.setStartTime(startTime);
        customerOrderTime.setEndTime(endTime);
        CustomerOrderTimeJob customerOrderTimeJob = new CustomerOrderTimeJob();
        //创建新的job
        quartzManager.addJobByCronExpressions(customerOrderTimeFormBean.getInstId() + "jobName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "jobGroupName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "triggerName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "triggerGroupName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeJob,
                "0 0 " + customerOrderTimeFormBean.getStartTime().split(":")[0] + " * * ? *",
                new Date(), null,
                customerOrderTimeFormBean.getInstId(), customerOrderTimeFormBean.getJobType());
        this.customerOrderTimeService.save(customerOrderTime);
    }

    public void modify(CustomerOrderTimeFormBean customerOrderTimeFormBean) throws BizException {
        long time = DateUtils.toDate(customerOrderTimeFormBean.getStartTime(), DateUtils.HH_MM_SS).getTime();
        if (time % 3600000 != 0) {
            throw new BizException(ErrorCode.INST_JOB_TIME_ERROR);
        }
        int startHour = DateUtils.getHourOfDay(DateUtils.toDate(customerOrderTimeFormBean.getStartTime(), DateUtils.HH_MM_SS));
        if (customerOrderTimeFormBean.getJobType() == JobType.DAY_JOB.getValue() && startHour > 11) {
            throw new BizException(ErrorCode.INST_JOB_DAY_JOB_ERROR);
        }
        if (customerOrderTimeFormBean.getJobType() == JobType.NIGHT_JOB.getValue() && startHour < 12) {
            throw new BizException(ErrorCode.INST_JOB_NIGHT_JOB_ERROR);
        }
        CustomerOrderTime customerOrderTime = new CustomerOrderTime();
        customerOrderTime.setId(customerOrderTimeFormBean.getId());
        customerOrderTime.setInstId(customerOrderTimeFormBean.getInstId());
        customerOrderTime.setJobType(customerOrderTimeFormBean.getJobType());
        Date startTime = DateUtils.toDate(customerOrderTimeFormBean.getStartTime(),
                DateUtils.HH_MM_SS);
        Date endTime = DateUtils.toDate(customerOrderTimeFormBean.getEndTime(),
                DateUtils.HH_MM_SS);
        customerOrderTime.setStartTime(startTime);
        customerOrderTime.setEndTime(endTime);

        CustomerOrderTimeJob customerOrderTimeJob = new CustomerOrderTimeJob();
        //先删除之前的job
        quartzManager.deleteJob(customerOrderTimeFormBean.getInstId() + "jobName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "jobGroupName" + customerOrderTimeFormBean.getJobType());
//        quartzManager.modifyJobTimeByCronExpressions(customerOrderTimeFormBean.getInstId() + "triggerName" + customerOrderTimeFormBean.getJobType(),
//                customerOrderTimeFormBean.getInstId() + "triggerGroupName" + customerOrderTimeFormBean.getJobType(),
//                "0 0 " + customerOrderTimeFormBean.getStartTime().split(":")[0] + "," + customerOrderTimeFormBean.getEndTime().split(":")[0] + " * * ? *",
//                new Date(), null);
        //创建新的job
        quartzManager.addJobByCronExpressions(customerOrderTimeFormBean.getInstId() + "jobName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "jobGroupName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "triggerName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "triggerGroupName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeJob,
                "0 0 " + customerOrderTimeFormBean.getStartTime().split(":")[0] + " * * ? *",
//                "0 * * * * ?",
                new Date(), null,
                customerOrderTimeFormBean.getInstId(), customerOrderTimeFormBean.getJobType());
        this.customerOrderTimeService.update(customerOrderTime);
    }

//    public static void main(String[] args) {
//        CustomerOrderTimeJob customerOrderTimeJob = new CustomerOrderTimeJob();
//        QuartzManager quartzManager = new QuartzManager();
//
//        quartzManager.deleteJob(1 + "jobName" + 1,
//                1 + "jobGroupName" + 1);
//
//        quartzManager.addJobByCronExpressions(1 + "jobName" + 1,
//                1 + "jobGroupName" + 1,
//                1 + "triggerName" + 1,
//                1 + "triggerGroupName" + 1,
//                customerOrderTimeJob,
//            "* * * * * ? *",
////                "0 0 " + customerOrderTimeFormBean.getStartTime().split(":")[0] + "," + customerOrderTimeFormBean.getEndTime().split(":")[0] + " * * ? *",
//                new Date(), null,
//                1, 2);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        quartzManager.deleteJob(1 + "jobName" + 1,
//                1 + "jobGroupName" + 1);
//    }
}
