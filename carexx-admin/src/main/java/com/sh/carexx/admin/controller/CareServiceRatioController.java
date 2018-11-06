package com.sh.carexx.admin.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.care.CareServiceRatioFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;

@RestController
@RequestMapping("/serviceratio")
public class CareServiceRatioController extends BaseController {

	@RequestMapping(value = "/add")
	public BasicRetVal add(@Valid CareServiceRatioFormBean careServiceRatioFormBean, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.addServiceRatio(careServiceRatioFormBean);
	}

	@RequestMapping(value = "/list")
	public String queryForList(CareServiceRatioFormBean careServiceRatioFormBean) {
		return this.ucServiceClient.queryServiceRatioForList(careServiceRatioFormBean);
	}

	@RequestMapping(value = "/modify")
	public BasicRetVal modify(@Valid CareServiceRatioFormBean careServiceRatioFormBean, BindingResult bindingResult) {
		if (careServiceRatioFormBean.getId() == null || bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.modifyServiceRatio(careServiceRatioFormBean);
	}

	@RequestMapping(value = "/enable/{id}")
	public BasicRetVal enable(@PathVariable("id") Integer id) {
		return this.ucServiceClient.enableServiceRatio(id);
	}

	@RequestMapping(value = "/disable/{id}")
	public BasicRetVal disable(@PathVariable("id") Integer id) {
		return this.ucServiceClient.disableServiceRatio(id);
	}
}
