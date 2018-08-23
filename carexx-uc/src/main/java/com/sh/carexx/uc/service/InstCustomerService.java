package com.sh.carexx.uc.service;

import com.sh.carexx.bean.customer.InstCustomerFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.InstCustomer;

import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: InstCustomerService <br/>
 * Function: 客户信息 <br/>
 * 
 * @author hetao
 * @since JDK 1.8
 */
public interface InstCustomerService {
	/**
	 * 
	 * getInstCustomerCount:(分页条件总数统计). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	Integer getInstCustomerCount(InstCustomerFormBean instCustomerFormBean);

	/**
	 * 
	 * queryInstCustomerList:(左连接医护机构并分页查询客户信息). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> queryInstCustomerList(InstCustomerFormBean instCustomerFormBean);

	/**
	 * 
	 * insert:(新增客户信息). <br/>
	 * 
	 * @author hetao
	 * @param instCustomer
	 * @since JDK 1.8
	 */
	void save(InstCustomer instCustomer) throws BizException;

	/**
	 * 
	 * update:(修改客户信息). <br/>
	 * 
	 * @author hetao
	 * @param instCustomer
	 * @since JDK 1.8
	 */
	void update(InstCustomer instCustomer) throws BizException;

	/**
	 * 
	 * delete:(停用客户信息). <br/>
	 * 
	 * @author hetao
	 * @param id
	 * @since JDK 1.8
	 */
	void delete(Integer id) throws BizException;

	/**
	 * 
	 * getByInstIdAndRealName:(通过机构id和客户名查客户信息). <br/>
	 * 
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	InstCustomer queryCustomerExistence(InstCustomer instCustomer);
}
