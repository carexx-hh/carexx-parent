package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.order.CustomerOrderTimeQueryFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CustomerOrderTime;
import com.sh.carexx.uc.dao.CustomerOrderTimeMapper;
import com.sh.carexx.uc.service.CustomerOrderTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerOrderTimeServiceImpl implements CustomerOrderTimeService {

    @Autowired
    private CustomerOrderTimeMapper customerOrderTimeMapper;

    @Override
    public CustomerOrderTime getById(Long id) {
        return this.customerOrderTimeMapper.selectById(id);
    }

    @Override
    public List<CustomerOrderTime> getByInstId(Integer instId) {
        return this.customerOrderTimeMapper.selectByInstId(instId);
    }

    @Override
    public CustomerOrderTime queryJobTypeExistence(Integer instId, Byte jobType) {
        return this.customerOrderTimeMapper.selectJobTypeExistence(instId, jobType);
    }

    @Override
    public Integer getCustomerOrderTimeCount(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean) {
        return this.customerOrderTimeMapper.selectCustomerOrderTimeCount(customerOrderTimeQueryFormBean);
    }

    @Override
    public List<Map<?, ?>> queryCustomerOrderTimeList(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean) {
        return this.customerOrderTimeMapper.selectCustomerOrderTimeList(customerOrderTimeQueryFormBean);
    }

    @Override
    public void save(CustomerOrderTime customerOrderTime) throws BizException {
        int rows = 0;
        try {
            rows = this.customerOrderTimeMapper.insert(customerOrderTime);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
        if (rows != 1) {
            throw new BizException(ErrorCode.DB_ERROR);
        }
    }

    @Override
    public void update(CustomerOrderTime customerOrderTime) throws BizException {
        int rows = 0;
        try {
            rows = this.customerOrderTimeMapper.update(customerOrderTime);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
        if (rows != 1) {
            throw new BizException(ErrorCode.DB_ERROR);
        }
    }
}
