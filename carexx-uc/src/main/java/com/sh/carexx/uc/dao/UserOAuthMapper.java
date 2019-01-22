package com.sh.carexx.uc.dao;

import org.apache.ibatis.annotations.Param;

import com.sh.carexx.model.uc.UserOAuth;

public interface UserOAuthMapper {
	int insert(UserOAuth userOAuth);

	UserOAuth selectById(Long id);

	UserOAuth selectByIdentityInfo(@Param("identityType") Byte identityType, @Param("identifier") String identifier);

	int update(UserOAuth userOAuth);
	
	int updateStaffId(@Param("userId")int userId, @Param("staffId")int staffId);
	
	int updateUserAcctId(@Param("userId")int userId, @Param("userAcctId")int userAcctId);
}