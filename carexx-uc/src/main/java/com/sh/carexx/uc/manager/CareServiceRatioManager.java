package com.sh.carexx.uc.manager;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.carexx.bean.care.CareServiceRatioFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.UseStatus;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CareServiceRatio;
import com.sh.carexx.uc.service.CareServiceRatioService;

@Service
public class CareServiceRatioManager {

	@Autowired
	private CareServiceRatioService careServiceRatioService;

	public void add(CareServiceRatioFormBean careServiceRatioFormBean) throws BizException {
		CareServiceRatio careServiceRatio = this.careServiceRatioService
				.getByServiceAddress(careServiceRatioFormBean.getServiceAddress());
		if (careServiceRatio != null) {
			throw new BizException(ErrorCode.CARE_SERVICE_RATIO_EXISTS_ERROR);
		}
		careServiceRatio = new CareServiceRatio();
		careServiceRatio.setServiceAddress(careServiceRatioFormBean.getServiceAddress());
		careServiceRatio.setServiceRatio(new BigDecimal(careServiceRatioFormBean.getServiceRatio()));
		careServiceRatio.setServiceRatioStatus(UseStatus.ENABLED.getValue());
		this.careServiceRatioService.save(careServiceRatio);
	}

	public void modfiy(CareServiceRatioFormBean careServiceRatioFormBean) throws BizException {
		CareServiceRatio careServiceRatio = new CareServiceRatio();
		careServiceRatio.setId(careServiceRatioFormBean.getId());
		careServiceRatio.setServiceRatio(new BigDecimal(careServiceRatioFormBean.getServiceRatio()));
		this.careServiceRatioService.update(careServiceRatio);
	}

	public void enable(Integer id) throws BizException {
		this.careServiceRatioService.updateServiceRatioStatus(id, UseStatus.DISABLED.getValue(),
				UseStatus.ENABLED.getValue());
	}

	public void disable(Integer id) throws BizException {
		this.careServiceRatioService.updateServiceRatioStatus(id, UseStatus.ENABLED.getValue(),
				UseStatus.DISABLED.getValue());
	}
}