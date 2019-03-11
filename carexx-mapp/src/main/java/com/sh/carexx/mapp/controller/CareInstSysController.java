package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/careinstsys")
public class CareInstSysController extends BaseController {

	@RequestMapping(value = "/list_all/{instId}")
	public String queryAllAvailable(@PathVariable("instId") Integer instId) {
		return this.ucServiceClient.queryAllAvailableCareInstSys(instId);
	}

}
