package com.sh.carexx.admin.controller;

import com.sh.carexx.bean.order.CustomerOrderTimeFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customerordertime")
public class CustomerOrderTimeController extends BaseController {

    @RequestMapping(value = "/get_by_instId")
    public String getByInstId() {
        Integer instId = this.getCurrentUser().getInstId();
        return this.ucServiceClient.getCustomerordertimeByInstId(instId);
    }

    @RequestMapping(value = "/add")
    public BasicRetVal add(@Valid CustomerOrderTimeFormBean customerOrderTimeFormBean, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
        }
        return this.ucServiceClient.addCustomerordertime(customerOrderTimeFormBean);
    }

    @RequestMapping(value = "/modify")
    public BasicRetVal modify(@Valid CustomerOrderTimeFormBean customerOrderTimeFormBean, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
        }
        return this.ucServiceClient.modifyCustomerordertime(customerOrderTimeFormBean);
    }
}
