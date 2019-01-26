package com.sh.carexx.api.client;

import com.sh.carexx.api.client.fallback.UCServiceFallback;
import com.sh.carexx.bean.acl.AclLoginFormBean;
import com.sh.carexx.bean.acl.AclModifyPwdFormBean;
import com.sh.carexx.bean.acl.AclRegFormBean;
import com.sh.carexx.bean.acl.AclRoleFormBean;
import com.sh.carexx.bean.care.*;
import com.sh.carexx.bean.customer.CustomerPatientFormBean;
import com.sh.carexx.bean.customer.InstCustomerFormBean;
import com.sh.carexx.bean.dict.DictDataFormBean;
import com.sh.carexx.bean.dict.DictFormBean;
import com.sh.carexx.bean.holiday.InstHolidayFormBean;
import com.sh.carexx.bean.order.*;
import com.sh.carexx.bean.staff.InstStaffFormBean;
import com.sh.carexx.bean.staff.InstStaffQueryFormBean;
import com.sh.carexx.bean.staff.InstStaffWorkTypeFormBean;
import com.sh.carexx.bean.user.*;
import com.sh.carexx.bean.usermsg.UserMsgFormBean;
import com.sh.carexx.bean.worktype.InstWorkTypeSettleFormBean;
import com.sh.carexx.bean.worktype.WorkTypeFormBean;
import com.sh.carexx.bean.worktype.WorkTypeSettleQueryFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.model.uc.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = CarexxConstant.MSProvider.MS_PROVIDER_UC, fallback = UCServiceFallback.class)
public interface UCServiceClient {
	@RequestMapping(value = "/auth/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String login(@RequestBody AclLoginFormBean aclLoginFormBean);

	@RequestMapping(value = "/auth/get_session_user_id", method = RequestMethod.GET)
	String getSessionUserId(@RequestParam("token") String token);

	@RequestMapping(value = "/auth/oauth_login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String doOAuthLogin(@RequestBody OAuthLoginFormBean oAuthLoginFormBean);

	@RequestMapping(value = "/auth/patient_login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String patientLogin(@RequestBody PatientLoginFormBean patientLoginFormBean);
	
	@RequestMapping(value = "/auth/nursing_supervisor_login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String nursingSupervisorLogin(@RequestBody NursingSupervisorLoginFormBean nursingSupervisorLoginFormBean);
	
	@RequestMapping(value = "/auth/get_oauth_user_id", method = RequestMethod.GET)
	String getOAuthUserId(@RequestParam("token") String token);

	@RequestMapping(value = "/auth/caregivers_login", method = RequestMethod.GET)
	String CaregiversLogin(@RequestParam("identityType") Byte identityType, @RequestParam("openId") String openId);
	
	@RequestMapping(value = "/user/get_user_info/{id}", method = RequestMethod.GET)
	UserInfo getUserInfo(@PathVariable("id") Integer id);
	
	@RequestMapping(value = "/user/get_user_oauth/{userId}", method = RequestMethod.GET)
	UserOAuth getUserOAuth(@PathVariable("userId") Integer userId);
	
	@RequestMapping(value = "/user/modify_bind_mobile/{id}/{mobile}/{verifyCode}", method = RequestMethod.GET)
	BasicRetVal modifyUserBindMobile(@PathVariable("id") Integer id, @PathVariable("mobile") String mobile, @PathVariable("verifyCode") String verifyCode);

	@RequestMapping(value = "/acluser/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addAclUser(@RequestBody AclRegFormBean aclRegFormBean);

	@RequestMapping(value = "/acluser/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryAclUserForList(@RequestBody AclRegFormBean aclRegFormBean);

	@RequestMapping(value = "/acluser/get_userId/{userId}", method = RequestMethod.GET)
	String getuserId(@PathVariable("userId") Integer userId);
	
	@RequestMapping(value = "/acluser/detail/{id}", method = RequestMethod.GET)
	String getAclUserDetail(@PathVariable("id") Integer id);

	@RequestMapping(value = "/acluser/get_roleId/{account}", method = RequestMethod.GET)
	String getRoleId(@PathVariable("account") String account);
	
	@RequestMapping(value = "/acluser/get/{id}", method = RequestMethod.GET)
	AclUserAcct getAclUser(@PathVariable("id") Integer id);

	@RequestMapping(value = "/acluser/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyAclUser(@RequestBody AclRegFormBean aclRegFormBean);

	@RequestMapping(value = "/acluser/modify_pwd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyAclLoginPwd(@RequestBody AclModifyPwdFormBean aclModifyPwdFormBean);
	
	@RequestMapping(value = "/acluser/lock/{id}", method = RequestMethod.GET)
	BasicRetVal lockAclUser(@PathVariable("id") Integer id);

	@RequestMapping(value = "/acluser/unlock/{id}", method = RequestMethod.GET)
	BasicRetVal unlockAclUser(@PathVariable("id") Integer id);

	@RequestMapping(value = "/acluser/delete/{id}", method = RequestMethod.GET)
	BasicRetVal deleteAclUser(@PathVariable("id") Integer id);

	@RequestMapping(value = "/aclrole/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addAclRole(@RequestBody AclRoleFormBean aclRoleFormBean);

	@RequestMapping(value = "/aclrole/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryAclRoleForList(@RequestBody AclRoleFormBean aclRoleFormBean);

	@RequestMapping(value = "/aclrole/list_all/{instId}", method = RequestMethod.GET)
	String queryAllAvailableAclRoleList(@PathVariable("instId") Integer instId);

	@RequestMapping(value = "/aclrole/list_all_auth", method = RequestMethod.GET)
	String queryAllAclRoleAuthList();

	@RequestMapping(value = "/aclrole/list_auth/{id}", method = RequestMethod.GET)
	String queryAclRoleAuthList(@PathVariable("id") Integer id);

	@RequestMapping(value = "/aclrole/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyAclRole(@RequestBody AclRoleFormBean aclRoleFormBean);

	@RequestMapping(value = "/aclrole/modify_auth", method = RequestMethod.GET)
	BasicRetVal modifyAclRoleAuth(@RequestParam("roleId") Integer roleId, @RequestParam("authBody") String authBody);

	@RequestMapping(value = "/aclrole/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableAclRole(@PathVariable("id") Integer id);

	@RequestMapping(value = "/aclrole/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableAclRole(@PathVariable("id") Integer id);

	/**
	 * 
	 * addCareInst:(添加医护机构信息). <br/>
	 * 
	 * @author zhoulei
	 * @param careInstFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinst/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCareInst(@RequestBody CareInstFormBean careInstFormBean);

	/**
	 * 
	 * queryCareInstForList:(查询医护机构信息分页). <br/>
	 * 
	 * @author zhoulei
	 * @param careInstFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinst/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCareInstForList(@RequestBody CareInstFormBean careInstFormBean);

	/**
	 * 
	 * queryServiceCareInstForList:(查询服务医护机构信息分页). <br/>
	 * 
	 * @author zhoulei
	 * @param careInstFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinst/list_service", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryServiceInstForList(@RequestBody CareInstFormBean careInstFormBean);

	/**
	 * 
	 * queryAllCareInst:(移动端查询所有医护机构信息). <br/>
	 * 
	 * @author zhoulei
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinst/all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryAllCareInst(@RequestBody CareInstFormBean careInstFormBean);

	/**
	 * 
	 * modifyCareInst:(修改医护机构信息). <br/>
	 * 
	 * @author zhoulei
	 * @param careInstFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinst/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyCareInst(@RequestBody CareInstFormBean careInstFormBean);

	/**
	 * 
	 * enableCareInst:(启用机构状态). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinst/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableCareInst(@PathVariable("id") Integer id);

	/**
	 * 
	 * disableCareInst:(禁用机构状态). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinst/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableCareInst(@PathVariable("id") Integer id);

	/**
	 * 
	 * addCareInstSys:(添加方法). <br/>
	 * 
	 * @author zhoulei
	 * @param careInstSysFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinstsys/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCareInstSys(@RequestBody CareInstSysFormBean careInstSysFormBean);

	/**
	 * 
	 * queryCareInstSysForList:(查询医护机构公司信息分页). <br/>
	 * 
	 * @author zhoulei
	 * @param careInstSysFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinstsys/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCareInstSysForList(@RequestBody CareInstSysFormBean careInstSysFormBean);

	/**
	 * 
	 * queryAllCareInstSysList:(查询医疗机构公司信息). <br/>
	 * 
	 * @author zhoulei
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinstsys/list_all/{instId}", method = RequestMethod.GET)
	String queryAllAvailableCareInstSys(@PathVariable("instId") Integer instId);

	/**
	 * 
	 * enableCareInstSys:(启用公司状态). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinstsys/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableCareInstSys(@PathVariable("id") Integer id);

	/**
	 * 
	 * disableCareInstSys:(禁用机构公司状态). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careinstsys/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableCareInstSys(@PathVariable("id") Integer id);

	/**
	 * 
	 * addDict:(添加字典). <br/>
	 * 
	 * @author hetao
	 * @param dictFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dict/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addDict(@RequestBody DictFormBean dictFormBean);

	/**
	 * 
	 * modifyDict:(修改字典). <br/>
	 * 
	 * @author hetao
	 * @param dictFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dict/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyDict(@RequestBody DictFormBean dictFormBean);

	/**
	 * 
	 * queryDictForList:(分页查询字典). <br/>
	 * 
	 * @author hetao
	 * @param dictFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dict/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryDictForList(@RequestBody DictFormBean dictFormBean);

	/**
	 * 
	 * enableDict:(修改字典状态为启用). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dict/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableDict(@PathVariable("id") Integer id);

	/**
	 * 
	 * disableDict:(修改字典状态为停用). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dict/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableDict(@PathVariable("id") Integer id);

	/**
	 * 
	 * queryAllAvailableDictDataForList:(查询所有启用字典数据). <br/>
	 * TODO(必须带字典id).<br/>
	 * 
	 * @author hetao
	 * @param dictid
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dictdata/list_all/{dictid}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryAllAvailableDictDataForList(@PathVariable("dictid") Integer dictid);

	/**
	 * 
	 * queryDictDataForList:(分页查询所有字典数据). <br/>
	 * 
	 * @author hetao
	 * @param dictDataFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dictdata/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryDictDataForList(@RequestBody DictDataFormBean dictDataFormBean);

	/**
	 * 
	 * modifyDictData:(修改字典数据). <br/>
	 * 
	 * @author hetao
	 * @param dictDataFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dictdata/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyDictData(@RequestBody DictDataFormBean dictDataFormBean);

	/**
	 * 
	 * addDictData:(添加字典数据). <br/>
	 * 
	 * @author hetao
	 * @param dictDataFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dictdata/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addDictData(@RequestBody DictDataFormBean dictDataFormBean);

	/**
	 * 
	 * enableDictData:(启用字典数据). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dictdata/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableDictData(@PathVariable("id") Integer id);

	/**
	 * 
	 * disableDictData:(停用字典数据). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/dictdata/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableDictData(@PathVariable("id") Integer id);

	/**
	 * 
	 * addWorkType:(添加工种). <br/>
	 * 
	 * @author hetao
	 * @param workTypeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/worktype/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addWorkType(@RequestBody WorkTypeFormBean workTypeFormBean);

	/**
	 * 
	 * modifyWorkType:(修改工种). <br/>
	 * 
	 * @author hetao
	 * @param workTypeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/worktype/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyWorkType(@RequestBody WorkTypeFormBean workTypeFormBean);

	/**
	 * 
	 * queryAllWorkTypeList:(查询所有启用工种信息). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/worktype/list_all", method = RequestMethod.GET)
	String queryAllAvailableWorkType();

	/**
	 * 
	 * queryForListWorkType:(分页查询工种信息). <br/>
	 * 
	 * @author hetao
	 * @param workTypeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/worktype/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryForListWorkType(@RequestBody WorkTypeFormBean workTypeFormBean);

	/**
	 * 
	 * enableWorkType:(启用工种信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/worktype/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableWorkType(@PathVariable("id") Integer id);

	/**
	 * 
	 * disableWorkType:(停用工种信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/worktype/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableWorkType(@PathVariable("id") Integer id);

	/**
	 * 
	 * addCareService:(添加平台服务). <br/>
	 * 
	 * @author hetao
	 * @param careServiceFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCareService(@RequestBody CareServiceFormBean careServiceFormBean);

	/**
	 * 
	 * queryAllCareService:(查询所有启用平台服务). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/list_all", method = RequestMethod.GET)
	String queryAllCareService();

	/**
	 * 
	 * queryCareServiceForList:(分页查询所有平台服务). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCareServiceForList(@RequestBody InstServiceQueryFormBean instServiceQueryFormBean);

	/**
	 * 
	 * modifyCareService:(修改平台服务信息). <br/>
	 * 
	 * @author hetao
	 * @param careServiceFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyCareService(@RequestBody CareServiceFormBean careServiceFormBean);

	/**
	 * 
	 * enableCareService:(启用平台服务信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableCareService(@PathVariable("id") Integer id);

	/**
	 * 
	 * disableCareService:(停用平台服务信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableCareService(@PathVariable("id") Integer id);

	/**
	 * 
	 * addInstCareService:(添加机构服务信息). <br/>
	 * 
	 * @author hetao
	 * @param instCareServiceFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/add_inst", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstCareService(@RequestBody InstCareServiceFormBean instCareServiceFormBean);

	/**
	 * 
	 * modifyInstCareService:(修改机构服务信息). <br/>
	 * 
	 * @author hetao
	 * @param instCareServiceFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/modify_inst", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyInstCareService(@RequestBody InstCareServiceFormBean instCareServiceFormBean);

	/**
	 * 
	 * queryInstCareServiceForList:(连接分页查询所有启用机构服务信息). <br/>
	 * 
	 * @author hetao
	 * @param instServiceQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/list_inst", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstCareServiceForList(@RequestBody InstServiceQueryFormBean instServiceQueryFormBean);

	/**
	 * 
	 * queryAllAvailableInstCareService:(移动端查询所有启用机构服务信息). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/list_all_service", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryAllAvailableInstCareService(@RequestBody InstServiceQueryFormBean instServiceQueryFormBean);

	/**
	 * 
	 * deleteInstCareService:(删除机构服务信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/careservice/delete/{id}", method = RequestMethod.GET)
	BasicRetVal deleteInstCareService(@PathVariable("id") Integer id);

	/**
	 * 
	 * queryInstCustomerForList:(连接分页查询客户信息). <br/>
	 * 
	 * @author hetao
	 * @param instCustomerFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instcustomer/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstCustomerForList(@RequestBody InstCustomerFormBean instCustomerFormBean);

	/**
	 * 
	 * addInstCustomer:(添加客户信息). <br/>
	 * 
	 * @author hetao
	 * @param instCustomerFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instcustomer/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstCustomer(@RequestBody InstCustomerFormBean instCustomerFormBean);

	/**
	 * 
	 * modifyInstCustomer:(修改客户信息). <br/>
	 * 
	 * @author hetao
	 * @param instCustomerFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instcustomer/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyInstCustomer(@RequestBody InstCustomerFormBean instCustomerFormBean);

	/**
	 * 
	 * deleteInstCustomer:(修改客户信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instcustomer/delete/{id}", method = RequestMethod.GET)
	BasicRetVal deleteInstCustomer(@PathVariable("id") Integer id);

	/**
	 * 
	 * addCustomerOrder:(添加客户服务订单). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCustomerOrder(@RequestBody CustomerOrderFormBean customerOrderFormBean);

	@RequestMapping(value = "/customerorder/add_community", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCommunityCustomerOrder(@RequestBody CustomerOrderFormBean customerOrderFormBean);
	/**
	 * 
	 * addCustomerOrder:(移动端添加客户服务预约订单). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/add_appointOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCustomerAppointOrder(@RequestBody CustomerAppointOrderFormBean customerAppointOrderFormBean);

	/**
	 * 
	 * queryCustomerOrderForList:(分页查询客户预约服务订单). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCustomerOrderForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * queryCustomerOrderForListByWorkTypeId:(通过工种查询订单). <br/>
	 * 
	 * @author zhoulei
	 * @param CustomerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/list_by_worktypeid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCustomerOrderForListByWorkTypeId(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);
	

	/**
	 * 
	 * queryCustomerOrderForListStaffSchedule:(通过工种查询订单). <br/>
	 * 
	 * @author zhoulei
	 * @param CustomerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/staffschedule_list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCustomerOrderForListStaffSchedule(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * queryCustomerOrderListByUserId:(移动端通过客户id查询客户预约服务订单). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/list_order", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCustomerOrderListByUserId(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);

	@RequestMapping(value = "/customerorder/by_orderStatus/{orderStatus}/{instId}", method = RequestMethod.GET)
	String queryMappByOrderStatus(@PathVariable("orderStatus")String orderStatus, @PathVariable("instId")Integer instId);

	@RequestMapping(value = "/customerorder/by_orderStatus_and_serviceStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryMappByOrderStatusAndServiceStatus(@RequestBody MappCustomerOrderQueryFormBean mappCustomerOrderQueryFormBean);
	/**
	 *
	 * queryDoneOrderByUserId:(移动端通过客户id查询完成和已支付订单). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/done_order/{userId}", method = RequestMethod.GET)
	String queryDoneOrderByUserId(@PathVariable("userId") Integer userId);
	/**
	 *
	 * queryOrderDetail:(移动端通过客户id查询客户预约服务订单). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/detail/{orderNo}", method = RequestMethod.GET)
	String queryOrderDetailByOrderNo(@PathVariable("orderNo") String orderNo);
	/**
	 * 
	 * CanceledCustomerOrder:(取消订单). <br/>
	 * 
	 * @author hetao
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/cancel/{orderNo}", method = RequestMethod.GET)
	BasicRetVal cancelCustomerOrder(@PathVariable("orderNo") String orderNo);

	/**
	 * 
	 * DeleteCustomerOrder:(删除订单). <br/> 
	 * 
	 * @author zhoulei 
	 * @param orderNo
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/delete/{orderNo}", method = RequestMethod.GET)
	BasicRetVal deleteCustomerOrder(@PathVariable("orderNo") String orderNo);
	/**
	 *
	 * mappCanceledCustomerOrder:(取消订单). <br/>
	 *
	 * @author hetao
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/mapp_cancel/{orderNo}", method = RequestMethod.GET)
	BasicRetVal mappCancelCustomerOrder(@PathVariable("orderNo") String orderNo);

	/**
	 * 
	 * confirmCompletedCustomerOrder:(确认订单支付). <br/>
	 * 
	 * @author hetao
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/throughPay/{orderNo}/{payType}", method = RequestMethod.GET)
	BasicRetVal throughPayCustomerOrder(@PathVariable("orderNo") String orderNo, @PathVariable("payType") Byte payType);

	/**
	 *
	 * modifyOrderServiceEndTime:(修改订单结束时间). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/modify_endTime/{orderNo}", method = RequestMethod.GET)
	BasicRetVal modifyOrderServiceEndTime(@PathVariable("orderNo") String orderNo);

	/**
	 * 
	 * confirmCompletedCustomerOrder:(确认订单完成). <br/> 
	 * 
	 * @author hetao 
	 * @param confirmCompletedOrderFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/confirmcompleted", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal confirmCompletedCustomerOrder(@RequestBody ConfirmCompletedOrderFormBean confirmCompletedOrderFormBean);

	/**
	 * 
	 * calcServiceFee:(计算结算金额). <br/>
	 * 
	 * @author hetao
	 * @param calcServiceFeeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/calc_service_fee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BigDecimal calcServiceFee(@RequestBody CalcServiceFeeFormBean calcServiceFeeFormBean);

	/**
	 * 
	 * syncPayResult:(同步支付结果). <br/>
	 * 
	 * @author zhoulei
	 * @param orderPayment
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/sync_pay_result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal syncPayResult(@RequestBody OrderPayment orderPayment);

	/**
	 * 
	 * getOrderPayment:(通过订单号查询支付信息). <br/>
	 * 
	 * @author zhoulei
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/get_order_payment/{orderNo}", method = RequestMethod.GET)
	OrderPayment getOrderPayment(@PathVariable("orderNo") String orderNo);

	/**
	 * 
	 * queryIncomeCountForList:(收入统计). <br/> 
	 * 
	 * @author zhoulei 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/income_count", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryIncomeCountForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	@RequestMapping(value = "/customerorder/inst_income_count", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstIncomeCountForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	@RequestMapping(value = "/customerorder/staff_income_count", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryStaffIncomeCountForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);
	/**
	 * 
	 * customerOrderAdjustAmt:(调整订单金额). <br/> 
	 * 
	 * @author hetao
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorder/adjust", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal customerOrderAdjust(@RequestBody CustomerOrderAdjustFormBean customerOrderAdjustFormBean);

	/**
	 * 
	 * queryUserMsgList:(查询用户消息). <br/>
	 * 
	 * @author zhoulei
	 * @param userMsgFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/msg/all/{userId}", method = RequestMethod.GET)
	String queryAllUserMsg(@PathVariable("userId") Integer userId);
	
	/**
	 * 
	 * addUserMsg:(添加用户消息). <br/>
	 * 
	 * @author zhoulei
	 * @param userMsgFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/msg/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addUserMsg(@RequestBody UserMsgFormBean userMsgFormBean);
	
	/**
	 * 
	 * readUserMsg:(读消息). <br/> 
	 * 
	 * @author zhoulei 
	 * @param msgId
	 * @param userId
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/msg/read/{msgId}/{userId}", method = RequestMethod.GET)
	BasicRetVal readUserMsg(@PathVariable("msgId") Long msgId, @PathVariable("userId") Integer userId);

	/**
	 * 
	 * deleteUserMsg:(删除用户消息). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/msg/delete/{ids}", method = RequestMethod.GET)
	BasicRetVal deleteUserMsg(@PathVariable("ids") String ids);

	/**
	 * 
	 * readUserMsg:(添加消息状态并返回用户消息信息). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/msg/read/{id}", method = RequestMethod.GET)
	UserMsg readUserMsg(@PathVariable("id") Long id);

	@RequestMapping(value = "/msg/count_unread/{userId}", method = RequestMethod.GET)
	String getUserMsgCountUnread(@PathVariable("userId") Integer userId);
	/**
	 * 
	 * queryInstStaffForList:(查询机构员工信息分页). <br/>
	 * 
	 * @author zhoulei
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaff/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstStaffForList(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean);

	@RequestMapping(value = "/inststaff/all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstStaffForAll(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean);
	
	@RequestMapping(value = "/inststaff/get_id/{id}", method = RequestMethod.GET)
	String getInstStaffId(@PathVariable("id") Integer id);
	/**
	 * 
	 * queryInstStaffByServiceId:(通过服务id和机构id查询). <br/>
	 * 
	 * @author zhoulei
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaff/list_by_serviceid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstStaffByServiceId(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean);
	
	@RequestMapping(value = "/inststaff/all_by_certification_status/{instId}/{certificationStatus}", method = RequestMethod.GET)
	String queryInstStaffByCertificationStatus(@PathVariable("instId") Integer instId, @PathVariable("certificationStatus") Byte certificationStatus);
	
	@RequestMapping(value = "/inststaff/staff_schedule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstStaffSchedule(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	@RequestMapping(value = "/inststaff/mapp_all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryMappAllInstStaff(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean);
	
	@RequestMapping(value = "/inststaff/agree_certification/{id}", method = RequestMethod.GET)
	BasicRetVal agreeCertification(@PathVariable("id") Integer id);
	
	@RequestMapping(value = "/inststaff/refused_certification/{id}", method = RequestMethod.GET)
	BasicRetVal refusedCertification(@PathVariable("id") Integer id);
	
	@RequestMapping(value = "/inststaff/apply_certification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal applyCertification(@RequestBody ApplyCertificationFormBean applyCertificationFormBean);
	
	@RequestMapping(value = "/inststaff/cancel_certification/{id}", method = RequestMethod.GET)
	BasicRetVal cancelCertification(@PathVariable("id") Integer id);
	
	/**
	 * 
	 * addInstStaff:(添加机构员工信息). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaff/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstStaff(@RequestBody InstStaffFormBean instStaffFormBean);

	/**
	 * 
	 * deleteInstStaff:(通过id删除员工信息). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaff/delete/{id}", method = RequestMethod.GET)
	BasicRetVal deleteInstStaff(@PathVariable("id") Integer id);

	/**
	 * 
	 * modifyInstStaff:(修改员工信息). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaff/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyInstStaff(@RequestBody InstStaffFormBean instStaffFormBean);

	@RequestMapping(value = "/inststaff/staff_bind_mobile/{id}/{mobile}/{verifyCode}", method = RequestMethod.GET)
	BasicRetVal modifyStaffBindMobile(@PathVariable("id") Integer id, @PathVariable("mobile") String mobile, @PathVariable("verifyCode") String verifyCode);
	/**
	 * 
	 * addInstStaffWorkType:(添加机构员工工种结算). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffWorkTypeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaffworktype/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstStaffWorkType(@RequestBody InstStaffWorkTypeFormBean instStaffWorkTypeFormBean);

	/**
	 * 
	 * queryInstStaffWorkTypeForList:(查询机构员工工种结算分页). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffWorkTypeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaffworktype/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstStaffWorkTypeForList(@RequestBody InstStaffWorkTypeFormBean instStaffWorkTypeFormBean);

	/**
	 * 
	 * queryInstStaffWorkTypeByStaffId:(通过员工id查询相应工种). <br/>
	 * 
	 * @author zhoulei
	 * @param staffId
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaffworktype/all_by_staffid/{staffId}", method = RequestMethod.GET)
	String queryInstStaffWorkTypeByStaffId(@PathVariable("staffId") Integer staffId);

	/**
	 * 
	 * modifyInstStaffWorkType:(修改机构员工工种结算). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffWorkTypeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaffworktype/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyInstStaffWorkType(@RequestBody InstStaffWorkTypeFormBean instStaffWorkTypeFormBean);

	/**
	 * 
	 * enableInstStaffWorkType:(启用机构员工工种结算). <br/>
	 * 
	 * @author zholei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaffworktype/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableInstStaffWorkType(@PathVariable("id") Long id);

	/**
	 * 
	 * enableInstStaffWorkType:(禁用机构员工工种结算). <br/>
	 * 
	 * @author zholei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inststaffworktype/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableInstStaffWorkType(@PathVariable("id") Long id);

	/**
	 * 
	 * queryAllInstWorkTypeSettle:(查询所有启用工种结算信息). <br/>
	 * 
	 * @author hetao
	 * @param instId
	 * @param workTypeId
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instworktypesettle/list_all/{instId}/{workTypeId}", method = RequestMethod.GET)
	String queryAllInstWorkTypeSettle(@PathVariable("instId") Integer instId,
			@PathVariable("workTypeId") Integer workTypeId);

	/**
	 * 
	 * queryInstWorkTypeSettleForList:(分页连接查询各机构工种结算信息). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instworktypesettle/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstWorkTypeSettleForList(@RequestBody WorkTypeSettleQueryFormBean workTypeSettleQueryFormBean);

	/**
	 * 
	 * addInstWorkTypeSettle:(添加工种结算信息). <br/>
	 * 
	 * @author hetao
	 * @param instWorkTypeSettleFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instworktypesettle/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstWorkTypeSettle(@RequestBody InstWorkTypeSettleFormBean instWorkTypeSettleFormBean);

	/**
	 * 
	 * modifyInstWorkTypeSettle:(修改工种结算信息). <br/>
	 * 
	 * @author hetao
	 * @param instWorkTypeSettleFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instworktypesettle/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyInstWorkTypeSettle(@RequestBody InstWorkTypeSettleFormBean instWorkTypeSettleFormBean);

	/**
	 * 
	 * enableInstWorkTypeSettle:(启用工种结算信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instworktypesettle/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableInstWorkTypeSettle(@PathVariable("id") Integer id);

	/**
	 * 
	 * disableInstWorkTypeSettle:(停用工种结算信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instworktypesettle/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableInstWorkTypeSettle(@PathVariable("id") Integer id);

	/**
	 * 
	 * queryAllForCustomerOrderSchedule:(查询所有排班). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryAllForCustomerOrderSchedule();

	/**
	 * 
	 * addCustomerOrderSchedule:(添加排班). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderScheduleFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCustomerOrderSchedule(@RequestBody CustomerOrderScheduleFormBean customerOrderScheduleFormBean);

	/**
	 *
	 * addOutSendOrderSchedule:(添加外派排班). <br/>
	 *
	 * @author hetao
	 * @param customerOrderScheduleFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/add_outSend", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addOutSendOrderSchedule(@RequestBody CustomerOrderScheduleFormBean customerOrderScheduleFormBean);

	@RequestMapping(value = "/customerorderschedule/mapp_add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal mappAddCustomerOrderSchedule(@RequestBody MappCustomerOrderScheduleFormBean mappCustomerOrderScheduleFormBean);
	
	@RequestMapping(value = "/customerorderschedule/accept_schedule/{orderNo}", method = RequestMethod.GET)
	BasicRetVal acceptSchedule(@PathVariable("orderNo") String orderNo);
	
	@RequestMapping(value = "/customerorderschedule/refused_schedule/{orderNo}", method = RequestMethod.GET)
	BasicRetVal refusedSchedule(@PathVariable("orderNo") String orderNo);
	/**
	 * 
	 * queryOrderScheduleStaff:(通过订单号查询订单排班). <br/>
	 * 
	 * @author hetao
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/all_schedule/{orderNo}", method = RequestMethod.GET)
	String queryOrderScheduleByOrderNo(@PathVariable("orderNo") String orderNo);

	/**
	 * 
	 * deleteCustomerOrderSchedule:(修改排班状态为取消). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/delete/{id}", method = RequestMethod.GET)
	BasicRetVal deleteCustomerOrderSchedule(@PathVariable("id") Long id);

	/**
	 * 
	 * completedCustomerOrderSchedule:(完成排班任务). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/confirmcompleted/{id}", method = RequestMethod.GET)
	BasicRetVal confirmCompletedCustomerOrderSchedule(@PathVariable("id") Long id);

	/**
	 * 
	 * batchDeleteSchedule:(批量删除排班). <br/>
	 * 
	 * @author hetao
	 * @param ids
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/batchdelete", method = RequestMethod.POST)
	BasicRetVal batchDeleteSchedule(@RequestParam("ids") String ids);

	/**
	 * 
	 * batchconfirmcompletedSchedule:(批量确认排班). <br/>
	 * 
	 * @author hetao
	 * @param ids
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/batchconfirmcompleted", method = RequestMethod.POST)
	BasicRetVal batchConfirmCompleteSchedule(@RequestParam("ids") String ids);

	/**
	 * 
	 * queryWorkQuantityReport:(工量结算报表). <br/>
	 * 
	 * @author hetao
	 * @param workQuantityReportFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/workquantity_report", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryWorkQuantityReport(@RequestBody WorkQuantityReportFormBean workQuantityReportFormBean);

	/**
	 * 
	 * modifySettleAmt:(调整结算款). <br/> 
	 * 
	 * @author zhoulei 
	 * @param orderSettleAdjustAmtFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/modify_settle_amt", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifySettleAmt(@RequestBody OrderSettleAdjustAmtFormBean orderSettleAdjustAmtFormBean);
	/**
	 * 
	 * queryCustomerPatientForList:(移动端查询机构客户就诊人信息). <br/>
	 * 
	 * @author zhoulei
	 * @param customerPatientFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerpatient/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCustomerPatientForList(@RequestBody CustomerPatientFormBean customerPatientFormBean);

	/**
	 * 
	 * addCustomerPatient:(移动端添加机构客户就诊人信息). <br/>
	 * 
	 * @author zhoulei
	 * @param customerPatientFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerpatient/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCustomerPatient(@RequestBody CustomerPatientFormBean customerPatientFormBean);

	/**
	 * 
	 * modifyCustomerPatient:(移动端修改机构客户就诊人信息). <br/>
	 * 
	 * @author zhoulei
	 * @param customerPatientFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerpatient/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyCustomerPatient(@RequestBody CustomerPatientFormBean customerPatientFormBean);

	/**
	 * 
	 * deleteCustomerPatient:(移动端删除机构客户就诊人信息). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerpatient/delete/{id}/{customerId}", method = RequestMethod.GET)
	BasicRetVal deleteCustomerPatient(@PathVariable("id") Long id, @PathVariable("customerId") Integer customerId);

	/**
	 * 
	 * addInstHoliday:(节假日添加方法). <br/>
	 * 
	 * @author zhoulei
	 * @param InstHolidayFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/holiday/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstHoliday(@RequestBody InstHolidayFormBean InstHolidayFormBean);

	/**
	 * 
	 * queryInstHolidayForList:(节假日查询方法). <br/>
	 * 
	 * @author zhoulei
	 * @param InstHolidayFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/holiday/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstHolidayForList(@RequestBody InstHolidayFormBean InstHolidayFormBean);

	/**
	 * 
	 * modifyInstHoliday:(修改节假日方法). <br/>
	 * 
	 * @author zhoulei
	 * @param InstHolidayFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/holiday/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyInstHoliday(@RequestBody InstHolidayFormBean InstHolidayFormBean);

	/**
	 * 
	 * deleteInstHoliday:(删除节假日方法). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/holiday/delete/{id}", method = RequestMethod.GET)
	BasicRetVal deleteInstHoliday(@PathVariable("id") Long id);

	/**
	 * 
	 * addInstSettle:(添加关账). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instsettle/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstSettle(@RequestBody InstSettle instSettle);

	/**
	 * 
	 * queryInstSettleAll:(查询机构下所有关账). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instsettle/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstSettleForList(@RequestBody InstSettleQueryFormBean instSettleQueryFormBean);

	/**
	 * 
	 * closeInstSettle:(关闭账单). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instsettle/closesettle/{id}/{modifier}", method = RequestMethod.GET)
	BasicRetVal closeInstSettle(@PathVariable("id") Long id, @PathVariable("modifier") String modifier);

	/**
	 * 
	 * openInstSettle:(打开关账). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instsettle/opensettle/{id}/{modifier}", method = RequestMethod.GET)
	BasicRetVal openInstSettle(@PathVariable("id") Long id, @PathVariable("modifier") String modifier);

	/**
	 * 
	 * queryOrderSettleCount:(结算统计). <br/>
	 * 
	 * @author hetao
	 * @param workQuantityReportFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/instsettle/settlecount_report", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryOrderSettleCount(@RequestBody WorkQuantityReportFormBean workQuantityReportFormBean);

	/**
	 * 
	 * addInstInpatientArea:(新增病区). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instInpatientAreaFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inpatientarea/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addInstInpatientArea(@RequestBody InstInpatientAreaFormBean instInpatientAreaFormBean);

	/**
	 * 
	 * queryInstInpatientAreaForList:(病区分页查询). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instInpatientAreaFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inpatientarea/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryInstInpatientAreaForList(@RequestBody InstInpatientAreaFormBean instInpatientAreaFormBean);

	/**
	 * 
	 * queryAllInstInpatientArea:(病区查询所有). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instId
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inpatientarea/all/{instId}", method = RequestMethod.GET)
	String queryAllInstInpatientArea(@PathVariable("instId") Integer instId);

	/**
	 * 
	 * modifyInstInpatientArea:(病区修改). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instInpatientAreaFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inpatientarea/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyInstInpatientArea(@RequestBody InstInpatientAreaFormBean instInpatientAreaFormBean);

	/**
	 * 
	 * deleteInstInpatientArea:(病区删除). <br/> 
	 * 
	 * @author zhoulei 
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/inpatientarea/delete/{id}", method = RequestMethod.GET)
	BasicRetVal deleteInstInpatientArea(@PathVariable("id") Integer id);
	
	@RequestMapping(value = "/sms/send_verify_code/{mobile}", method = RequestMethod.GET)
	BasicRetVal sendVerifyCode(@PathVariable("mobile") String mobile);
	
	@RequestMapping(value = "/serviceratio/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addServiceRatio(@RequestBody CareServiceRatioFormBean careServiceRatioFormBean);
	
	@RequestMapping(value = "/serviceratio/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryServiceRatioForList(@RequestBody CareServiceRatioFormBean careServiceRatioFormBean);
	
	@RequestMapping(value = "/serviceratio/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyServiceRatio(@RequestBody CareServiceRatioFormBean careServiceRatioFormBean);

	@RequestMapping(value = "/serviceratio/enable/{id}", method = RequestMethod.GET)
	BasicRetVal enableServiceRatio(@PathVariable("id") Integer id);

	@RequestMapping(value = "/serviceratio/disable/{id}", method = RequestMethod.GET)
	BasicRetVal disableServiceRatio(@PathVariable("id") Integer id);

	/**
	 *
	 * getCustomerordertimeByInstId:(通过机构id查询机构班次). <br/>
	 *
	 * @author hetao
	 * @param instId
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerordertime/get_by_instId/{instId}", method = RequestMethod.GET)
	String getCustomerordertimeByInstId(@PathVariable("instId") Integer instId);

	@RequestMapping(value = "/customerordertime/by_instId/{instId}", method = RequestMethod.GET)
	String queryCustomerordertimeByInstId(@PathVariable("instId") Integer instId);

	/**
	 *
	 * addCustomerordertime:(新增机构班次). <br/>
	 *
	 * @author hetao
	 * @param customerOrderTimeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerordertime/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addCustomerordertime(@RequestBody CustomerOrderTimeFormBean customerOrderTimeFormBean);

	/**
	 *
	 * modifyCustomerordertime:(修改机构班次). <br/>
	 *
	 * @author hetao
	 * @param customerOrderTimeFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerordertime/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal modifyCustomerordertime(@RequestBody CustomerOrderTimeFormBean customerOrderTimeFormBean);

	/**
	 *
	 * queryCustomerOrderTimeForList:(机构班次分页查询). <br/>
	 *
	 * @author hetao
	 * @param customerOrderTimeQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerordertime/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryCustomerOrderTimeForList(@RequestBody CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

	/**
	 *
	 * queryUserAccountByUserId:(通过用户id查询账户). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/useraccount/by_userId/{userId}", method = RequestMethod.GET)
	String queryUserAccountByUserId(@PathVariable("userId") Integer userId);

	/**
	 *
	 * queryOrderScheduleStatisticsByStaffId:我的收入 订单统计
	 *
	 * @author chenshichao
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/customerorderschedule/order_statistics/{staffId}/{serviceEndTime}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryOrderScheduleStatisticsByStaffId(@PathVariable("staffId") Integer staffId, @PathVariable("serviceEndTime") String serviceEndTime);

	@RequestMapping(value = "/accountdetail/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BasicRetVal addUserAccountDetail(@RequestBody UserAccountDetailFormBean userAccountDetailFormBean);

	@RequestMapping(value = "/accountdetail/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String queryUserAccountDetail(@RequestBody UserAccountDetailQueryFormBean userAccountDetailQueryFormBean);

	@RequestMapping(value = "/play_cards/addScore/{scores}", method = RequestMethod.GET)
	BasicRetVal addScore(@PathVariable("scores") String scores);

	@RequestMapping(value = "/play_cards/getScore", method = RequestMethod.GET)
	String getScore();

	@RequestMapping(value = "/play_cards/resetScore", method = RequestMethod.POST)
	BasicRetVal resetScore();

	@RequestMapping(value = "/play_cards/addUserName/{id}/{userName}", method = RequestMethod.GET)
	BasicRetVal addUserName(@PathVariable(value = "id") int id, @PathVariable(value = "userName") String userName);

	@RequestMapping(value = "/play_cards/queryUserName", method = RequestMethod.GET)
	String queryUserName();
}
