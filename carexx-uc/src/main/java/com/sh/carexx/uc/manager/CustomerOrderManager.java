package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.order.*;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.TimeUnit;
import com.sh.carexx.common.enums.UseStatus;
import com.sh.carexx.common.enums.order.*;
import com.sh.carexx.common.enums.pay.PayMethod;
import com.sh.carexx.common.enums.pay.PayStatus;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.keygen.KeyGenerator;
import com.sh.carexx.common.util.DateUtils;
import com.sh.carexx.common.util.ValidUtils;
import com.sh.carexx.model.uc.*;
import com.sh.carexx.uc.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CustomerOrderManager <br/>
 * Function: 客户订单 <br/>
 * Date: 2018年5月29日 下午5:29:57 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
@Service
public class CustomerOrderManager {
    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private CustomerOrderService customerOrderService;
    @Autowired
    private OrderPaymentManager orderPaymentManager;
    @Autowired
    private InstCareServiceService instCareServiceService;
    @Autowired
    private UserService userService;
    @Autowired
    private InstCustomerService instCustomerService;
    @Autowired
    private CustomerOrderScheduleService customerOrderScheduleService;
    @Autowired
    private InstHolidayService instHolidayService;
    @Autowired
    private OrderSettleService orderSettleService;
    @Autowired
    private InstSettleService instSettleService;
    @Autowired
    private OrderPaymentService orderPaymentService;
    @Autowired
    private CustomerOrderTimeService customerOrderTimeService;

    private Logger log = Logger.getLogger(CustomerOrderManager.class);

    /**
     * calcServiceFee:(计算订单金额). <br/>
     *
     * @param instId：机构id
     * @param serviceId：服务id
     * @param serviceStartTime：服务开始时间
     * @param serviceEndTime：服务结束时间
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public BigDecimal calcServiceFee(Integer instId, Integer serviceId, Date serviceStartTime, Date serviceEndTime) {
        // 订单总时长
        int hour = DateUtils.getHourDiff(serviceStartTime, serviceEndTime);
        // 节假日时长
        int holidayHour = 0;
        int checkHour = 0;
        Date holidayStartTime = serviceStartTime;
        // 检查订单中是否存在节假日，存在则累计
        while (checkHour < hour) {
            InstHoliday instHoliday = this.instHolidayService.getByScheduleDate(instId, holidayStartTime);
            if (instHoliday != null) {
                holidayHour += 12;
            }
            holidayStartTime = DateUtils.addHour(holidayStartTime, 12);
            checkHour += 12;
        }
        InstCareService instCareService = instCareServiceService.queryServicePrice(instId, serviceId);
        // 正常服务金额
        BigDecimal normalServiceFeeAmt = BigDecimal.ZERO;
        // 节假日服务金额
        BigDecimal holidayServiceFeeAmt = BigDecimal.ZERO;
        if (TimeUnit.DAY.getValue() == instCareService.getServiceUnit()) {
            // 如果节假日小时数大于零，则将节假日部分价格翻倍
            if (holidayHour > 0) {
                holidayServiceFeeAmt = instCareService.getServicePrice().multiply(
                        new BigDecimal(holidayHour * 2).divide(new BigDecimal(24), 2, BigDecimal.ROUND_HALF_UP));
            }
            // 正常时间部分计算
            if (hour - holidayHour > 0) {
                normalServiceFeeAmt = instCareService.getServicePrice().multiply(
                        new BigDecimal(hour - holidayHour).divide(new BigDecimal(24), 2, BigDecimal.ROUND_HALF_UP));
            }
        } else if (TimeUnit.MONTH.getValue() == instCareService.getServiceUnit()) {
            return instCareService.getServicePrice();
        }
        // 返回时节假日服务金额加上正常服务金额
        return normalServiceFeeAmt.add(holidayServiceFeeAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * holidayCount:(统计订单中节假日天数). <br/>
     *
     * @param instId:机构id
     * @param serviceStartTime:服务开始时间
     * @param serviceEndTime:服务结束时间
     * @return
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public BigDecimal holidayCount(Integer instId, Date serviceStartTime, Date serviceEndTime) {
        // 订单总时长
        int hour = DateUtils.getHourDiff(serviceStartTime, serviceEndTime);
        // 节假日天数
        BigDecimal holiday = new BigDecimal(0);
        int checkHour = 0;
        Date holidayStartTime = serviceStartTime;
        // 检查存在节假日，存在则累计0.5天并往后推12小时
        while (checkHour < hour) {
            InstHoliday instHoliday = this.instHolidayService.getByScheduleDate(instId, holidayStartTime);
            if (instHoliday != null) {
                holiday = holiday.add(new BigDecimal(0.5));
            }
            holidayStartTime = DateUtils.addHour(holidayStartTime, 12);
            checkHour += 12;
        }
        return holiday;
    }

    /**
     * add:(客户端代客下单). <br/>
     *
     * @param customerOrderFormBean
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void add(CustomerOrderFormBean customerOrderFormBean) throws BizException {
        Date serviceStartTime = null;
        Date serviceEndTime = null;
        if (ValidUtils.isDateTime(customerOrderFormBean.getServiceStartTime())) {
            serviceStartTime = DateUtils.toDate(customerOrderFormBean.getServiceStartTime(),
                    DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        if (ValidUtils.isDateTime(customerOrderFormBean.getServiceEndTime())) {
            serviceEndTime = DateUtils.toDate(customerOrderFormBean.getServiceEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        // 检查结束时间必须大于开始时间
        if (!serviceStartTime.before(serviceEndTime)) {
            throw new BizException(ErrorCode.TIME_END_BEFORE_START_ERROR);
        }
        // 检查下单时间是否已关账
        Date da = this.instSettleService.queryMaxSettleDateBySettleStatus(customerOrderFormBean.getInstId());
        if (da != null) {
            if (!da.before(serviceStartTime)) {
                throw new BizException(ErrorCode.ORDER_SETTLE_EXISTS_ERROR);
            }
        }
        // 检查客户下单是否重复
        CustomerOrderQueryFormBean customerOrderQueryFormBean = new CustomerOrderQueryFormBean();
        customerOrderQueryFormBean.setInstId(customerOrderFormBean.getInstId());
        customerOrderQueryFormBean.setRealName(customerOrderFormBean.getPatientName());
        customerOrderQueryFormBean.setServiceId(customerOrderFormBean.getServiceId().toString());
        customerOrderQueryFormBean.setServiceStartTime(customerOrderFormBean.getServiceStartTime());
        customerOrderQueryFormBean.setServiceEndTime(customerOrderFormBean.getServiceEndTime());
        List<Map<?, ?>> orderlist = this.customerOrderService.queryOrderExistence(customerOrderQueryFormBean);
        if (orderlist.size() > 0) {
            throw new BizException(ErrorCode.CUSTOMER_ORDER_EXISTS_ERROR);
        }

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderType(OrderType.UNDERLINE_ORDER.getValue());
        customerOrder.setServiceAddress(ServiceAddress.INST.getValue());
        customerOrder.setInstId(customerOrderFormBean.getInstId());
        customerOrder.setCustomerId(customerOrderFormBean.getCustomerId());
        InstCustomer instCustomer = this.instCustomerService.getById(customerOrderFormBean.getCustomerId());
        customerOrder.setPhone(instCustomer.getPhone());
        customerOrder.setServiceId(customerOrderFormBean.getServiceId());
        String orderNo = this.keyGenerator.generateOrderNo();
        customerOrder.setOrderNo(orderNo);
        customerOrder.setInpatientAreaId(customerOrderFormBean.getInpatientAreaId());
        customerOrder.setAccurateAddress(customerOrderFormBean.getAccurateAddress());
        customerOrder.setServiceStartTime(serviceStartTime);
        customerOrder.setServiceEndTime(serviceEndTime);
        customerOrder.setOrderAmt(this.calcServiceFee(customerOrder.getInstId(), customerOrder.getServiceId(),
                customerOrder.getServiceStartTime(), customerOrder.getServiceEndTime()));
        customerOrder.setHoliday(this.holidayCount(customerOrder.getInstId(), customerOrder.getServiceStartTime(),
                customerOrder.getServiceEndTime()));
        customerOrder.setAdjustAmt(new BigDecimal(0));
        customerOrder.setOperatorId(customerOrderFormBean.getOperatorId());
        customerOrder.setOrderRemark(customerOrderFormBean.getOrderRemark());
        customerOrder.setOrderStatus(OrderStatus.WAIT_SCHEDULE.getValue());
        // 添加订单
        this.customerOrderService.save(customerOrder);
        // 添加一条订单支付信息
        this.orderPaymentManager.add(customerOrder);
    }

    /**
     * addCommunity:(添加社区订单). <br/>
     *
     * @param customerOrderFormBean
     * @throws BizException
     * @author zhoulei
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void addCommunity(CustomerOrderFormBean customerOrderFormBean) throws BizException {
        Date serviceStartTime = null;
        Date serviceEndTime = null;
        if (ValidUtils.isDateTime(customerOrderFormBean.getServiceStartTime())) {
            serviceStartTime = DateUtils.toDate(customerOrderFormBean.getServiceStartTime(),
                    DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        if (ValidUtils.isDateTime(customerOrderFormBean.getServiceEndTime())) {
            serviceEndTime = DateUtils.toDate(customerOrderFormBean.getServiceEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        // 检查结束时间必须大于开始时间
        if (!serviceStartTime.before(serviceEndTime)) {
            throw new BizException(ErrorCode.TIME_END_BEFORE_START_ERROR);
        }
        // 检查下单时间是否已关账
        Date da = this.instSettleService.queryMaxSettleDateBySettleStatus(customerOrderFormBean.getInstId());
        if (da != null) {
            if (!da.before(serviceStartTime)) {
                throw new BizException(ErrorCode.ORDER_SETTLE_EXISTS_ERROR);
            }
        }
        // 检查客户下单是否重复
        CustomerOrderQueryFormBean customerOrderQueryFormBean = new CustomerOrderQueryFormBean();
        customerOrderQueryFormBean.setInstId(customerOrderFormBean.getInstId());
        customerOrderQueryFormBean.setRealName(customerOrderFormBean.getPatientName());
        customerOrderQueryFormBean.setServiceId(customerOrderFormBean.getServiceId().toString());
        customerOrderQueryFormBean.setServiceStartTime(customerOrderFormBean.getServiceStartTime());
        customerOrderQueryFormBean.setServiceEndTime(customerOrderFormBean.getServiceEndTime());
        List<Map<?, ?>> orderlist = this.customerOrderService.queryOrderExistence(customerOrderQueryFormBean);
        if (orderlist.size() > 0) {
            throw new BizException(ErrorCode.CUSTOMER_ORDER_EXISTS_ERROR);
        }

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderType(OrderType.UNDERLINE_ORDER.getValue());
        customerOrder.setServiceAddress(ServiceAddress.COMMUNITY.getValue());
        customerOrder.setInstId(customerOrderFormBean.getInstId());
        customerOrder.setCustomerId(customerOrderFormBean.getCustomerId());
        customerOrder.setPhone(customerOrderFormBean.getPhone());
        customerOrder.setServiceId(customerOrderFormBean.getServiceId());
        String orderNo = this.keyGenerator.generateOrderNo();
        customerOrder.setOrderNo(orderNo);
        customerOrder.setAccurateAddress(customerOrderFormBean.getAccurateAddress());
        customerOrder.setServiceStartTime(serviceStartTime);
        customerOrder.setServiceEndTime(serviceEndTime);
        customerOrder.setOrderAmt(this.calcServiceFee(customerOrder.getInstId(), customerOrder.getServiceId(),
                customerOrder.getServiceStartTime(), customerOrder.getServiceEndTime()));
        customerOrder.setHoliday(this.holidayCount(customerOrder.getInstId(), customerOrder.getServiceStartTime(),
                customerOrder.getServiceEndTime()));
        customerOrder.setAdjustAmt(new BigDecimal(0));
        customerOrder.setOperatorId(customerOrderFormBean.getOperatorId());
        customerOrder.setOrderRemark(customerOrderFormBean.getOrderRemark());
        customerOrder.setOrderStatus(OrderStatus.WAIT_SCHEDULE.getValue());
        // 添加订单
        this.customerOrderService.save(customerOrder);
        // 添加一条订单支付信息
        this.orderPaymentManager.add(customerOrder);
    }

    /**
     * addAppointOrder:(移动端下单). <br/>
     *
     * @param customerAppointOrderFormBean
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void addAppointOrder(CustomerAppointOrderFormBean customerAppointOrderFormBean) throws BizException {
        Date serviceStartTime = null;
        if (ValidUtils.isDateTime(customerAppointOrderFormBean.getServiceStartTime())) {
            serviceStartTime = DateUtils.toDate(customerAppointOrderFormBean.getServiceStartTime(),
                    DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        Date date = new Date();
        int subHour = DateUtils.getHourDiff(serviceStartTime, date);
        if (subHour >= 12) {
            throw new BizException(ErrorCode.ORDER_START_TIME_ERROR);
        }

        Date serviceEndTime = DateUtils.addHour(serviceStartTime, 12);
        // 检查下单时间是否已关账
        Date da = this.instSettleService.queryMaxSettleDateBySettleStatus(customerAppointOrderFormBean.getInstId());
        if (da != null) {
            if (!da.before(serviceStartTime)) {
                throw new BizException(ErrorCode.ORDER_SETTLE_EXISTS_ERROR);
            }
        }
        // 客户下单同时添加一条客户信息
        Integer customerId = 0;
        UserInfo userInfo = this.userService.getById(customerAppointOrderFormBean.getUserId());
        InstCustomer customer = new InstCustomer();
        customer.setInstId(customerAppointOrderFormBean.getInstId());
        customer.setUserId(customerAppointOrderFormBean.getUserId());
        customer.setRealName(customerAppointOrderFormBean.getPatientName());
        customer.setPhone(customerAppointOrderFormBean.getPhone());
        InstCustomer instCustomer = this.instCustomerService.queryCustomerExistence(customer);
        if (instCustomer == null) {
            instCustomer = new InstCustomer();
            instCustomer.setInstId(customerAppointOrderFormBean.getInstId());
            instCustomer.setUserId(customerAppointOrderFormBean.getUserId());
            instCustomer.setRealName(customerAppointOrderFormBean.getPatientName());
            instCustomer.setPhone(customerAppointOrderFormBean.getPhone());
            instCustomer.setSex(userInfo.getSex());
            instCustomer.setAddress(userInfo.getRegion());
            instCustomer.setCustomerStatus(UseStatus.ENABLED.getValue());
            this.instCustomerService.save(instCustomer);
            customerId = instCustomer.getId();
        } else {
            customerId = instCustomer.getId();
        }

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderType(OrderType.ONLINE_ORDER.getValue());
        customerOrder.setServiceAddress(ServiceAddress.INST.getValue());
        customerOrder.setInstId(customerAppointOrderFormBean.getInstId());
        customerOrder.setUserId(customerAppointOrderFormBean.getUserId());
        customerOrder.setCustomerId(customerId);
        customerOrder.setPhone(customerAppointOrderFormBean.getPhone());
        customerOrder.setServiceId(customerAppointOrderFormBean.getServiceId());
        String orderNo = this.keyGenerator.generateOrderNo();
        customerOrder.setOrderNo(orderNo);
        customerOrder.setInpatientAreaId(customerAppointOrderFormBean.getInpatientAreaId());
        customerOrder.setAccurateAddress(customerAppointOrderFormBean.getAccurateAddress());
        customerOrder.setServiceStartTime(serviceStartTime);
        customerOrder.setOrderAmt(this.calcServiceFee(customerOrder.getInstId(), customerOrder.getServiceId(),
                customerOrder.getServiceStartTime(), serviceEndTime));
        customerOrder.setHoliday(
                this.holidayCount(customerOrder.getInstId(), customerOrder.getServiceStartTime(), serviceEndTime));
        customerOrder.setAdjustAmt(new BigDecimal(0));
        customerOrder.setOrderStatus(OrderStatus.WAIT_SCHEDULE.getValue());
        customerOrder.setOrderRemark(customerAppointOrderFormBean.getOrderRemark());
        // 添加订单
        this.customerOrderService.save(customerOrder);
        // 添加一条订单支付信息
        this.orderPaymentManager.add(customerOrder);
    }

    /**
     * addCommunityOrder:(社区端下单). <br/>
     *
     * @param customerAppointOrderFormBean
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void addCommunityOrder(CustomerAppointOrderFormBean customerAppointOrderFormBean) throws BizException {
        Date serviceStartTime = null;
        Date serviceEndTime = null;
        if (ValidUtils.isDateTime(customerAppointOrderFormBean.getServiceStartTime())) {
            serviceStartTime = DateUtils.toDate(customerAppointOrderFormBean.getServiceStartTime(),
                    DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        if (ValidUtils.isDateTime(customerAppointOrderFormBean.getServiceEndTime())) {
            serviceEndTime = DateUtils.toDate(customerAppointOrderFormBean.getServiceEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        // 检查结束时间必须大于开始时间
        if (!serviceStartTime.before(serviceEndTime)) {
            throw new BizException(ErrorCode.TIME_END_BEFORE_START_ERROR);
        }

        // 检查下单时间是否已关账
        Date da = this.instSettleService.queryMaxSettleDateBySettleStatus(customerAppointOrderFormBean.getInstId());
        if (da != null) {
            if (!da.before(serviceStartTime)) {
                throw new BizException(ErrorCode.ORDER_SETTLE_EXISTS_ERROR);
            }
        }
        // 客户下单同时添加一条客户信息
        Integer customerId = 0;
        UserInfo userInfo = this.userService.getById(customerAppointOrderFormBean.getUserId());
        InstCustomer customer = new InstCustomer();
        customer.setInstId(customerAppointOrderFormBean.getInstId());
        customer.setUserId(customerAppointOrderFormBean.getUserId());
        customer.setRealName(customerAppointOrderFormBean.getPatientName());
        customer.setPhone(customerAppointOrderFormBean.getPhone());
        InstCustomer instCustomer = this.instCustomerService.queryCustomerExistence(customer);
        if (instCustomer == null) {
            instCustomer = new InstCustomer();
            instCustomer.setInstId(customerAppointOrderFormBean.getInstId());
            instCustomer.setUserId(customerAppointOrderFormBean.getUserId());
            instCustomer.setRealName(customerAppointOrderFormBean.getPatientName());
            instCustomer.setPhone(customerAppointOrderFormBean.getPhone());
            instCustomer.setSex(customerAppointOrderFormBean.getSex());
            instCustomer.setAddress(userInfo.getRegion());
            instCustomer.setCustomerStatus(UseStatus.ENABLED.getValue());
            this.instCustomerService.save(instCustomer);
            customerId = instCustomer.getId();
        } else {
            customerId = instCustomer.getId();
        }

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderType(OrderType.ONLINE_ORDER.getValue());
        customerOrder.setServiceAddress(ServiceAddress.COMMUNITY.getValue());
        customerOrder.setInstId(customerAppointOrderFormBean.getInstId());
        customerOrder.setUserId(customerAppointOrderFormBean.getUserId());
        customerOrder.setCustomerId(customerId);
        customerOrder.setPhone(customerAppointOrderFormBean.getPhone());
        customerOrder.setServiceId(customerAppointOrderFormBean.getServiceId());
        String orderNo = this.keyGenerator.generateOrderNo();
        customerOrder.setOrderNo(orderNo);
        customerOrder.setAccurateAddress(customerAppointOrderFormBean.getAccurateAddress());
        customerOrder.setServiceStartTime(serviceStartTime);
        customerOrder.setServiceEndTime(serviceEndTime);
        customerOrder.setOrderAmt(this.calcServiceFee(customerOrder.getInstId(), customerOrder.getServiceId(),
                serviceStartTime, serviceEndTime));
        customerOrder.setHoliday(this.holidayCount(customerOrder.getInstId(), serviceStartTime, serviceEndTime));
        customerOrder.setAdjustAmt(new BigDecimal(0));
        customerOrder.setOrderStatus(OrderStatus.WAIT_SCHEDULE.getValue());
        customerOrder.setOrderRemark(customerAppointOrderFormBean.getOrderRemark());
        // 添加订单
        this.customerOrderService.save(customerOrder);
        // 添加一条订单支付信息
        this.orderPaymentManager.add(customerOrder);
    }

    /**
     * updateOrderAmtAndHoliday:(推订单金额和节假日天数). <br/>
     *
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void modifyOrderAmtAndHoliday(int instId) throws BizException {
//		List<CustomerOrder> list = this.customerOrderService.getAllOrder();
        List<CustomerOrder> list = this.customerOrderService.getOrderByInstId(instId);
        if (list == null) {
            return;
        }
        for (CustomerOrder order : list) {
//            CustomerOrderSchedule customerOrderSchedule = this.customerOrderScheduleService
//                    .getNearByOrderNo(order.getOrderNo());
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setOrderNo(order.getOrderNo());
            CustomerOrderTime customerOrderTime = this.customerOrderTimeService.getDayJobByInstId(instId);//当前机构白班时间
            String today = DateUtils.toString(new Date(), DateUtils.YYYY_MM_DD);//当前日期
            Date nowTime = DateUtils.toDate(DateUtils.toString(new Date(), DateUtils.HH_MM_SS), DateUtils.HH_MM_SS);//当前时间
            Date afterTime = new Date(nowTime.getTime() - 60000);//推前一分钟时间
            Date startTime = customerOrderTime.getStartTime();//早班时间
            Date endTime = customerOrderTime.getEndTime();//晚班时间
            Date serviceEndTime = null;
            if (afterTime.getTime() <= startTime.getTime()) {
                serviceEndTime = DateUtils.toDate((today + " " + DateUtils.toString(endTime, DateUtils.HH_MM_SS)), DateUtils.YYYY_MM_DD_HH_MM_SS);
            } else {
                Date time = DateUtils.toDate((today + " " + DateUtils.toString(endTime, DateUtils.HH_MM_SS)), DateUtils.YYYY_MM_DD_HH_MM_SS);
                serviceEndTime = DateUtils.addHour(time, 12);
            }
            customerOrder.setOrderAmt(this.calcServiceFee(order.getInstId(), order.getServiceId(),
                    order.getServiceStartTime(), serviceEndTime));
            customerOrder.setHoliday(this.holidayCount(order.getInstId(), order.getServiceStartTime(),
                    serviceEndTime));
            this.customerOrderService.updateOrderAmtAndHoliday(customerOrder);
            this.orderPaymentManager.modifyPayAmt(customerOrder);
        }
    }

    /**
     * updateServiceEndTime:(修改订单结束时间). <br/>
     *
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void modifyServiceEndTime(String orderNo) throws BizException {
        CustomerOrder customerOrder = this.customerOrderService.getByOrderNo(orderNo);
        CustomerOrderTime customerOrderTime = this.customerOrderTimeService.getDayJobByInstId(customerOrder.getInstId());//当前机构白班时间
        String today = DateUtils.toString(new Date(), DateUtils.YYYY_MM_DD);//当前日期
        Date nowTime = DateUtils.toDate(DateUtils.toString(new Date(), DateUtils.HH_MM_SS), DateUtils.HH_MM_SS);//当前时间
        Date startTime = customerOrderTime.getStartTime();//早班时间
        Date endTime = customerOrderTime.getEndTime();//晚班时间
        Date serviceEndTime = null;
        if (nowTime.getTime() <= startTime.getTime()) { //如果当前时间小于早班开始时间
            serviceEndTime = DateUtils.toDate((today + " " + DateUtils.toString(startTime, DateUtils.HH_MM_SS)), DateUtils.YYYY_MM_DD_HH_MM_SS);
        } else if(nowTime.getTime() >= endTime.getTime()){ //如果当前时间大于晚班开始时间
            Date time = DateUtils.toDate((today + " " + DateUtils.toString(endTime, DateUtils.HH_MM_SS)), DateUtils.YYYY_MM_DD_HH_MM_SS);
            serviceEndTime = DateUtils.addHour(time, 12);
        }else{
            serviceEndTime = DateUtils.toDate((today + " " + DateUtils.toString(endTime, DateUtils.HH_MM_SS)), DateUtils.YYYY_MM_DD_HH_MM_SS);
        }
        customerOrder.setServiceEndTime(serviceEndTime);
        this.customerOrderService.updateServiceEndTime(customerOrder);
        this.noWantSchedule(orderNo, serviceEndTime);
    }

    /**
     * noWantSchedule:(删除多余排班和结算记录). <br/>
     *
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void noWantSchedule(String orderNo, Date serviceEndTime) throws BizException {
        CustomerOrderSchedule customerOrderSchedule = new CustomerOrderSchedule();
        customerOrderSchedule.setOrderNo(orderNo);
        customerOrderSchedule.setServiceStatus(OrderScheduleStatus.WAIT_ACCEPT.getValue());
        List<CustomerOrderSchedule> customerOrderScheduleList = this.customerOrderScheduleService.queryNoWantSchedule(customerOrderSchedule);
        if(customerOrderScheduleList.size() > 0){
            this.customerOrderScheduleService.updateServiceStatus(customerOrderScheduleList);
        }
        customerOrderSchedule = new CustomerOrderSchedule();
        customerOrderSchedule.setOrderNo(orderNo);
        customerOrderSchedule.setServiceEndTime(serviceEndTime);
        customerOrderScheduleList = this.customerOrderScheduleService.queryNoWantSchedule(customerOrderSchedule);
        if(customerOrderScheduleList.size() > 0){
            this.customerOrderScheduleService.deleteNoWantSchedule(customerOrderScheduleList);
            this.orderSettleService.deleteNoWantSettle(customerOrderScheduleList);
        }
        customerOrderSchedule = this.customerOrderScheduleService.getNearByOrderNo(orderNo);
        if (customerOrderSchedule.getServiceEndTime().getTime() > serviceEndTime.getTime()) {
            customerOrderSchedule.setServiceEndTime(serviceEndTime);
            this.customerOrderScheduleService.updateServiceEndTime(customerOrderSchedule);
            OrderSettle orderSettle = this.orderSettleService.getByScheduleId(customerOrderSchedule.getId());
            orderSettle.setStaffSettleAmt((orderSettle.getStaffSettleAmt().divide(new BigDecimal(2))).setScale(2, BigDecimal.ROUND_HALF_UP));
            orderSettle.setInstSettleAmt((orderSettle.getInstSettleAmt().divide(new BigDecimal(2))).setScale(2, BigDecimal.ROUND_HALF_UP));
            this.orderSettleService.updateSettleAmt(orderSettle);
        }
    }

    /**
     * modifyServiceTime:(修改订单结束时间). <br/>
     *
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void modifyServiceTime(String orderNo, Date serviceStartTime, Date serviceEndTime) throws BizException {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderNo(orderNo);
        customerOrder.setServiceStartTime(serviceStartTime);
        customerOrder.setServiceEndTime(serviceEndTime);
        this.customerOrderService.updateServiceTime(customerOrder);
    }

    /**
     * cancel:(取消订单). <br/>
     *
     * @param orderNo：订单号
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void cancel(String orderNo) throws BizException {
        this.customerOrderService.updateOrderCancel(orderNo, OrderStatus.CANCELED.getValue());
        // 通过订单号查询该订单所有的排班
        List<CustomerOrderSchedule> orderschedulelist = this.customerOrderScheduleService.getByOrderNo(orderNo);
        for (CustomerOrderSchedule list : orderschedulelist) {
            this.customerOrderScheduleService.deleteOrderSchedule(list.getId(), OrderScheduleStatus.DELETED.getValue());
            // 取消订单下的排班同时取消所有的订单结算
            this.orderSettleService.updateStatus(list.getId(), OrderSettleStatus.SETTLING.getValue(),
                    OrderSettleStatus.CANCELED.getValue());
        }
    }

    /**
     * delete:(删除订单). <br/>
     *
     * @param orderNo
     * @throws BizException
     * @author zhoulei
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void delete(String orderNo) throws BizException {
        this.customerOrderService.updateOrderDelete(orderNo, OrderStatus.CANCELED.getValue());
        // 通过订单号查询该订单所有的排班
        List<CustomerOrderSchedule> orderschedulelist = this.customerOrderScheduleService.getByOrderNo(orderNo);
        for (CustomerOrderSchedule list : orderschedulelist) {
            OrderSettle orderSettle = this.orderSettleService.getByScheduleId(list.getId());
            if (orderSettle.getSettleStatus() == OrderSettleStatus.CLOSED.getValue()) {
                throw new BizException(ErrorCode.ORDER_SETTLE_EXISTS_ERROR);
            }
            this.customerOrderScheduleService.deleteOrderSchedule(list.getId(), OrderScheduleStatus.DELETED.getValue());
            // 取消订单下的排班同时取消所有的订单结算
            this.orderSettleService.updateSettleDelete(list.getId(), OrderSettleStatus.CANCELED.getValue());
        }
        this.orderPaymentService.updatePaymentDelete(orderNo, PayStatus.DELETE_PAY.getValue());
    }

    /**
     * mappCancel:(移动端取消订单). <br/>
     *
     * @param orderNo：订单号
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    public void mappCancel(String orderNo) throws BizException {
        this.customerOrderService.mappOrderCancel(orderNo, OrderStatus.CANCELED.getValue());
    }

    /**
     * throughPay:(订单支付). <br/>
     *
     * @param orderNo
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void throughPay(String orderNo, Byte payType) throws BizException {
        // 通过订单号查询出所有排班并将排班结算信息改为已结算
        List<CustomerOrderSchedule> orderScheduleList = this.customerOrderScheduleService.getByOrderNo(orderNo);
        for (CustomerOrderSchedule orderSchedule : orderScheduleList) {
            this.orderSettleService.updateStatus(orderSchedule.getId(), OrderSettleStatus.SETTLING.getValue(),
                    OrderSettleStatus.SETTLED.getValue());
        }
        this.orderPaymentManager.offlinePayment(orderNo, payType);
        // 订单状态改为已支付
        this.customerOrderService.updateStatus(orderNo, OrderStatus.WAIT_PAY.getValue(),
                OrderStatus.ALREADY_PAY.getValue());
    }

    /**
     * confirmCompleted:(确认完成订单). <br/>
     *
     * @param confirmCompletedOrderFormBean
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void confirmCompleted(ConfirmCompletedOrderFormBean confirmCompletedOrderFormBean) throws BizException {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderNo(confirmCompletedOrderFormBean.getOrderNo());
        customerOrder.setInstSysId(confirmCompletedOrderFormBean.getInstSysId());
        Byte proofType = confirmCompletedOrderFormBean.getProofType();
        customerOrder.setProofType(proofType);
        if (proofType == 1) {
            customerOrder.setReceiptNo(confirmCompletedOrderFormBean.getProofNo());
        } else if (proofType == 2) {
            customerOrder.setInvoiceNo(confirmCompletedOrderFormBean.getProofNo());
        }
        customerOrder.setSigningPerson(confirmCompletedOrderFormBean.getSigningPerson());
        // 完成订单并添加完成信息(签单人、凭证号等)
        this.customerOrderService.confirmCompleted(customerOrder);
        // 将订单状态由已支付改为已完成
        this.customerOrderService.updateStatus(confirmCompletedOrderFormBean.getOrderNo(),
                OrderStatus.ALREADY_PAY.getValue(), OrderStatus.COMPLETED.getValue());
    }

    /**
     * adjustAmt:(调整订单). <br/>
     *
     * @param customerOrderAdjustFormBean
     * @throws BizException
     * @author zhoulei
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void adjust(CustomerOrderAdjustFormBean customerOrderAdjustFormBean) throws BizException {
        BigDecimal adjustAmt = new BigDecimal(customerOrderAdjustFormBean.getAdjustAmt());
        // 校验调整金额是否合法
        CustomerOrder customerOrder = this.customerOrderService.getByOrderNo(customerOrderAdjustFormBean.getOrderNo());
        if (customerOrder.getOrderAmt().add(adjustAmt).compareTo(BigDecimal.ZERO) < 1) {
            throw new BizException(ErrorCode.ADJUST_AMT_GRERTER_ORDER_AMT_ERROR);
        }

        customerOrder.setAdjustAmt(adjustAmt);
        customerOrder.setProofType(customerOrderAdjustFormBean.getProofType());
        if (customerOrderAdjustFormBean.getProofType() == ProofType.RECEIPT.getValue()) {
            customerOrder.setReceiptNo(customerOrderAdjustFormBean.getProofNo());
            customerOrder.setInvoiceNo("");
        } else if (customerOrderAdjustFormBean.getProofType() == ProofType.INVOICE.getValue()) {
            customerOrder.setInvoiceNo(customerOrderAdjustFormBean.getProofNo());
            customerOrder.setReceiptNo("");
        }
        customerOrder.setOrderRemark(customerOrderAdjustFormBean.getOrderRemark());
        this.customerOrderService.update(customerOrder);

        if (customerOrderAdjustFormBean.getOrderType() == OrderType.ONLINE_ORDER.getValue()) {
            throw new BizException(ErrorCode.ONLINE_ORDER_NOT_MODIFY);
        }
        if (customerOrderAdjustFormBean.getPayType() == PayMethod.ONLINE_PAY.getValue()) {
            throw new BizException(ErrorCode.PAYMETHOD_NOT_ONLINE_PAY);
        }
        this.orderPaymentService.updatePayType(customerOrderAdjustFormBean.getOrderNo(),
                customerOrderAdjustFormBean.getPayType());
    }
}
