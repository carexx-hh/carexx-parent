package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instworktypesettle")
public class InstWorkTypeSettleController extends BaseController {

	@RequestMapping(value = "/list_all/{workTypeId}")
	public String queryAllAvailable(@PathVariable("workTypeId") Integer workTypeId) {
		Integer instId = this.getCurrentUserOAuth().getInstId();
		return this.ucServiceClient.queryAllInstWorkTypeSettle(instId, workTypeId);
	}
}
