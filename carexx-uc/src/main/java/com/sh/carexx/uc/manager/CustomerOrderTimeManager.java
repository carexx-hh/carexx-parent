package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.order.CustomerOrderTimeFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
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

    public void add(CustomerOrderTimeFormBean customerOrderTimeFormBean) throws BizException {
        CustomerOrderTime customerOrderTime = customerOrderTimeService.queryJobTypeExistence(customerOrderTimeFormBean.getInstId(),
                customerOrderTimeFormBean.getJobType());
        if(customerOrderTime != null){
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
        this.customerOrderTimeService.save(customerOrderTime);
    }

    public void modify(CustomerOrderTimeFormBean customerOrderTimeFormBean) throws BizException {
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
    }
}
