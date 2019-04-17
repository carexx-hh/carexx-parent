package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.order.WorkQuantityReportFormBean;
import com.sh.carexx.model.uc.CustomerOrderSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CustomerOrderScheduleMapper <br/>
 * Function: 订单排班 <br/>
 * Date: 2018年5月25日 上午11:49:18 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface CustomerOrderScheduleMapper {

    /**
     * insert:(添加排班). <br/>
     *
     * @param customerOrderSchedule
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int insert(CustomerOrderSchedule customerOrderSchedule);

    /**
     * selectCustomerOrderSchedule:(查询所有排班). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectCustomerOrderSchedule();

    /**
     * selectById:(通过主键查询). <br/>
     *
     * @param id
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    CustomerOrderSchedule selectById(Long id);

    /**
     * selectByOrderNo:(通过订单编号查询最近排班). <br/>
     *
     * @param orderNo
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    CustomerOrderSchedule selectNearByOrderNo(String orderNo);

    /**
     * modifySchedule:(修改排班状态). <br/>
     *
     * @param orderNo
     * @return
     * @author csc
     * @since JDK 1.8
     */
    int modifySchedule(String orderNo);

    /**
     * selectByOrderNo:(通过订单编号查询). <br/>
     *
     * @param orderNo
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<CustomerOrderSchedule> selectByOrderNo(String orderNo);

    /**
     * selectOrderScheduleStaff:(通过订单号查排班). <br/>
     *
     * @param orderNo
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectScheduleByOrderNo(String orderNo);

    /**
     * selectScheduleByStaffId:(通过订单号和人员Id查询排班). <br/>
     *
     * @param orderNo
     * @param StaffId
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectScheduleByStaffId(String orderNo, String StaffId);

    /**
     * selectByExist:(添加排班时查询是否已排班). <br/>
     *
     * @param orderNo
     * @param serviceStartTime
     * @param serviceEndTime
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<CustomerOrderSchedule> selectByExistence(@Param("orderNo") String orderNo,
                                                  @Param("serviceStartTime") Date serviceStartTime, @Param("serviceEndTime") Date serviceEndTime);

    /**
     * updateSchedule:(合并排班,修改排班信息). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int updateSchedule(CustomerOrderSchedule customerOrderSchedule);

    /**
     * updateStatus:(修改排班状态). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int updateStatus(@Param("id") Long id, @Param("srcStatus") Byte srcStatus, @Param("targetStatus") Byte targetStatus);

    /**
     * updateOrderScheduleCancel:(取消订单时同时删除排班). <br/>
     *
     * @param targetStatus
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    int deleteOrderSchedule(@Param("id") Long id, @Param("targetStatus") Byte targetStatus);

    /**
     * selectByTimeBefore:(通过时间查询排班). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<CustomerOrderSchedule> selectByTime(@Param("recentlySettleDate") Date recentlySettleDate, @Param("settleDate") Date settleDate, @Param("srcStatus") Byte srcStatus, @Param("instId") Integer instId);

    /**
     * selectWorkQuantityReport:(工量结算报表). <br/>
     *
     * @param workQuantityReportFormBean
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> selectWorkQuantityReport(WorkQuantityReportFormBean workQuantityReportFormBean);

    int deleteMappOrderSchedule(Long id);

    /**
     * selectCustomerOrderSchedule:我的收入 订单统计
     *
     * @return
     * @author chenshichao
     * @since JDK 1.8
     */
    Map<?, ?> selectOrderScheduleStatistics(@Param("staffId") Integer staffId, @Param("serviceEndTime") String serviceEndTime);

    /**
     * selectOrderSchedulePresent:(获取当前时间的排班信息). <br/>
     *
     * @return
     * @author csc
     * @since JDK 1.8
     */
    CustomerOrderSchedule selectOrderSchedulePresent(@Param("orderNo") String orderNo);

    /**
     * countOrderSchedule:(获取当前订单进行中的排班数). <br/>
     *
     * @return
     * @author csc
     * @since JDK 1.8
     */
    Integer countOrderSchedule(@Param("orderNo") String orderNo);

    /**
     * updateStaffIdPresentById:(更改当前班次的护工). <br/>
     *
     * @return
     * @author csc
     * @since JDK 1.8
     */
    int updateStaffIdPresentById(@Param("id") Long id, @Param("serviceStaffId") Integer serviceStaffId, @Param("workTypeSettleId") Integer workTypeSettleId);

    /**
     * updateServiceTime:(修改订单结束时间). <br/>
     *
     * @param customerOrder
     * @return
     * @author csc
     * @since JDK 1.8
     */
    int updateServiceTime(CustomerOrderSchedule customerOrderSchedule);
}