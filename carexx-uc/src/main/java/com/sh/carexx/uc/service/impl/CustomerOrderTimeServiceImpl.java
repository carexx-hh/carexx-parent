package com.sh.carexx.uc.service.impl;

import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CustomerOrderTime;
import com.sh.carexx.uc.dao.CustomerOrderTimeMapper;
import com.sh.carexx.uc.service.CustomerOrderTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
