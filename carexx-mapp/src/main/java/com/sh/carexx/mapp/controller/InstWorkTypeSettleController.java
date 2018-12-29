package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instworktypesettle")
public class InstWorkTypeSettleController extends BaseController {

	@RequestMapping(value = "/list_all/{workTypeId}/{instId}")
	public String queryAllAvailable(@PathVariable("workTypeId") Integer workTypeId,
			@PathVariable("instId") Integer instId) {
		return this.ucServiceClient.queryAllInstWorkTypeSettle(instId, workTypeId);
	}
}
