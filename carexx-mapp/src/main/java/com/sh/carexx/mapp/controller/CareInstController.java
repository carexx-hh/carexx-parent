package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.care.CareInstFormBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/careinst")
public class CareInstController extends BaseController {

	@RequestMapping(value = "/all")
	public String queryForAll(CareInstFormBean careInstFormBean) {
		return this.ucServiceClient.queryAllCareInst(careInstFormBean);
	}

	@RequestMapping(value = "/list_Region")
	public String queryInstRegion(){
		return this.ucServiceClient.queryInstRegion();
	}
}
