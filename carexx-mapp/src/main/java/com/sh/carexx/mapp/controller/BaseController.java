package com.sh.carexx.mapp.controller;

import com.sh.carexx.api.client.UCServiceClient;
import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.enums.TerChnl;
import com.sh.carexx.common.util.WebUtils;
import com.sh.carexx.common.web.SessionHolder;
import com.sh.carexx.model.uc.UserInfo;
import com.sh.carexx.model.uc.UserOAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
	@Autowired
	protected UCServiceClient ucServiceClient;

	public UserInfo getCurrentUser() {
		return this.ucServiceClient.getUserInfo(SessionHolder.getUserId());
	}
	
	public UserOAuth getCurrentUserOAuth() {
		return this.ucServiceClient.getUserOAuth(SessionHolder.getUserId());
	}

	protected String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	protected void fillFormBean(BasicFormBean formBean) {
		if (formBean.getTerChnl() == null) {
			formBean.setTerChnl(String.valueOf(TerChnl.WXAPPLET.getValue()));
		}
		formBean.setIp(WebUtils.getIpAddr(this.getRequest()));
	}
}
