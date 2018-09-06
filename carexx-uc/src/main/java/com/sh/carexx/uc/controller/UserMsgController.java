package com.sh.carexx.uc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.usermsg.UserMsgFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.uc.manager.UserMsgManager;
import com.sh.carexx.uc.service.UserMsgService;

@RestController
@RequestMapping("/msg")
public class UserMsgController {
	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private UserMsgManager userMsgManager;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal add(@RequestBody UserMsgFormBean userMsgFormBean) {
		try {
			this.userMsgManager.add(userMsgFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/all/{userId}", method = RequestMethod.GET)
	public String queryAllUserMsg(@PathVariable("userId") Integer userId) {
		List<Map<?,?>> resultList = null;
		resultList = this.userMsgService.queryAllUserMsg(userId);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, resultList).toJSON();
	}

	@RequestMapping(value = "/read/{msgId}/{userId}", method = RequestMethod.GET)
	public BasicRetVal read(@PathVariable("msgId") Long msgId, @PathVariable("userId") Integer userId) {
		try {
			this.userMsgManager.read(msgId, userId);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public BasicRetVal delete(@PathVariable("id") Long id) {
		try {
			this.userMsgManager.delete(id);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

}
