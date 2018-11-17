package com.sh.carexx.uc.service;

import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CustomerOrderTime;

import java.util.List;

public interface CustomerOrderTimeService {
    CustomerOrderTime getById (Long id);

    List<CustomerOrderTime> getByInstId(Integer instId);

    void save(CustomerOrderTime customerOrderTime) throws BizException;

    void update(CustomerOrderTime customerOrderTime) throws BizException;
}
