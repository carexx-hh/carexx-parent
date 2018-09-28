package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.acl.AclRoleFormBean;
import com.sh.carexx.model.uc.AclRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AclRoleMapper {
	int insert(AclRole aclRole);

	AclRole selectById(Integer id);
	
	AclRole selectByUserId(Integer userId);

	Integer selectAclRoleCount(AclRoleFormBean aclRoleFormBean);
	
	List<AclRole> selectAllAvailable(@Param("instId") Integer instId);

	List<AclRole> selectAclRoleList(AclRoleFormBean aclRoleFormBean);

	int updateStatus(@Param("id") Integer id, @Param("srcStatus") Byte srcStatus,
			@Param("targetStatus") Byte targetStatus);

	int update(AclRole aclRole);
}