package com.sh.carexx.uc.service;

import com.sh.carexx.bean.order.CustomerOrderTimeQueryFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CustomerOrderTime;

import java.util.List;
import java.util.Map;

public interface CustomerOrderTimeService {
    CustomerOrderTime getById (Long id);

    List<CustomerOrderTime> getByInstId(Integer instId);

    CustomerOrderTime queryJobTypeExistence(Integer instId,Byte jobType);

    Integer getCustomerOrderTimeCount(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    List<Map<?,?>> queryCustomerOrderTimeList(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    void save(CustomerOrderTime customerOrderTime) throws BizException;

    void update(CustomerOrderTime customerOrderTime) throws BizException;
}
