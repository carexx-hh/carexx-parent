package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/msg")
public class UserMsgController extends BaseController {

	@RequestMapping(value = "/all/{userId}")
	public String queryAllUserMsg(@PathVariable("userId") Integer userId) {
		return this.ucServiceClient.queryAllUserMsg(userId);
	}
}
