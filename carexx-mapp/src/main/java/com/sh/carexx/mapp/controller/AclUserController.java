package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acluser")
public class AclUserController extends BaseController {

	@RequestMapping(value = "/get_userId/{userId}")
	public String getuserId(@PathVariable("userId") Integer userId) {
		return this.ucServiceClient.getuserId(userId);
	}
}
