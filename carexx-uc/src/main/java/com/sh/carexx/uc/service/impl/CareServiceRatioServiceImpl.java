package com.sh.carexx.uc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.carexx.bean.care.CareServiceRatioFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CareServiceRatio;
import com.sh.carexx.uc.dao.CareServiceRatioMapper;
import com.sh.carexx.uc.service.CareServiceRatioService;

@Service
public class CareServiceRatioServiceImpl implements CareServiceRatioService {

	@Autowired 
	private CareServiceRatioMapper careServiceRatioMapper;
	
	@Override
	public CareServiceRatio getById(Integer id) {
		return this.careServiceRatioMapper.selectById(id);
	}
	
	@Override
	public CareServiceRatio getByServiceAddress(byte serviceAddress) {
		return this.careServiceRatioMapper.selectByServiceAddress(serviceAddress);
	}
	
	@Override
	public void save(CareServiceRatio careServiceRatio) throws BizException {
		int rows = 0;
		try {
			rows = this.careServiceRatioMapper.insert(careServiceRatio);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		
	}

	@Override
	public Integer getServiceRatioCount(CareServiceRatioFormBean careServiceRatioFormBean) {
		return this.careServiceRatioMapper.selectServiceRatioCount(careServiceRatioFormBean);
	}

	@Override
	public List<Map<?, ?>> queryServiceRatioList(CareServiceRatioFormBean careServiceRatioFormBean) {
		return  this.careServiceRatioMapper.selectServiceRatioList(careServiceRatioFormBean);
	}

	@Override
	public List<CareServiceRatio> queryAllServiceRatio() {
		return this.careServiceRatioMapper.selectAllServiceRatio();
	}
	
	@Override
	public void update(CareServiceRatio careServiceRatio) throws BizException {
		int rows = 0;
		try {
			rows = this.careServiceRatioMapper.update(careServiceRatio);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		
	}

	@Override
	public void updateServiceRatioStatus(Integer id, Byte srcStatus, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.careServiceRatioMapper.updateServiceRatioStatus(id, srcStatus, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

}
