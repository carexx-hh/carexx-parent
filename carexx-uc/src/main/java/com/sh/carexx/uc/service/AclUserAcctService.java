package com.sh.carexx.uc.service;

import com.sh.carexx.bean.acl.AclRegFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.AclUserAcct;

import java.util.List;
import java.util.Map;

public interface AclUserAcctService {
	void save(AclUserAcct aclUserAcct) throws BizException;

	AclUserAcct getById(Integer id);

	int getRoleId(String account);
	
	AclUserAcct getByAccount(String account);

	Integer getAclUserCount(AclRegFormBean aclRegFormBean);

	List<Map<String, Object>> queryAclUserList(AclRegFormBean aclRegFormBean);

	void updateStatus(Integer id, Byte srcStatus, Byte targetStatus) throws BizException;

	void delete(Integer id) throws BizException;

	void update(AclUserAcct aclUserAcct) throws BizException;
}
