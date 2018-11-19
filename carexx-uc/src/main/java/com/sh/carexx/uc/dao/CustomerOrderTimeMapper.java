package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.order.CustomerOrderTimeQueryFormBean;
import com.sh.carexx.model.uc.CustomerOrderTime;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerOrderTimeMapper {

    CustomerOrderTime selectById(Long id);

    List<CustomerOrderTime> selectByInstId(Integer instId);

    CustomerOrderTime selectJobTypeExistence(@Param("instId") Integer instId, @Param("jobType") Byte jobType);

    Integer selectCustomerOrderTimeCount(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    List<Map<?,?>> selectCustomerOrderTimeList(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    int insert(CustomerOrderTime customerOrderTime);

    int update(CustomerOrderTime customerOrderTime);
}