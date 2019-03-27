package com.sh.carexx.uc.service;

import com.sh.carexx.bean.order.WorkQuantityReportFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CustomerOrderSchedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CustomerOrderScheduleService <br/>
 * Function: 订单排班 <br/>
 * Date: 2018年5月25日 上午11:50:04 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface CustomerOrderScheduleService {
    /**
     * save:(添加排班). <br/>
     *
     * @param customerOrderSchedule
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    void save(CustomerOrderSchedule customerOrderSchedule) throws BizException;

    /**
     * queryCustomerOrderSchedule:(查询所有排班). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> queryCustomerOrderSchedule();

    /**
     * getById:(通过id查询). <br/>
     *
     * @param id
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    CustomerOrderSchedule getById(Long id);

    /**
     * getNearByOrderNo:(查询当前订单最近排班). <br/>
     *
     * @param orderNo
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    CustomerOrderSchedule getNearByOrderNo(String orderNo);

    /**
     * getByOrderNo:(通过订单号查询). <br/>
     *
     * @param orderNo
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<CustomerOrderSchedule> getByOrderNo(String orderNo);

    /**
     * queryOrderScheduleStaff:(通过订单号查询排班). <br/>
     *
     * @param orderNo
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<?, ?>> queryScheduleByOrderNo(String orderNo);

    /**
     * queryScheduleByStaffId:(通过订单号和人员查询排班). <br/>
     *
     * @param orderNo
     * @param staffId
     * @return
     * @author zhoulei
     * @since JDK 1.8
     */
    List<Map<?, ?>> queryScheduleByStaffId(String orderNo, String staffId);

    /**
     * queryByExist:(添加排班时查询是否已排班). <br/>
     *
     * @param orderNo
     * @param serviceStartTime
     * @param serviceEndTime
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<CustomerOrderSchedule> queryByExistence(String orderNo, Date serviceStartTime, Date serviceEndTime);

    /**
     * updateSchedule:(合并排班,修改排班信息). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    void updateSchedule(CustomerOrderSchedule customerOrderSchedule) throws BizException;

    /**
     * updateStatus:(修改订单状态). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    void updateStatus(Long id, Byte srcStatus, Byte targetStatus) throws BizException;

    /**
     * deleteOrderSchedule:(取消订单时同时删除排班). <br/>
     *
     * @param targetStatus
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    void deleteOrderSchedule(Long id, Byte targetStatus) throws BizException;

    /**
     * getByTimeBefore:(通过时间查询排班). <br/>
     *
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<CustomerOrderSchedule> getByTime(Date recentlySettleDate, Date settleDate, Byte srcStatus, Integer instId);

    /**
     * queryWorkQuantityReport:(工量结算报表). <br/>
     *
     * @param workQuantityReportFormBean
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    List<Map<String, Object>> queryWorkQuantityReport(WorkQuantityReportFormBean workQuantityReportFormBean);

    void deleteMappOrderSchedule(Long id) throws BizException;

    /**
     * selectCustomerOrderSchedule:我的收入 订单统计
     *
     * @return
     * @author chenshichao
     * @since JDK 1.8
     */
    Map<?, ?> selectOrderScheduleStatistics(Integer staffId, String serviceEndTime);

    /**
     * selectOrderSchedulePresent:(获取当前时间的排班信息). <br/>
     *
     * @return
     * @author csc
     * @since JDK 1.8
     */
    CustomerOrderSchedule selectOrderSchedulePresent(String orderNo);

    /**
     * updateStaffIdPresentById:(更改当前班次的护工). <br/>
     *
     * @return
     * @author csc
     * @since JDK 1.8
     */
    void updateStaffIdPresentById(Long id, Integer serviceStaffId) throws BizException;
}
