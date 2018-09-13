package com.sh.carexx.mapp.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.usermsg.UserMsgFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.SessionHolder;

@RestController
@RequestMapping(value = "/msg")
public class UserMsgController extends BaseController {
	@RequestMapping(value = "/add")
	public BasicRetVal add(@Valid UserMsgFormBean userMsgFormBean, BindingResult bindingResult) {
		userMsgFormBean.setUserId(SessionHolder.getUserId());
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.addUserMsg(userMsgFormBean);
	}
	
	@RequestMapping(value = "/all/{userId}")
	public String queryAllUserMsg(@PathVariable("userId") Integer userId) {
		return this.ucServiceClient.queryAllUserMsg(userId);
	}
	
	@RequestMapping(value = "/read/{msgId}/{userId}")
	public BasicRetVal read(@PathVariable("msgId") Long msgId, @PathVariable("userId") Integer userId) {
		return this.ucServiceClient.readUserMsg(msgId, userId);
	}
	
	@RequestMapping(value = "/delete/{ids}")
	public BasicRetVal delete(@PathVariable("ids") String ids) {
		return this.ucServiceClient.deleteUserMsg(ids);
	}
}
