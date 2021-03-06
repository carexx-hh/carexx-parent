package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.bean.staff.InstStaffFormBean;
import com.sh.carexx.bean.staff.InstStaffQueryFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/inststaff")
public class InstStaffController extends BaseController {

	@RequestMapping(value = "/staff_schedule")
	public String queryInstStaffSchedule(@Valid CustomerOrderQueryFormBean customerOrderQueryFormBean) {

		return this.ucServiceClient.queryInstStaffSchedule(customerOrderQueryFormBean);
	}
	
	@RequestMapping(value = "/mapp_all")
	public String queryMappAllInstStaff(@Valid InstStaffQueryFormBean instStaffQueryFormBean) {

		return this.ucServiceClient.queryMappAllInstStaff(instStaffQueryFormBean);
	}
	
	@RequestMapping(value = "/get_id")
	public String getId() {
		Integer id = this.getCurrentUserOAuth().getStaffId();
		return this.ucServiceClient.getInstStaffId(id);
	}
	
	@RequestMapping(value = "/get_staffId/{id}")
	public String getStaffId(@PathVariable("id") Integer id) {
		return this.ucServiceClient.getInstStaffId(id);
	}
	
	@RequestMapping(value = "/agree_certification/{id}")
	public BasicRetVal AgreeCertification(@PathVariable("id") Integer id) {
		return this.ucServiceClient.agreeCertification(id);
	}
	
	@RequestMapping(value = "/refused_certification/{id}")
	public BasicRetVal RefusedCertification(@PathVariable("id") Integer id) {
		return this.ucServiceClient.refusedCertification(id);
	}
	
	@RequestMapping(value = "/cancel_certification/{id}")
	public BasicRetVal cancelCertification(@PathVariable("id") Integer id) {
		return this.ucServiceClient.cancelCertification(id);
	}
	
	@RequestMapping(value = "/all_by_certification_status/{certificationStatus}")
	public String queryInstStaffByCertificationStatus(@PathVariable("certificationStatus") Byte certificationStatus) {
		Integer instId = this.getCurrentUserOAuth().getInstId();
		return this.ucServiceClient.queryInstStaffByCertificationStatus(instId, certificationStatus);
	}
	
	@RequestMapping(value = "/modify")
	public BasicRetVal modify(@Valid InstStaffFormBean instStaffFormBean, BindingResult bindingResult) {
		if (instStaffFormBean.getId() == null || bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.modifyInstStaff(instStaffFormBean);
	}
	
	@RequestMapping(value = "/staff_bind_mobile/{mobile}/{verifyCode}")
	public BasicRetVal staffBindMobile(@PathVariable("mobile") String mobile,
			@PathVariable("verifyCode") String verifyCode) {
		Integer id = this.getCurrentUserOAuth().getStaffId();
		return this.ucServiceClient.modifyStaffBindMobile(id, mobile, verifyCode);
	}
}
