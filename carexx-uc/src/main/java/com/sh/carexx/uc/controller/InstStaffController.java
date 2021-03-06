package com.sh.carexx.uc.controller;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.bean.staff.InstStaffFormBean;
import com.sh.carexx.bean.staff.InstStaffQueryFormBean;
import com.sh.carexx.bean.user.ApplyCertificationFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.common.web.PagerBean;
import com.sh.carexx.uc.manager.InstStaffManager;
import com.sh.carexx.uc.service.InstStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inststaff")
public class InstStaffController {

	@Autowired
	private InstStaffService instStaffService;

	@Autowired
	private InstStaffManager instStaffManager;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal add(@RequestBody InstStaffFormBean InstStaffFormBean) {
		try {
			this.instStaffManager.add(InstStaffFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryForList(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean) {
		Integer totalNum = this.instStaffService.getInstStaffCount(instStaffQueryFormBean);
		List<Map<String, Object>> resultList = null;
		if (totalNum > 0) {
			resultList = this.instStaffService.queryInstStaffList(instStaffQueryFormBean);
		}
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, new PagerBean(totalNum, resultList)).toJSON();
	}

	@RequestMapping(value = "/all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryForAll(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean) {
		List<Map<?, ?>> resultList = null;
		resultList = this.instStaffService.queryAllInstStaff(instStaffQueryFormBean);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, resultList).toJSON();
	}

	@RequestMapping(value = "/get_id/{id}", method = RequestMethod.GET)
	public String getId(@PathVariable("id") Integer id) {
		Map<?, ?> resultList = null;
		resultList = this.instStaffService.getDetailById(id);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, resultList).toJSON();
	}

	@RequestMapping(value = "/list_by_serviceid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryForListByServiceId(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean) {
		Integer totalNum = this.instStaffService.getInstStaffCountByServiceId(instStaffQueryFormBean);
		List<Map<?, ?>> resultList = null;
		if (totalNum > 0) {
			resultList = this.instStaffService.queryInstStaffListByServiceId(instStaffQueryFormBean);
		}
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, new PagerBean(totalNum, resultList)).toJSON();
	}

	@RequestMapping(value = "/all_by_certification_status/{instId}/{certificationStatus}", method = RequestMethod.GET)
	public String queryInstStaffByCertificationStatus(@PathVariable("instId") Integer instId,
			@PathVariable("certificationStatus") Byte certificationStatus) {
		List<Map<?, ?>> resultList = null;
		resultList = this.instStaffService.queryInstStaffByCertificationStatus(instId, certificationStatus);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, resultList).toJSON();
	}

	@RequestMapping(value = "/staff_schedule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryInstStaffSchedule(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<?, ?>> instStaffList = null;
		try {
			instStaffList = this.instStaffManager.staffSchedule(customerOrderQueryFormBean);
		} catch (BizException e) {
			return new DataRetVal(CarexxConstant.RetCode.SERVER_ERROR, null).toJSON();
		}
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, instStaffList).toJSON();

	}

	@RequestMapping(value = "/mapp_all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryMappAllInstStaff(@RequestBody InstStaffQueryFormBean instStaffQueryFormBean) {
		List<Map<?, ?>> instStaffList = null;
		try {
			instStaffList = this.instStaffManager.queryMappAllInstStaff(instStaffQueryFormBean);
		} catch (BizException e) {
			return new DataRetVal(CarexxConstant.RetCode.SERVER_ERROR, null).toJSON();
		}
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, instStaffList).toJSON();

	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal modify(@RequestBody InstStaffFormBean instStaffFormBean) {
		try {
			this.instStaffManager.modify(instStaffFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public BasicRetVal delete(@PathVariable("id") Integer id) {
		try {
			this.instStaffManager.delete(id);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/agree_certification/{id}", method = RequestMethod.GET)
	public BasicRetVal AgreeCertification(@PathVariable("id") Integer id) {
		try {
			this.instStaffManager.agreeCertification(id);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/refused_certification/{id}", method = RequestMethod.GET)
	public BasicRetVal RefusedCertification(@PathVariable("id") Integer id) {
		try {
			this.instStaffManager.refusedCertification(id);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/apply_certification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal applyCertification(@RequestBody ApplyCertificationFormBean applyCertificationFormBean) {
		try {
			this.instStaffManager.applyCertification(applyCertificationFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/cancel_certification/{id}", method = RequestMethod.GET)
	public BasicRetVal cancelCertification(@PathVariable("id") Integer id) {
		try {
			this.instStaffManager.cancelCertification(id);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}
	
	@RequestMapping(value = "/staff_bind_mobile/{id}/{mobile}/{verifyCode}", method = RequestMethod.GET)
    public BasicRetVal modifyStaffBindMobile(@PathVariable("id") Integer id, @PathVariable("mobile") String mobile, @PathVariable("verifyCode") String verifyCode) {
        try {
        	this.instStaffManager.modifyStaffBindMobile(id, mobile, verifyCode);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
    	return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
    }
}
