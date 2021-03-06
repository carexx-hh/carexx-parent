package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.bean.staff.InstStaffFormBean;
import com.sh.carexx.bean.staff.InstStaffQueryFormBean;
import com.sh.carexx.bean.user.ApplyCertificationFormBean;
import com.sh.carexx.bean.user.OAuthLoginFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.Identity;
import com.sh.carexx.common.enums.UseStatus;
import com.sh.carexx.common.enums.staff.CertificationStatus;
import com.sh.carexx.common.enums.staff.StaffStatus;
import com.sh.carexx.common.enums.user.IdentityType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.DateUtils;
import com.sh.carexx.common.util.ValidUtils;
import com.sh.carexx.model.uc.CareService;
import com.sh.carexx.model.uc.CustomerOrder;
import com.sh.carexx.model.uc.CustomerOrderSchedule;
import com.sh.carexx.model.uc.InstStaff;
import com.sh.carexx.model.uc.InstStaffWorkType;
import com.sh.carexx.model.uc.UserInfo;
import com.sh.carexx.uc.service.CareServiceService;
import com.sh.carexx.uc.service.CustomerOrderScheduleService;
import com.sh.carexx.uc.service.CustomerOrderService;
import com.sh.carexx.uc.service.InstStaffService;
import com.sh.carexx.uc.service.InstStaffWorkTypeService;
import com.sh.carexx.uc.service.UserOAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: InstStaffManager <br/>
 * Function: 人员管理 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * Date: 2018年7月12日 下午1:46:51 <br/>
 * 
 * @author zhoulei
 * @since JDK 1.8
 */
@Service
public class InstStaffManager {

	@Autowired
	public InstStaffService instStaffService;

	@Autowired
	public InstStaffWorkTypeService instStaffWorkTypeService;

	@Autowired
	public CustomerOrderScheduleService customerOrderScheduleService;

	@Autowired
	public CustomerOrderService customerOrderService;

	@Autowired
	public UserManager userManager;
	
	@Autowired
	public UserOAuthService userOAuthService;
	
	@Autowired
	public CareServiceService careServiceService;
	
	@Autowired
	private SmsManager smsManager;
	
	
	/**
	 * 
	 * add:(人员添加). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffFormBean
	 * @throws BizException
	 * @since JDK 1.8
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public void add(InstStaffFormBean instStaffFormBean) throws BizException {
		// 员工信息身份证校验
		if (instStaffService.getByIdNo(instStaffFormBean.getIdNo(), instStaffFormBean.getInstId()) != null) {
			throw new BizException(ErrorCode.INST_STAFF_EXISTS_ERROR);
		}

		InstStaff instStaff = new InstStaff();
		instStaff.setInstId(instStaffFormBean.getInstId());
		instStaff.setServiceInstId(instStaffFormBean.getServiceInstId());
		instStaff.setPersonType(instStaffFormBean.getPersonType());
		instStaff.setJobStatus(instStaffFormBean.getJobStatus());
		instStaff.setCertificationStatus(CertificationStatus.NO_CERTIFICATION.getValue());
		instStaff.setStaffStatus(StaffStatus.NORMAL.getValue());
		instStaff.setRealName(instStaffFormBean.getRealName());
		instStaff.setIdNo(instStaffFormBean.getIdNo());
		instStaff.setWorkLicense(instStaffFormBean.getWorkLicense());
		instStaff.setHealthyLicense(instStaffFormBean.getHealthyLicense());
		instStaff.setSex(instStaffFormBean.getSex());
		instStaff.setPhoto(instStaffFormBean.getPhoto());
		if (ValidUtils.isDate(instStaffFormBean.getBirthday())) {
			instStaff.setBirthday(DateUtils.toDate(instStaffFormBean.getBirthday(), DateUtils.YYYY_MM_DD));
		}
		instStaff.setPhone(instStaffFormBean.getPhone());
		instStaff.setAddress(instStaffFormBean.getAddress());
		if (ValidUtils.isDate(instStaffFormBean.getEntryDate())) {
			instStaff.setEntryDate(DateUtils.toDate(instStaffFormBean.getEntryDate(), DateUtils.YYYY_MM_DD));
		}
		if (ValidUtils.isDate(instStaffFormBean.getLeaveDate())) {
			instStaff.setLeaveDate(DateUtils.toDate(instStaffFormBean.getLeaveDate(), DateUtils.YYYY_MM_DD));
		}
		this.instStaffService.save(instStaff);

		Integer staffId = instStaff.getId();
		InstStaffWorkType instStaffWorkType = new InstStaffWorkType();
		instStaffWorkType.setStaffId(staffId);
		instStaffWorkType.setWorkTypeId(instStaffFormBean.getWorkTypeId());
		instStaffWorkType.setSettleStatus(UseStatus.DISABLED.getValue());
		instStaffWorkTypeService.save(instStaffWorkType);
	}

	/**
	 * 
	 * modify:(员工信息修改). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffFormBean
	 * @throws BizException
	 * @since JDK 1.8
	 */
	public void modify(InstStaffFormBean instStaffFormBean) throws BizException {
		// 员工信息身份证校验
		InstStaff oldinstStaff = instStaffService.getByIdNo(instStaffFormBean.getIdNo(), instStaffFormBean.getInstId());
		if (oldinstStaff != null && oldinstStaff.getId() != instStaffFormBean.getId()) {
			throw new BizException(ErrorCode.INST_STAFF_EXISTS_ERROR);
		}

		InstStaff instStaff = new InstStaff();
		instStaff.setId(instStaffFormBean.getId());
		instStaff.setServiceInstId(instStaffFormBean.getServiceInstId());
		instStaff.setPersonType(instStaffFormBean.getPersonType());
		instStaff.setJobStatus(instStaffFormBean.getJobStatus());
		instStaff.setRealName(instStaffFormBean.getRealName());
		instStaff.setIdNo(instStaffFormBean.getIdNo());
		instStaff.setWorkLicense(instStaffFormBean.getWorkLicense());
		instStaff.setHealthyLicense(instStaffFormBean.getHealthyLicense());
		instStaff.setSex(instStaffFormBean.getSex());
		instStaff.setPhoto(instStaffFormBean.getPhoto());
		if (ValidUtils.isDate(instStaffFormBean.getBirthday())) {
			instStaff.setBirthday(DateUtils.toDate(instStaffFormBean.getBirthday(), DateUtils.YYYY_MM_DD));
		}
		instStaff.setPhone(instStaffFormBean.getPhone());
		instStaff.setAddress(instStaffFormBean.getAddress());
		if (ValidUtils.isDate(instStaffFormBean.getEntryDate())) {
			instStaff.setEntryDate(DateUtils.toDate(instStaffFormBean.getEntryDate(), DateUtils.YYYY_MM_DD));
		}
		if (ValidUtils.isDate(instStaffFormBean.getLeaveDate())) {
			instStaff.setLeaveDate(DateUtils.toDate(instStaffFormBean.getLeaveDate(), DateUtils.YYYY_MM_DD));
		}
		this.instStaffService.update(instStaff);
	}

	public List<Map<?, ?>> queryMappAllInstStaff(InstStaffQueryFormBean instStaffQueryFormBean) throws BizException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = null;
		try {
			currentTime = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<?, ?>> instStaffList = instStaffService.queryInstStaffServiceNumber(null, instStaffQueryFormBean.getInstId(),
				currentTime, instStaffQueryFormBean.getRealName());
		return instStaffList;
	}

	public List<Map<?, ?>> staffSchedule(CustomerOrderQueryFormBean customerOrderQueryFormBean) throws BizException {
		String orderNo = customerOrderQueryFormBean.getOrderNo();
		String realName = customerOrderQueryFormBean.getStaffName();
		CustomerOrder customerOrder = customerOrderService.getByOrderNo(orderNo);
		CareService careService = this.careServiceService.getById(customerOrder.getServiceId());
		CustomerOrderSchedule customerOrderSchedule = customerOrderScheduleService.getNearByOrderNo(orderNo);
		Integer serviceInstId = customerOrder.getInstId();
		Date currentTime = null;
		if (customerOrderSchedule != null) {
			currentTime = customerOrderSchedule.getServiceStartTime();
		} else {
			currentTime = customerOrder.getServiceStartTime();
		}
		List<Map<?, ?>> instStaffList = instStaffService.queryInstStaffServiceNumber(careService.getWorkTypeId(), serviceInstId, currentTime, realName);
		return instStaffList;
	}

	public void agreeCertification(Integer id) throws BizException {
		Byte srcStatus = CertificationStatus.IN_CERTIFICATION.getValue();
		this.instStaffService.updateCertificationStatus(id, srcStatus.toString(),
				CertificationStatus.HAS_CERTIFICATION.getValue());
	}

	public void refusedCertification(Integer id) throws BizException {
		Byte srcStatus = CertificationStatus.IN_CERTIFICATION.getValue();
		this.instStaffService.updateCertificationStatus(id, srcStatus.toString(),
				CertificationStatus.REFUSED_CERTIFICATION.getValue());
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public void applyCertification(ApplyCertificationFormBean applyCertificationFormBean) throws BizException {
		//this.smsManager.checkSmsVerifyCode(applyCertificationFormBean.getPhone(), applyCertificationFormBean.getVerifyCode());
		InstStaff InstStaff = this.instStaffService.getByIdNoAndPhone(applyCertificationFormBean.getIdNo(), applyCertificationFormBean.getPhone());
		if (InstStaff != null) {
			if (InstStaff.getCertificationStatus() == CertificationStatus.NO_CERTIFICATION.getValue()
					|| InstStaff.getCertificationStatus() == CertificationStatus.REFUSED_CERTIFICATION.getValue()) {
				/*缺省验证码验证审核代码*/
				this.instStaffService.updateCertificationStatus(InstStaff.getId(), String.valueOf(InstStaff.getCertificationStatus()),
						CertificationStatus.IN_CERTIFICATION.getValue());
				
				OAuthLoginFormBean oAuthLoginFormBean = new OAuthLoginFormBean();
				oAuthLoginFormBean.setIdentityType(IdentityType.WECHAT.getValue());
				oAuthLoginFormBean.setIdentifier(applyCertificationFormBean.getOpenId());
				oAuthLoginFormBean.setIdentity(Identity.CAREGIVERS.getValue());
				oAuthLoginFormBean.setNickname(applyCertificationFormBean.getNickname());
				oAuthLoginFormBean.setAvatar(applyCertificationFormBean.getAvatar());
				oAuthLoginFormBean.setSex(applyCertificationFormBean.getSex());
				oAuthLoginFormBean.setRegion(applyCertificationFormBean.getRegion());
				
				UserInfo userInfo = this.userManager.add(oAuthLoginFormBean);
				this.userOAuthService.updateStaffId(userInfo.getId(), InstStaff.getId(), InstStaff.getInstId());
			} else if (InstStaff.getCertificationStatus() == CertificationStatus.IN_CERTIFICATION.getValue()) {
				throw new BizException(ErrorCode.IN_CERTIFICATION);
			}else {
				throw new BizException(ErrorCode.HAS_CERTIFICATION);
			}
		} else {
			throw new BizException(ErrorCode.IDNO_OR_MOBILE_INPUT_ERROR);
		}
	}

	public void cancelCertification(Integer id) throws BizException {
		Integer count = customerOrderService.getOrderCountByStaffId(id);
		if(count > 0) {
			throw new BizException(ErrorCode.THE_STAFF_IS_SERVING);
		}
		Byte srcStatus = CertificationStatus.HAS_CERTIFICATION.getValue();
		this.instStaffService.updateCertificationStatus(id, srcStatus.toString(),
				CertificationStatus.NO_CERTIFICATION.getValue());
	}
	
	/**
	 * 
	 * modifyStaffBindMobile:(手机绑定). <br/> 
	 * 
	 * @author zhoulei 
	 * @param id
	 * @param mobile
	 * @param verifyCode
	 * @throws BizException 
	 * @since JDK 1.8
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public void modifyStaffBindMobile(Integer id, String mobile, String verifyCode) throws BizException {
		this.smsManager.checkSmsVerifyCode(mobile, verifyCode);
		this.instStaffService.updateMobileById(id, mobile);
	}

	/**
	 *
	 * delete:(员工信息刪除). <br/>
	 *
	 * @author csc
	 * @param id
	 * @throws BizException
	 * @since JDK 1.8
	 */
	public void delete(Integer id) throws BizException {
		this.instStaffService.delete(id);
		this.userOAuthService.deleteByStaffId(id);
	}
}
