package com.sh.carexx.uc.service;

import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.UserOAuth;

public interface UserOAuthService {
	UserOAuth getByIdentityInfo(Byte identityType, String identifier);

	UserOAuth getByUserId(Integer userId);
	
	void save(UserOAuth userOAuth) throws BizException;

	void update(UserOAuth userOAuth) throws BizException;
	
	void updateStaffId(int userId, int staffId, int instId) throws BizException;
	
	void updateUserAcctId(int userId, int userAcctId, int instId) throws BizException;

	void deleteByStaffId(int deleteByStaffId) throws BizException;
}
