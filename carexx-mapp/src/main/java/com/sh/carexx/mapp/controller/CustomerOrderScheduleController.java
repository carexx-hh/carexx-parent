package com.sh.carexx.mapp.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.order.MappCustomerOrderScheduleFormBean;
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
	public BasicRetVal mappAdd(@Valid MappCustomerOrderScheduleFormBean mappCustomerOrderScheduleFormBean,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		mappCustomerOrderScheduleFormBean.setUserId(this.getCurrentUserOAuth().getUserAcctId());
		return this.ucServiceClient.mappAddCustomerOrderSchedule(mappCustomerOrderScheduleFormBean);
	}

	@RequestMapping(value = "/accept_schedule/{orderNo}")
	public BasicRetVal acceptSchedule(@PathVariable("orderNo") String orderNo) {
		return this.ucServiceClient.acceptSchedule(orderNo);
	}

	@RequestMapping(value = "/refused_schedule/{orderNo}")
	public BasicRetVal refusedSchedule(@PathVariable("orderNo") String orderNo) {
		return this.ucServiceClient.refusedSchedule(orderNo);
	}

	@RequestMapping(value = "/order_statistics/{serviceEndTime}")
	public String queryOrderScheduleStatisticsByStaffId(@PathVariable("serviceEndTime") String serviceEndTime) {
		Integer staffId = this.getCurrentUserOAuth().getStaffId();
		return this.ucServiceClient.queryOrderScheduleStatisticsByStaffId(staffId, serviceEndTime);
	}
}
