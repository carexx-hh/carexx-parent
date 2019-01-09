package com.sh.carexx.mapp.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.user.ApplyCertificationFormBean;
import com.sh.carexx.bean.user.NursingSupervisorLoginFormBean;
import com.sh.carexx.bean.user.OAuthLoginFormBean;
import com.sh.carexx.bean.user.PatientLoginFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.enums.Identity;
import com.sh.carexx.common.enums.user.IdentityType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.JSONUtils;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.mapp.wechat.WechatManager;
import com.sh.carexx.model.uc.UserInfo;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WechatManager wechatManager;

	@RequestMapping("/patient_login")
	public String patientLogin(PatientLoginFormBean patientLoginFormBean, BindingResult bindingResult) {
		try {
			Map<String, Object> oAuthInfo = this.wechatManager.getWxAppletOAuthInfo(patientLoginFormBean.getCode(),
					Identity.PATIENT.getValue());
			patientLoginFormBean.setOpenId(String.valueOf(oAuthInfo.get("openid")));

		} catch (BizException e) {
			this.logger.error("微信登录失败", e);
		}
		/*patientLoginFormBean.setOpenId("1");*/
		return this.ucServiceClient.patientLogin(patientLoginFormBean);
	}

	@RequestMapping("/nursing_supervisor_login")
	public String nursingSupervisorLogin(NursingSupervisorLoginFormBean nursingSupervisorLoginFormBean,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT).toJSON();
		}
		try {
			Map<String, Object> oAuthInfo = this.wechatManager.getWxAppletOAuthInfo(
					nursingSupervisorLoginFormBean.getCode(), Identity.NURSING_SUPERVISOR.getValue());
			nursingSupervisorLoginFormBean.setOpenId(String.valueOf(oAuthInfo.get("openid")));
		} catch (BizException e) {
			this.logger.error("微信登录失败", e);
		}
		/*nursingSupervisorLoginFormBean.setOpenId("1");*/
		return this.ucServiceClient.nursingSupervisorLogin(nursingSupervisorLoginFormBean);
	}

	@RequestMapping("/caregivers_login")
	public String caregiversLogin(String code) {
		String openId = null;
		try {
			Map<String, Object> oAuthInfo = this.wechatManager.getWxAppletOAuthInfo(code,
					Identity.CAREGIVERS.getValue());
			openId = String.valueOf(oAuthInfo.get("openid")); // 用户唯一标识
			Byte identityType = IdentityType.WECHAT.getValue();
			return this.ucServiceClient.CaregiversLogin(identityType, openId);
		} catch (BizException e) {
			this.logger.error("微信登录失败", e);
		}
		return null;

		/*
		 * openId = "1"; // 用户唯一标识 Byte identityType =
		 * IdentityType.WECHAT.getValue(); return
		 * this.ucServiceClient.CaregiversLogin(identityType, openId);
		 */
	}

	@RequestMapping("/apply_certification")
	public BasicRetVal applyCertification(ApplyCertificationFormBean applyCertificationFormBean,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.applyCertification(applyCertificationFormBean);
	}
}