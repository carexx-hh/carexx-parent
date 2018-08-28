package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.order.MappCustomerOrderScheduleFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customerorderschedule")
public class CustomerOrderScheduleController extends BaseController {

	@RequestMapping(value = "/all_schedule/{orderNo}")
	public String queryOrderScheduleByOrderNo(@PathVariable("orderNo") String orderNo) {
		return this.ucServiceClient.queryOrderScheduleByOrderNo(orderNo);
	}

	@RequestMapping(value = "near_schedule/{orderNo}")
	public String queryNearScheduleByOrderNo(@PathVariable("orderNo") String orderNo){
		return this.ucServiceClient.queryNearScheduleByOrderNo(orderNo);
	}

	@RequestMapping("/mapp_add")
	public BasicRetVal mappAdd(@Valid MappCustomerOrderScheduleFormBean mappCustomerOrderScheduleFormBean,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.mappAddCustomerOrderSchedule(mappCustomerOrderScheduleFormBean);
	}
}
