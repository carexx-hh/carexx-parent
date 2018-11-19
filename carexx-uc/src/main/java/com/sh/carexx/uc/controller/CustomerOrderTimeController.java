package com.sh.carexx.uc.controller;

import com.sh.carexx.bean.order.CustomerOrderTimeFormBean;
import com.sh.carexx.bean.order.CustomerOrderTimeQueryFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.common.web.PagerBean;
import com.sh.carexx.uc.manager.CustomerOrderTimeManager;
import com.sh.carexx.uc.service.CustomerOrderTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customerordertime")
public class CustomerOrderTimeController {

    @Autowired
    private CustomerOrderTimeManager customerOrderTimeManager;

    @Autowired
    private CustomerOrderTimeService customerOrderTimeService;

    @RequestMapping(value = "/get_by_instId/{instId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getByInstId(@PathVariable("instId") Integer instId) {
        return new DataRetVal(CarexxConstant.RetCode.SUCCESS,this.customerOrderTimeService.getByInstId(instId)).toJSON();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicRetVal add(@RequestBody CustomerOrderTimeFormBean customerOrderTimeFormBean) {
        try {
            this.customerOrderTimeManager.add(customerOrderTimeFormBean);
        } catch (BizException e) {
            return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
        }
        return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String queryForList(@RequestBody CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean) {
        Integer totalNum = this.customerOrderTimeService.getCustomerOrderTimeCount(customerOrderTimeQueryFormBean);
        List<Map<?, ?>> result = null;
        if (totalNum > 0) {
            result = this.customerOrderTimeService.queryCustomerOrderTimeList(customerOrderTimeQueryFormBean);
        }
        return new DataRetVal(CarexxConstant.RetCode.SUCCESS, new PagerBean(totalNum, result)).toJSON();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicRetVal modify(@RequestBody CustomerOrderTimeFormBean customerOrderTimeFormBean) {
        try {
            this.customerOrderTimeManager.modify(customerOrderTimeFormBean);
        } catch (BizException e) {
            return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
        }
        return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
    }
}
