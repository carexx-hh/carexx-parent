package com.sh.carexx.uc.dao;

import com.sh.carexx.model.uc.CustomerOrderTime;

import java.util.List;

public interface CustomerOrderTimeMapper {

    CustomerOrderTime selectById(Long id);

    List<CustomerOrderTime> selectByInstId(Integer instId);

    int insert(CustomerOrderTime customerOrderTime);

    int update(CustomerOrderTime customerOrderTime);
}