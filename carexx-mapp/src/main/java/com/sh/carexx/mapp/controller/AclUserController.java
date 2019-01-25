package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acluser")
public class AclUserController extends BaseController {

	@RequestMapping(value = "/get_userId")
	public String getuserId() {
		Integer userAcctId = this.getCurrentUserOAuth().getUserAcctId();
		return this.ucServiceClient.getuserId(userAcctId);
	}
}
