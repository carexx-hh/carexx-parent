package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.customer.InstCustomerFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.InstCustomer;
import com.sh.carexx.uc.dao.InstCustomerMapper;
import com.sh.carexx.uc.service.InstCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InstCustomerServiceImpl implements InstCustomerService {
	@Autowired
	private InstCustomerMapper instCustomerMapper;

	@Override
	public InstCustomer getById(Integer id) {
		return this.instCustomerMapper.selectById(id);
	}

	@Override
	public Integer getInstCustomerCount(InstCustomerFormBean instCustomerFormBean) {
		return this.instCustomerMapper.selectInstCustomerCount(instCustomerFormBean);
	}

	@Override
	public List<Map<?, ?>> queryInstCustomerList(InstCustomerFormBean instCustomerFormBean) {
		return this.instCustomerMapper.selectInstCustomerList(instCustomerFormBean);
	}

	@Override
	public void save(InstCustomer instCustomer) throws BizException {
		int rows = 0;
		try {
			rows = this.instCustomerMapper.insert(instCustomer);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void update(InstCustomer instCustomer) throws BizException {
		int rows = 0;
		try {
			rows = this.instCustomerMapper.update(instCustomer);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void delete(Integer id) throws BizException {
		int rows = 0;
		try {
			rows = this.instCustomerMapper.delete(id);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public InstCustomer queryCustomerExistence(InstCustomer instCustomer) {
		return this.instCustomerMapper.selectCustomerExistence(instCustomer);
	}

}
