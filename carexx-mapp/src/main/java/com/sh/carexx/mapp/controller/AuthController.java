package com.sh.carexx.mapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.user.OAuthLoginFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.enums.Identity;
import com.sh.carexx.common.enums.user.IdentityType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.JSONUtils;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.mapp.wechat.WechatManager;
import com.sh.carexx.model.uc.UserInfo;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.application.domain}")
	private String domain;

	@Autowired
	private WechatManager wechatManager;

	@RequestMapping("/login")
	public String login(String code, UserInfo userInfo) {
		String openId = null;
		String token = null;
		try {
			Map<String, Object> oAuthInfo = this.wechatManager.getWxAppletOAuthInfo(code, Identity.PATIENT.getValue());
			openId = String.valueOf(oAuthInfo.get("openid")); // 用户唯一标识
			OAuthLoginFormBean oAuthLoginFormBean = new OAuthLoginFormBean();
			oAuthLoginFormBean.setIdentityType(IdentityType.WECHAT.getValue());
			oAuthLoginFormBean.setIdentifier(openId);
			oAuthLoginFormBean.setIdentity(Identity.PATIENT.getValue());
			oAuthLoginFormBean.setNickname(userInfo.getNickname());
			oAuthLoginFormBean.setAvatar(userInfo.getAvatar());
			oAuthLoginFormBean.setSex(userInfo.getSex());
			oAuthLoginFormBean.setRegion(userInfo.getRegion());

			String retVal = this.ucServiceClient.doOAuthLogin(oAuthLoginFormBean);
			DataRetVal dataRetVal = JSONUtils.toBean(retVal, DataRetVal.class);
			if (dataRetVal != null && CarexxConstant.RetCode.SUCCESS == dataRetVal.getCode()) {
				token = String.valueOf(dataRetVal.getData());
			}
		} catch (BizException e) {
	        this.logger.error("微信登录失败", e);
	    }
		Map<String, Object> ReturnValue = new HashMap<>();
		ReturnValue.put("token", token);
		ReturnValue.put("openId", openId);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, ReturnValue).toJSON();
	}
}