package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.acl.AclRegFormBean;
import com.sh.carexx.model.uc.AclUserAcct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AclUserAcctMapper {
	int insert(AclUserAcct aclUserAcct);

	AclUserAcct selectById(Integer id);

	int selectRoleId(String account);
	
	AclUserAcct selectByAccount(String account);

	Integer selectAclUserCount(AclRegFormBean aclRegFormBean);

	List<Map<String, Object>> selectAclUserList(AclRegFormBean aclRegFormBean);

	int updateStatus(@Param("id") Integer id, @Param("srcStatus") Byte srcStatus,
			@Param("targetStatus") Byte targetStatus);

	int delete(@Param("id") Integer id);

	int update(AclUserAcct aclUserAcct);
}