package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.acl.AclLoginFormBean;
import com.sh.carexx.bean.user.NursingSupervisorLoginFormBean;
import com.sh.carexx.bean.user.OAuthLoginFormBean;
import com.sh.carexx.bean.user.PatientLoginFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.Identity;
import com.sh.carexx.common.enums.UseStatus;
import com.sh.carexx.common.enums.staff.CertificationStatus;
import com.sh.carexx.common.enums.user.IdentityType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.Radix32Utils;
import com.sh.carexx.model.uc.InstStaff;
import com.sh.carexx.model.uc.UserAccount;
import com.sh.carexx.model.uc.UserInfo;
import com.sh.carexx.model.uc.UserOAuth;
import com.sh.carexx.uc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserManager {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private AclUserAcctService aclUserAcctService;
	@Autowired
	private UserOAuthService userOAuthService;
	@Autowired
	private SmsManager smsManager;
	@Autowired
	private InstStaffService instStaffService;
	@Autowired
	private AclUserManager aclUserManager;
	@Autowired
	private UserAccountService userAccountService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public UserInfo add(OAuthLoginFormBean oAuthLoginFormBean) throws BizException {
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
			if(userOAuth.getIdentity() == Identity.PATIENT.getValue()){
				UserAccount userAccount = new UserAccount();
				userAccount.setUserId(userInfo.getId());
				userAccount.setAccountBalance(new BigDecimal(0));
				this.userAccountService.save(userAccount);
			}
		} else {
			userInfo.setId(oriUserOAuth.getUserId());
			this.userService.update(userInfo);
			userOAuth.setId(oriUserOAuth.getId());
			this.userOAuthService.update(userOAuth);
		}
		return userInfo;
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public String doOAuthLogin(OAuthLoginFormBean oAuthLoginFormBean) throws BizException {
		UserInfo userInfo = this.add(oAuthLoginFormBean);
		String token = Radix32Utils.encode(userInfo.getId());
		try {
			this.redisTemplate.opsForValue().set(CarexxConstant.CachePrefix.CAREXX_AUTH_TOKEN + token, token);
		} catch (Exception e) {
			throw new BizException(ErrorCode.SYS_ERROR, e);
		}
		return token;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public Map<String, Object> patientLogin(PatientLoginFormBean patientLoginFormBean) throws BizException {
		if(patientLoginFormBean.getOpenId() == null || patientLoginFormBean.getOpenId() == "") {
			throw new BizException(ErrorCode.OPENID_FAIL_TO_GET);
		}
		OAuthLoginFormBean oAuthLoginFormBean = new OAuthLoginFormBean();
		oAuthLoginFormBean.setIdentityType(IdentityType.WECHAT.getValue());
		oAuthLoginFormBean.setIdentifier(patientLoginFormBean.getOpenId());
		oAuthLoginFormBean.setIdentity(Identity.PATIENT.getValue());
		oAuthLoginFormBean.setNickname(patientLoginFormBean.getNickname());
		oAuthLoginFormBean.setAvatar(patientLoginFormBean.getAvatar());
		oAuthLoginFormBean.setSex(patientLoginFormBean.getSex());
		oAuthLoginFormBean.setRegion(patientLoginFormBean.getRegion());
		
		UserInfo userInfo = this.add(oAuthLoginFormBean);
		String token = Radix32Utils.encode(userInfo.getId());
		try {
			this.redisTemplate.opsForValue().set(CarexxConstant.CachePrefix.CAREXX_AUTH_TOKEN + token, token);
		} catch (Exception e) {
			throw new BizException(ErrorCode.SYS_ERROR, e);
		}

		String openId = patientLoginFormBean.getOpenId();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("token", token);
		resultMap.put("openId", openId);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public Map<String, Object> nursingSupervisorLogin(NursingSupervisorLoginFormBean nursingSupervisorLoginFormBean) throws BizException {
		if(nursingSupervisorLoginFormBean.getOpenId() == null || nursingSupervisorLoginFormBean.getOpenId() == "") {
			throw new BizException(ErrorCode.OPENID_FAIL_TO_GET);
		}
		
		AclLoginFormBean aclLoginFormBean = new AclLoginFormBean();
		aclLoginFormBean.setAcctNo(nursingSupervisorLoginFormBean.getAcctNo());
		aclLoginFormBean.setLoginPass(nursingSupervisorLoginFormBean.getLoginPass());
		Map<String, Object> loginMap = this.aclUserManager.login(aclLoginFormBean);
		int roleId = this.aclUserAcctService.getRoleId(nursingSupervisorLoginFormBean.getAcctNo());
		
		if(roleId != 4)
		{
			throw new BizException(ErrorCode.NOT_NURSING_SUPERVISOR);
		}
		
		OAuthLoginFormBean oAuthLoginFormBean = new OAuthLoginFormBean();
		oAuthLoginFormBean.setIdentityType(IdentityType.WECHAT.getValue());
		oAuthLoginFormBean.setIdentifier(nursingSupervisorLoginFormBean.getOpenId());
		oAuthLoginFormBean.setIdentity(Identity.NURSING_SUPERVISOR.getValue());
		oAuthLoginFormBean.setNickname(nursingSupervisorLoginFormBean.getNickname());
		oAuthLoginFormBean.setAvatar(nursingSupervisorLoginFormBean.getAvatar());
		oAuthLoginFormBean.setSex(nursingSupervisorLoginFormBean.getSex());
		oAuthLoginFormBean.setRegion(nursingSupervisorLoginFormBean.getRegion());
		
		UserInfo userInfo = this.add(oAuthLoginFormBean);
		String token = Radix32Utils.encode(userInfo.getId());
		try {
			this.redisTemplate.opsForValue().set(CarexxConstant.CachePrefix.CAREXX_AUTH_TOKEN + token, token);
		} catch (Exception e) {
			throw new BizException(ErrorCode.SYS_ERROR, e);
		}
		String openId = nursingSupervisorLoginFormBean.getOpenId();
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap = loginMap;
		resultMap.put("token", token);
		resultMap.put("openId", openId);
		return resultMap;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public Map<String, Object> CaregiversLogin(Byte identityType, String openId) throws BizException {
		Map<String, Object> resultMap = new HashMap<>();
		Byte certificationStatus = 0;
		UserOAuth userOAuth = this.userOAuthService.getByIdentityInfo(identityType, openId);
		if (userOAuth != null) {
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
				resultMap.put("openId", openId);
				return resultMap;
			}
		} else {
			resultMap.put("certificationStatus", CertificationStatus.NO_CERTIFICATION.getValue());
			resultMap.put("openId", openId);
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
