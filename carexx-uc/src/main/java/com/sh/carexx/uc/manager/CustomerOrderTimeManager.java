package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.order.CustomerOrderTimeFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.staff.JobType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.quartz.QuartzManager;
import com.sh.carexx.common.util.DateUtils;
import com.sh.carexx.model.uc.CustomerOrder;
import com.sh.carexx.model.uc.CustomerOrderSchedule;
import com.sh.carexx.model.uc.CustomerOrderTime;
import com.sh.carexx.uc.service.CustomerOrderScheduleService;
import com.sh.carexx.uc.service.CustomerOrderService;
import com.sh.carexx.uc.service.CustomerOrderTimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CustomerOrderTimeManager {

    private Logger log = Logger.getLogger(CustomerOrderTimeManager.class);

    @Autowired
    private CustomerOrderTimeService customerOrderTimeService;
    @Autowired
    private CustomerOrderService customerOrderService;
    @Autowired
    private CustomerOrderManager customerOrderManager;
    @Autowired
    private CustomerOrderScheduleService customerOrderScheduleService;
    @Autowired
    private CustomerOrderScheduleManager customerOrderScheduleManager;
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
        this.customerOrderTimeService.save(customerOrderTime);
        //创建新的job
        quartzManager.addJobByCronExpressions(customerOrderTimeFormBean.getInstId() + "jobName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "jobGroupName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "triggerName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "triggerGroupName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeJob,
                "0 0 " + customerOrderTimeFormBean.getStartTime().split(":")[0] + " * * ? *",
                new Date(), null,
                customerOrderTimeFormBean.getInstId(), customerOrderTimeFormBean.getJobType());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
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
        this.customerOrderTimeService.update(customerOrderTime);

        //修改之前订单的排班时间
        List<CustomerOrder> customerOrderList = customerOrderService.getAllOrderByInstId(customerOrderTimeFormBean.getInstId());
        String dayTime = "";
        String nightTime = "";
        if (customerOrderTimeFormBean.getJobType() == JobType.DAY_JOB.getValue()) {
            dayTime = customerOrderTimeFormBean.getStartTime();
            nightTime = customerOrderTimeFormBean.getEndTime();
        }
        if (customerOrderTimeFormBean.getJobType() == JobType.NIGHT_JOB.getValue()) {
            dayTime = customerOrderTimeFormBean.getEndTime();
            nightTime = customerOrderTimeFormBean.getStartTime();
        }
        //循环所有的订单
        for (CustomerOrder customerOrder : customerOrderList) {
            SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //开始时间
            int orderStartHour = Integer.parseInt(sdfHour.format(customerOrder.getServiceStartTime()));
            String startDate = sdf.format(customerOrder.getServiceStartTime());
            String newStartDate;
            if (orderStartHour >= 12) {
                newStartDate = startDate.split(" ")[0] + " " + nightTime;
            } else {
                newStartDate = startDate.split(" ")[0] + " " + dayTime;
            }
            //结束时间 可能为空
            String newEndDate = "";
            if (customerOrder.getServiceEndTime() != null) {
                int orderEndhour = Integer.parseInt(sdfHour.format(customerOrder.getServiceEndTime()));
                String endDate = sdf.format(customerOrder.getServiceEndTime());
                if (orderEndhour >= 12) {
                    newEndDate = endDate.split(" ")[0] + " " + nightTime;
                } else {
                    newEndDate = endDate.split(" ")[0] + " " + dayTime;
                }
            }
            log.info("newStartDate" + newStartDate);
            log.info("newEndDate" + newEndDate);
            customerOrderManager.modifyServiceTime(customerOrder.getOrderNo(),
                    DateUtils.toDate(newStartDate, DateUtils.YYYY_MM_DD_HH_MM_SS),
                    DateUtils.toDate(newEndDate, DateUtils.YYYY_MM_DD_HH_MM_SS));
            //通过订单号获取排班信息
            List<CustomerOrderSchedule> customerOrderScheduleList = customerOrderScheduleService.getByOrderNo(customerOrder.getOrderNo());
            for (CustomerOrderSchedule customerOrderSchedule : customerOrderScheduleList) {
                int scheduleStartHour = Integer.parseInt(sdfHour.format(customerOrderSchedule.getServiceStartTime()));
                int scheduleEndHour = Integer.parseInt(sdfHour.format(customerOrderSchedule.getServiceEndTime()));
                String scheduleStartTime = sdf.format(customerOrderSchedule.getServiceStartTime());
                String scheduleEndTime = sdf.format(customerOrderSchedule.getServiceEndTime());
                String newScheduleStartDate;
                String newScheduleEndDate;
                if (scheduleStartHour >= 12) {
                    newScheduleStartDate = scheduleStartTime.split(" ")[0] + " " + nightTime;
                } else {
                    newScheduleStartDate = scheduleStartTime.split(" ")[0] + " " + dayTime;
                }
                if (scheduleEndHour >= 12) {
                    newScheduleEndDate = scheduleEndTime.split(" ")[0] + " " + nightTime;
                } else {
                    newScheduleEndDate = scheduleEndTime.split(" ")[0] + " " + dayTime;
                }
                log.info("newScheduleStartDate" + newScheduleStartDate);
                log.info("newScheduleEndDate" + newScheduleEndDate);
                customerOrderScheduleManager.modifyServiceTime(customerOrder.getId(),
                        DateUtils.toDate(newScheduleStartDate, DateUtils.YYYY_MM_DD_HH_MM_SS),
                        DateUtils.toDate(newScheduleEndDate, DateUtils.YYYY_MM_DD_HH_MM_SS));
            }
        }

        CustomerOrderTimeJob customerOrderTimeJob = new CustomerOrderTimeJob();
        //先删除之前的job
        quartzManager.deleteJob(customerOrderTimeFormBean.getInstId() + "jobName" + customerOrderTimeFormBean.getJobType(),
                customerOrderTimeFormBean.getInstId() + "jobGroupName" + customerOrderTimeFormBean.getJobType());
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
    }

}
