package com.sh.carexx.mapp.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.common.web.BasicRetVal;

@RestController
@RequestMapping("/inststaff")
public class InstStaffController extends BaseController {

	@RequestMapping(value = "/serviceNum")
	public String queryInstStaffServiceNum(@Valid CustomerOrderQueryFormBean customerOrderQueryFormBean) {

		return this.ucServiceClient.queryInstStaffServiceNum(customerOrderQueryFormBean);
	}
	
	@RequestMapping(value = "/agree_certification/{id}")
	public BasicRetVal AgreeCertification(@PathVariable("id") Integer id) {
		return this.ucServiceClient.agreeCertification(id);
	}
	
	@RequestMapping(value = "/refused_certification/{id}")
	public BasicRetVal RefusedCertification(@PathVariable("id") Integer id) {
		return this.ucServiceClient.refusedCertification(id);
	}
	
	@RequestMapping(value = "/all_by_certification_status/{instId}/{certificationStatus}")
	public String queryInstStaffByCertificationStatus(@PathVariable("instId") Integer instId, @PathVariable("certificationStatus") Byte certificationStatus) {
		return this.ucServiceClient.queryInstStaffByCertificationStatus(instId, certificationStatus);
	}
}
