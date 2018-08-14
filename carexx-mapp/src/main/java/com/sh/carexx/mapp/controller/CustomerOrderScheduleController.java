package com.sh.carexx.mapp.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.order.CustomerOrderScheduleFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;

@RestController
@RequestMapping("/customerorderschedule")
public class CustomerOrderScheduleController extends BaseController {

	@RequestMapping(value = "/all_schedule/{orderNo}")
	public String queryOrderScheduleByOrderNo(@PathVariable("orderNo") String orderNo) {
		return this.ucServiceClient.queryOrderScheduleByOrderNo(orderNo);
	}
	
	@RequestMapping("/mapp_add")
	public BasicRetVal mappAdd(@Valid CustomerOrderScheduleFormBean customerOrderScheduleFormBean,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.mappAddCustomerOrderSchedule(customerOrderScheduleFormBean);
	}
}
