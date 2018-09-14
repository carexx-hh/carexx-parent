package com.sh.carexx.uc.service;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CustomerOrder;

import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: 客户订单 <br/>
 * 
 * @author hetao
 * @since JDK 1.8
 */
public interface CustomerOrderService {

	/**
	 * 
	 * save:(添加方法). <br/>
	 * 
	 * @author hetao
	 * @param customerOrder
	 * @throws BizException
	 * @since JDK 1.8
	 */
	void save(CustomerOrder customerOrder) throws BizException;
	
	/**
	 * 
	 * confirmCompleted:(完成订单). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrder 
	 * @since JDK 1.8
	 */
	void confirmCompleted(CustomerOrder customerOrder) throws BizException;

	/**
	 *
	 * confirmCompleted:(查询所有线上订单). <br/>
	 *
	 * @author hetao
	 * @since JDK 1.8
	 */
	List<CustomerOrder> getAllOrder();

	/**
	 * 
	 * getByUserId:(客户端通过客户id查询服务订单分页). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> getByUserId(CustomerOrderQueryFormBean customerOrderQueryFormBean);


	/**
	 *
	 * selectDoneOrderByUserId:(患者端通过客户id查询已支付和完成订单). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> getDoneOrderByUserId(Integer userId);

	/**
	 *
	 * selectOrderDetail:(患者端通过订单号查询订单详情). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> getOrderDetail(String orderNo);

	/**
	 * 
	 * getCustomerOrderCount:(查询客户预约服务订单总数). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer getCustomerOrderCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * queryCustomerOrderList:(查询客户预约服务订单分页). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> queryCustomerOrderList(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * getByOrderNo:(通过订单号查询). <br/>
	 * 
	 * @author zhoulei
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	CustomerOrder getByOrderNo(String orderNo);
	
	/**
	 * 
	 * queryOrderByExist:(查询客户下单重复). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?,?>> queryOrderExistence(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectArrangeOrder:(查询可派单订单). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instId
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?,?>> queryMappArrangeOrder(String orderStatus, Integer instId);
	/**
	 * 
	 * updateStatus:(修改订单状态). <br/>
	 * 
	 * @author hetao
	 * @param orderNo
	 * @throws BizException
	 * @since JDK 1.8
	 */
	void updateStatus(String orderNo, Byte srcStatus, Byte targetStatus) throws BizException;
	
	/**
	 * 
	 * updateOrderCancel:(取消订单). <br/> 
	 * 
	 * @author hetao 
	 * @param orderNo
	 * @param targetStatus
	 * @throws BizException 
	 * @since JDK 1.8
	 */
	void updateOrderCancel(String orderNo, Byte targetStatus) throws BizException;

	/**
	 *
	 * mappOrderCancel:(取消订单). <br/>
	 *
	 * @author hetao
	 * @param orderNo
	 * @param targetStatus
	 * @throws BizException
	 * @since JDK 1.8
	 */
	void mappOrderCancel(String orderNo, Byte targetStatus) throws BizException;
	
	/**
	 * 
	 * queryIncomeCount:(收入统计). <br/> 
	 * 
	 * @author zhoulei 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> queryIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * queryInstIncomeCount:(机构收入统计). <br/> 
	 * 
	 * @author zhoulei 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> queryInstIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * updateAdjustAmt:(调整订单金额). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrder
	 * @throws BizException 
	 * @since JDK 1.8
	 */
	void update(CustomerOrder customerOrder) throws BizException;

	/**
	 *
	 * updateOrderAmtAndHoliday:(修改订单金额和节假日天数). <br/>
	 *
	 * @author hetao
	 * @param customerOrder
	 * @return
	 * @since JDK 1.8
	 */
	void updateOrderAmtAndHoliday(CustomerOrder customerOrder) throws BizException;

	/**
	 *
	 * updateServiceEndTime:(修改订单结束时间). <br/>
	 *
	 * @author hetao
	 * @param customerOrder
	 * @return
	 * @since JDK 1.8
	 */
	void updateServiceEndTime(CustomerOrder customerOrder) throws BizException;
}
