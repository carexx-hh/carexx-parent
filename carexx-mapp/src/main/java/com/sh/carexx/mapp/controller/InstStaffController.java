package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.staff.InstStaffQueryFormBean;

@RestController
@RequestMapping("/inststaff")
public class InstStaffController extends BaseController {

	@RequestMapping(value = "/serviceNum")
	public String queryInstStaffServiceNum(InstStaffQueryFormBean instStaffQueryFormBean) {
		return this.ucServiceClient.queryInstStaffServiceNum(instStaffQueryFormBean);
	}
}
