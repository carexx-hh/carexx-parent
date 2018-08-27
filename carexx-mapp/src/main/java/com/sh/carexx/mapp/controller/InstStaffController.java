package com.sh.carexx.mapp.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;

@RestController
@RequestMapping("/inststaff")
public class InstStaffController extends BaseController {

	@RequestMapping(value = "/serviceNum")
	public String queryInstStaffServiceNum(@Valid CustomerOrderQueryFormBean customerOrderQueryFormBean) {

		return this.ucServiceClient.queryInstStaffServiceNum(customerOrderQueryFormBean);
	}
}
