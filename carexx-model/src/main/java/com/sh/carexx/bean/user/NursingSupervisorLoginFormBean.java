package com.sh.carexx.bean.user;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.util.ValidUtils;

public class NursingSupervisorLoginFormBean extends BasicFormBean {

	@NotBlank
	private String acctNo;

	@NotBlank
	@Size(min = 6, max = 20)
	private String loginPass;
	
	@NotBlank
	private String code;
	
	private String openId;
	
	private String nickname;
	
	private String avatar;
	
	@Pattern(regexp = "[0,1,2]")
	private String sex;
	
	private String region;

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Byte getSex() {
		if (ValidUtils.isInteger(sex)) {
			return Byte.parseByte(sex);
		}
		return null;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
}
