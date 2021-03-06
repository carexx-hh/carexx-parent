package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.bean.order.MappCustomerOrderQueryFormBean;
import com.sh.carexx.model.uc.CustomerOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: 客户预约服务订单 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface CustomerOrderMapper {

    /**
     * insert:(添加方法). <br/>
     *
     * @param customerOrder
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int insert(CustomerOrder customerOrder);

    /**
     * confirmCompleted:(完成订单). <br/>
     *
     * @param customerOrder
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int confirmCompleted(CustomerOrder customerOrder);

    /**
     * confirmCompleted:(查询所有线上订单). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<CustomerOrder> selectAllOrder();

    /**
     * selectOrderByInstId:(根据instId查询订单). <br/>
     *
     * @return
     * @author chenshichao
     * @since JDK 1.8
     */
    List<CustomerOrder> selectOrderByInstId(int instId);

    /**
     * selectAllOrderByInstId:(根据instId查询订单). <br/>
     *
     * @return
     * @author chenshichao
     * @since JDK 1.8
     */
    List<CustomerOrder> selectAllOrderByInstId(int instId);

    /**
     * selectByUserId:(患者端通过客户id查询服务订单分页). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectByUserId(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectDoneOrderByUserId:(患者端通过客户id查询已支付和完成订单). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectDoneOrderByUserId(@Param("userId") Integer userId);

    /**
     * selectOrderDetail:(患者端通过订单号查询订单详情). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectOrderDetail(@Param("orderNo") String orderNo);

    /**
     * selectCustomerOrderCount:(查询客户预约服务订单总数). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    Integer selectCustomerOrderCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectCustomerOrderList:(查询客户预约服务订单分页). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectCustomerOrderList(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectByWorkTypeIdCount:(通过工种ID统计). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    Integer selectByWorkTypeIdCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectByWorkTypeIdList:(通过工种ID查询). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectByWorkTypeIdList(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectStaffScheduleCount:(人员排班查看统计). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    Integer selectStaffScheduleCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectStaffScheduleList:(人员排班查看). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectStaffScheduleList(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectByOrderNo:(通过订单编号查询). <br/>
     *
     * @param orderNo
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    CustomerOrder selectByOrderNo(String orderNo);

    /**
     * selectOrderByExist:(客户下单查重复). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectOrderExistence(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectMappByOrderStatus:(移动端通过订单状态查询订单). <br/>
     *
     * @param instId
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectMappByOrderStatus(@Param("orderStatus") String orderStatus, @Param("instId") Integer instId);

    /**
     * selectMappByOrderStatusAndServiceStatus:(移动端通过订单状态和排班状态查询订单). <br/>
     *
     * @param orderStatus
     * @param serviceStatus
     * @param instId
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectMappByOrderStatusAndServiceStatus(MappCustomerOrderQueryFormBean mappCustomerOrderQueryFormBean);

    /**
     * selectMappWaitSchedule:(查询未派单). <br/>
     *
     * @param instId
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectMappWaitSchedule(Integer instId);

    /**
     * selectMappManagerDoOrderSchedule:(移动端管理端查询已派单订单). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectMappManagerDoOrderSchedule(MappCustomerOrderQueryFormBean mappCustomerOrderQueryFormBean);

    /**
     * selectOrderCountByStaffId:(通过人员id统计订单). <br/>
     *
     * @param staffId
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    Integer selectOrderCountByStaffId(Integer staffId);

    /**
     * updateStatus:(修改订单状态). <br/>
     *
     * @param orderNo
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int updateStatus(@Param("orderNo") String orderNo, @Param("srcStatus") Byte srcStatus, @Param("targetStatus") Byte targetStatus);

    /**
     * updateOrderCancel:(取消订单). <br/>
     *
     * @param orderNo
     * @param targetStatus
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int updateOrderCancel(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);

    /**
     * updateOrderDelete:(删除订单). <br/>
     *
     * @param orderNo
     * @param targetStatus
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    int updateOrderDelete(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);

    /**
     * mappOrderCancel:(移动端取消订单). <br/>
     *
     * @param orderNo
     * @param targetStatus
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int mappOrderCancel(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);

    /**
     * selectIncomeCount:(收入统计). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<String, Object>> selectIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectInstIncomeCount:(机构收入统计). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<String, Object>> selectInstIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * selectStaffIncomeCount:(人员收入统计). <br/>
     *
     * @param customerOrderQueryFormBean
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<String, Object>> selectStaffIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

    /**
     * updateAdjustAmt:(调整订单金额). <br/>
     *
     * @param customerOrder
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int update(CustomerOrder customerOrder);

    /**
     * updateOrderAmtAndHoliday:(修改订单金额和节假日天数). <br/>
     *
     * @param customerOrder
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int updateOrderAmtAndHoliday(CustomerOrder customerOrder);

    /**
     * updateOperatorIdAndOrderAmtAndHoliday:(修改操作人,订单金额和节假日天数). <br/>
     *
     * @param customerOrder
     * @return
     * @author csc
     * @since JDK 1.8
     */
    int updateOperatorIdAndOrderAmtAndHoliday(CustomerOrder customerOrder);

    /**
     * updateServiceEndTime:(修改订单结束时间). <br/>
     *
     * @param customerOrder
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int updateServiceEndTime(CustomerOrder customerOrder);

    /**
     * updateServiceTime:(修改订单时间). <br/>
     *
     * @param customerOrder
     * @return
     * @author csc
     * @since JDK 1.8
     */
    int updateServiceTime(CustomerOrder customerOrder);

    /**
     * updateOperatorId:(修改操作员). <br/>
     *
     * @param customerOrder
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    int updateOperatorId(CustomerOrder customerOrder);

    /**
     * selectProofInfoByOrderNo
     *
     * @param orderNo
     * @return
     * @author chenshichao
     * @since JDK 1.8
     */
    Map<?, ?> selectProofInfoByOrderNo(String orderNo);
}