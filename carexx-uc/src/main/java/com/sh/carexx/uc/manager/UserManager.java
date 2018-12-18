package com.sh.carexx.uc.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sh.carexx.bean.user.OAuthLoginFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.UseStatus;
import com.sh.carexx.common.enums.staff.CertificationStatus;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.Radix32Utils;
import com.sh.carexx.model.uc.InstStaff;
import com.sh.carexx.model.uc.UserInfo;
import com.sh.carexx.model.uc.UserOAuth;
import com.sh.carexx.uc.service.InstStaffService;
import com.sh.carexx.uc.service.UserOAuthService;
import com.sh.carexx.uc.service.UserService;

@Service
public class UserManager {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private UserOAuthService userOAuthService;
	@Autowired
	private SmsManager smsManager;
	@Autowired
	private InstStaffService instStaffService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public String doOAuthLogin(OAuthLoginFormBean oAuthLoginFormBean) throws BizException {
		UserOAuth oriUserOAuth = this.userOAuthService.getByIdentityInfo(oAuthLoginFormBean.getIdentityType(),
				oAuthLoginFormBean.getIdentifier());
		UserInfo userInfo = new UserInfo();
		userInfo.setNickname(oAuthLoginFormBean.getNickname());
		userInfo.setAvatar(oAuthLoginFormBean.getAvatar());
		userInfo.setSex(oAuthLoginFormBean.getSex());
		userInfo.setRegion(oAuthLoginFormBean.getRegion());

		UserOAuth userOAuth = new UserOAuth();
		userOAuth.setIdentityType(oAuthLoginFormBean.getIdentityType());
		userOAuth.setIdentifier(oAuthLoginFormBean.getIdentifier());
		userOAuth.setIdentity(oAuthLoginFormBean.getIdentity());
		userOAuth.setCredential(oAuthLoginFormBean.getCredential());
		userOAuth.setIdentityStatus(UseStatus.ENABLED.getValue());
		if (oriUserOAuth == null) {
			this.userService.save(userInfo);
			userOAuth.setUserId(userInfo.getId());
			this.userOAuthService.save(userOAuth);
		} else {
			userInfo.setId(oriUserOAuth.getUserId());
			this.userService.update(userInfo);
			userOAuth.setId(oriUserOAuth.getId());
			this.userOAuthService.update(userOAuth);
		}
		String token = Radix32Utils.encode(userInfo.getId());
		try {
			this.redisTemplate.opsForValue().set(CarexxConstant.CachePrefix.CAREXX_AUTH_TOKEN + token, token);
		} catch (Exception e) {
			throw new BizException(ErrorCode.SYS_ERROR, e);
		}
		return token;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public Map<String, Object> CaregiversLogin(Byte identityType, String openId) throws BizException {
		Map<String, Object> resultMap = new HashMap<>();
		Byte certificationStatus = 0;
		UserOAuth userOAuth = this.userOAuthService.getByIdentityInfo(identityType, openId);
		if (userOAuth.getStaffId() != null) {
			InstStaff instStaff = instStaffService.getById(userOAuth.getStaffId());
			certificationStatus = instStaff.getCertificationStatus();
			if (certificationStatus == CertificationStatus.HAS_CERTIFICATION.getValue()) {
				String token = Radix32Utils.encode(userOAuth.getUserId());
				try {
					this.redisTemplate.opsForValue().set(CarexxConstant.CachePrefix.CAREXX_AUTH_TOKEN + token, token);
				} catch (Exception e) {
					throw new BizException(ErrorCode.SYS_ERROR, e);
				}

				resultMap.put("certificationStatus", certificationStatus);
				resultMap.put("token", token);
				resultMap.put("openId", openId);
				resultMap.put("staffId", userOAuth.getStaffId());
				return resultMap;
			} else {
				resultMap.put("certificationStatus", certificationStatus);
				return resultMap;
			}
		} else {
			resultMap.put("certificationStatus", CertificationStatus.NO_CERTIFICATION.getValue());
			return resultMap;
		}

	}

	public String getOAuthUserId(String token) {
		try {
			return this.redisTemplate.opsForValue().get(CarexxConstant.CachePrefix.CAREXX_AUTH_TOKEN + token);
		} catch (Exception e) {
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public void modifyBindMobile(Integer id, String mobile, String verifyCode) throws BizException {
		this.smsManager.checkSmsVerifyCode(mobile, verifyCode);
		this.userService.updateMobileById(id, mobile);
	}
}
