package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inststaff")
public class InstStaffController extends BaseController {

	@RequestMapping(value = "/serviceNum/{orderNo}")
	public String queryInstStaffServiceNum(@PathVariable("orderNo") String orderNo) {
		return this.ucServiceClient.queryInstStaffServiceNum(orderNo);
	}
}
