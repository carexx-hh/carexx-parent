package com.sh.carexx.bean.user;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.util.ValidUtils;

public class ApplyCertificationFormBean extends BasicFormBean {
	
	@NotBlank
	private String openId;
	
	private String nickname;
	
	private String avatar;
	
	@Pattern(regexp = "[0,1,2]")
	private String sex;
	
	private String region;

	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.MOBILE)
	@Size(max = 11)
	private String phone;
	
	@NotBlank
	@Size(max = 6)
	private String verifyCode;
	
	@NotBlank
	@Pattern(regexp = CarexxConstant.Regex.ID_CARD_NO)
	private String idNo;
	
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
}
