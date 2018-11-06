package com.sh.carexx.uc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.carexx.bean.care.CareServiceRatioFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.common.web.PagerBean;
import com.sh.carexx.uc.manager.CareServiceRatioManager;
import com.sh.carexx.uc.service.CareServiceRatioService;

@RestController
@RequestMapping("/serviceratio")
public class CareServiceRatioController {

	@Autowired
	private CareServiceRatioManager careServiceRatioManager;

	@Autowired
	private CareServiceRatioService careServiceRatioService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal add(@RequestBody CareServiceRatioFormBean careServiceRatioFormBean) {
		try {
			this.careServiceRatioManager.add(careServiceRatioFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryForList(@RequestBody CareServiceRatioFormBean careServiceRatioFormBean) {
		Integer totalNum = this.careServiceRatioService.getServiceRatioCount(careServiceRatioFormBean);
		List<Map<?, ?>> resultList = null;
		if (totalNum > 0) {
			resultList = this.careServiceRatioService.queryServiceRatioList(careServiceRatioFormBean);
		}
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, new PagerBean(totalNum, resultList)).toJSON();
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal modfiy(@RequestBody CareServiceRatioFormBean careServiceRatioFormBean) {
		try {
			this.careServiceRatioManager.modfiy(careServiceRatioFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
	public BasicRetVal enable(@PathVariable("id") Integer id) {
		try {
			this.careServiceRatioManager.enable(id);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
	public BasicRetVal disable(@PathVariable("id") Integer id) {
		try {
			this.careServiceRatioManager.disable(id);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}
}
