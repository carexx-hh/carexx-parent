package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.staff.InstStaffQueryFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.InstStaff;
import com.sh.carexx.uc.dao.CustomerOrderMapper;
import com.sh.carexx.uc.dao.InstStaffMapper;
import com.sh.carexx.uc.service.InstStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InstStaffServiceImpl implements InstStaffService {

	@Autowired
	private InstStaffMapper instStaffMapper;

	@Autowired
	private CustomerOrderMapper customerOrderMapper;
	
	@Override
	public void save(InstStaff instStaff) throws BizException {
		int rows = 0;
		try {
			rows = this.instStaffMapper.insert(instStaff);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public InstStaff getById(Integer id) {
		return this.instStaffMapper.selectById(id);
	}


	@Override
	public Map<?, ?> getDetailById(Integer id) {
		return this.instStaffMapper.selectDetailById(id);
	}
	
	@Override
	public InstStaff getByIdNo(String idNo, Integer instId) {
		return this.instStaffMapper.selectByIdNo(idNo, instId);
	}

	@Override
	public InstStaff getByIdNoAndPhone(String idNo, String phone) {
		return this.instStaffMapper.selectByIdNoAndPhone(idNo, phone);
	}
	
	@Override
	public Integer getInstStaffCountByServiceId(InstStaffQueryFormBean instStaffQueryFormBean) {
		return this.instStaffMapper.selectInstStaffCountByServiceId(instStaffQueryFormBean);
	}

	@Override
	public List<Map<?, ?>> queryInstStaffListByServiceId(InstStaffQueryFormBean instStaffQueryFormBean) {
		return this.instStaffMapper.selectInstStaffListByServiceId(instStaffQueryFormBean);
	}
	
	@Override
	public List<Map<?, ?>> queryInstStaffServiceNumber(Integer workTypeId,Integer serviceInstId,Date currentTime, String realName) {
		return this.instStaffMapper.selectInstStaffServiceNumber(workTypeId,serviceInstId,currentTime,realName);
	}
	
	@Override
	public Integer getInstStaffCount(InstStaffQueryFormBean instStaffQueryFormBean) {
		return instStaffMapper.selectInstStaffCount(instStaffQueryFormBean);
	}

	@Override
	public List<Map<String, Object>> queryInstStaffList(InstStaffQueryFormBean instStaffQueryFormBean) {
		List<Map<String, Object>> instStaffList = instStaffMapper.selectInstStaffList(instStaffQueryFormBean);
		for(Map<String, Object> instStaffMap: instStaffList) {
			Integer orderCount = customerOrderMapper.selectOrderCountByStaffId(Integer.parseInt(String.valueOf(instStaffMap.get("id"))));
			instStaffMap.put("orderCount", orderCount);
		}
		return instStaffList;
	}

	@Override
	public List<Map<?, ?>> queryAllInstStaff(InstStaffQueryFormBean instStaffQueryFormBean) {
		return instStaffMapper.selectAllInstStaff(instStaffQueryFormBean);
	}
	@Override
	public void update(InstStaff instStaff) throws BizException {
		int rows = 0;
		try {
			rows = this.instStaffMapper.update(instStaff);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void delete(Integer id) throws BizException {
		int rows = 0;
		try {
			rows = this.instStaffMapper.delete(id);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void updateCertificationStatus(Integer id, String srcStatus, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.instStaffMapper.updateCertificationStatus(id, srcStatus, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public List<Map<?, ?>> queryInstStaffByCertificationStatus(Integer instId, Byte certificationStatus) {
		return this.instStaffMapper.selectInstStaffByCertificationStatus(instId, certificationStatus);
	}

	@Override
	public void updateMobileById(Integer id, String mobile) throws BizException {
		int rows = 0;
		try {
			rows = this.instStaffMapper.updateMobileById(id, mobile);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

}
