package com.sh.carexx.uc.dao;

import org.apache.ibatis.annotations.Param;

import com.sh.carexx.model.uc.OrderPayment;

/**
 * 
 * ClassName: OrderPaymentMapper <br/> 
 * Function: 订单支付 <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * Date: 2018年5月21日 下午5:46:10 <br/> 
 * 
 * @author zhoulei 
 * @since JDK 1.8
 */
public interface OrderPaymentMapper {

	/**
	 * 
	 * selectById:(通过id查询订单). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	OrderPayment selectById(Long id);

	/**
	 * 
	 * selectByOrderNo:(通过订单编号查询订单). <br/>
	 * 
	 * @author zhoulei
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	OrderPayment selectByOrderNo(String orderNo);

	/**
	 * 
	 * insert:(添加订单支付信息方法). <br/>
	 * 
	 * @author zhoulei
	 * @param orderPayment
	 * @return
	 * @since JDK 1.8
	 */
	int insert(OrderPayment orderPayment);

	/**
	 * 
	 * update:(修改订单支付信息方法). <br/>
	 * 
	 * @author zhoulei
	 * @param orderPayment
	 * @return
	 * @since JDK 1.8
	 */
	int update(OrderPayment orderPayment);

	/**
	 *
	 * updatePayAmt:(修改订单支付金额). <br/>
	 *
	 * @author hetao
	 * @param orderPayment
	 * @return
	 * @since JDK 1.8
	 */
	int updatePayAmt(OrderPayment orderPayment);
	
	/**
	 * 
	 * updatePaymentDelete:(删除订单支付). <br/> 
	 * 
	 * @author zhoulei 
	 * @param orderNo
	 * @param targetStatus
	 * @return 
	 * @since JDK 1.8
	 */
	int updatePaymentDelete(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);
	
	/**
	 * 
	 * updatePayType:(修改支付方式). <br/> 
	 * 
	 * @author zhoulei 
	 * @param orderNo
	 * @param payType
	 * @return 
	 * @since JDK 1.8
	 */
	int updatePayType(@Param("orderNo") String orderNo, @Param("payType") Byte payType);
}