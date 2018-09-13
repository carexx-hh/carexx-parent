package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.model.uc.CustomerOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: 客户预约服务订单 <br/>
 * 
 * @author hetao
 * @since JDK 1.8
 */
public interface CustomerOrderMapper {

	/**
	 * 
	 * insert:(添加方法). <br/>
	 * 
	 * @author hetao
	 * @param customerOrder
	 * @return
	 * @since JDK 1.8
	 */
	int insert(CustomerOrder customerOrder);
	
	/**
	 * 
	 * confirmCompleted:(完成订单). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrder
	 * @return 
	 * @since JDK 1.8
	 */
	int confirmCompleted(CustomerOrder customerOrder);

	/**
	 *
	 * confirmCompleted:(查询所有线上订单). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	List<CustomerOrder> selectAllOrder();

	/**
	 * 
	 * selectByUserId:(患者端通过客户id查询服务订单分页). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectByUserId(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 *
	 * selectDoneOrderByUserId:(患者端通过客户id查询已支付和完成订单). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectDoneOrderByUserId(@Param("userId") Integer userId);
	/**
	 *
	 * selectOrderDetail:(患者端通过订单号查询订单详情). <br/>
	 *
	 * @author hetao
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectOrderDetail(@Param("orderNo") String orderNo);
	/**
	 * 
	 * selectCustomerOrderCount:(查询客户预约服务订单总数). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectCustomerOrderCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectCustomerOrderList:(查询客户预约服务订单分页). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectCustomerOrderList(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectByOrderNo:(通过订单编号查询). <br/>
	 * 
	 * @author zhoulei
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	CustomerOrder selectByOrderNo(String orderNo);
	
	/**
	 * 
	 * selectOrderByExist:(客户下单查重复). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?,?>> selectOrderExistence(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * selectMappArrangeOrder:(移动端查询可安排订单). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instId
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?,?>> selectMappArrangeOrder(@Param("orderStatus") String orderStatus, @Param("instId") Integer instId);
	
	/**
	 * 
	 * updateStatus:(修改订单状态). <br/> 
	 * 
	 * @author hetao
	 * @param orderNo
	 * @return 
	 * @since JDK 1.8
	 */
	int updateStatus(@Param("orderNo") String orderNo, @Param("srcStatus") Byte srcStatus, @Param("targetStatus") Byte targetStatus);
	
	/**
	 * 
	 * updateOrderCancel:(取消订单). <br/> 
	 * 
	 * @author hetao 
	 * @param orderNo
	 * @param targetStatus
	 * @return 
	 * @since JDK 1.8
	 */
	int updateOrderCancel(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);

	/**
	 *
	 * mappOrderCancel:(移动端取消订单). <br/>
	 *
	 * @author hetao
	 * @param orderNo
	 * @param targetStatus
	 * @return
	 * @since JDK 1.8
	 */
	int mappOrderCancel(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);
	
	/**
	 * 
	 * selectIncomeCount:(收入统计). <br/> 
	 * 
	 * @author zhoulei 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> selectIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * updateAdjustAmt:(调整订单金额). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrder
	 * @return 
	 * @since JDK 1.8
	 */
	int update(CustomerOrder customerOrder);

	/**
	 *
	 * updateOrderAmtAndHoliday:(修改订单金额和节假日天数). <br/>
	 *
	 * @author hetao
	 * @param customerOrder
	 * @return
	 * @since JDK 1.8
	 */
	int updateOrderAmtAndHoliday(CustomerOrder customerOrder);

	/**
	 *
	 * updateServiceEndTime:(修改订单结束时间). <br/>
	 *
	 * @author hetao
	 * @param customerOrder
	 * @return
	 * @since JDK 1.8
	 */
	int updateServiceEndTime(CustomerOrder customerOrder);
}