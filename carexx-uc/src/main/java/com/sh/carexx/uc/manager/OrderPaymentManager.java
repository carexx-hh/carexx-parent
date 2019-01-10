package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.usermsg.UserMsgFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.order.OrderSettleStatus;
import com.sh.carexx.common.enums.order.OrderStatus;
import com.sh.carexx.common.enums.pay.PayMethod;
import com.sh.carexx.common.enums.pay.PayStatus;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.*;
import com.sh.carexx.uc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: 订单支付 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * Date: 2018年7月12日 下午1:51:36 <br/>
 *
 * @author zhoulei
 * @since JDK 1.8
 */
@Service
public class OrderPaymentManager {

    @Autowired
    private OrderPaymentService orderPaymentService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private InstStaffService instStaffService;

    @Autowired
    private InstCustomerService instCustomerService;

    @Autowired
    private UserMsgManager userMsgManager;

    @Autowired
    private CustomerOrderScheduleService customerOrderScheduleService;

    @Autowired
    public OrderSettleService orderSettleService;

    /**
     * add:(支付信息添加). <br/>
     *
     * @param customerOrder
     * @throws BizException
     * @author zhoulei
     * @since JDK 1.8
     */
    public void add(CustomerOrder customerOrder) throws BizException {
        //支付信息校验订单号
        OrderPayment oldOrderPayment = this.orderPaymentService.getByOrderNo(customerOrder.getOrderNo());
        if (oldOrderPayment != null) {
            throw new BizException(ErrorCode.ORDER_PAYMENT_EXISTS_ERROR);
        }
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setOrderNo(customerOrder.getOrderNo());
        orderPayment.setPayAmt(customerOrder.getOrderAmt());
        orderPayment.setPayStatus(PayStatus.PENDING_PAY.getValue());

        this.orderPaymentService.save(orderPayment);

    }

    /**
     * syncPayResult:(同步支付结果). <br/>
     *
     * @param orderPayment
     * @throws BizException
     * @author zhoulei
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void syncPayResult(OrderPayment orderPayment) throws BizException {
        OrderPayment oldOrderPayment = this.orderPaymentService.getByOrderNo(orderPayment.getOrderNo());
        if (oldOrderPayment.getPayStatus() == PayStatus.PENDING_PAY.getValue()
                && orderPayment.getPayStatus() == PayStatus.PAY_SUCCESS.getValue()) {
            //订单表待支付变成已支付
            this.customerOrderService.updateStatus(orderPayment.getOrderNo(), OrderStatus.IN_SERVICE.getValue(),
                    OrderStatus.ALREADY_PAY.getValue());
            //支付完成消息通知管理员
            CustomerOrder customerOrder = this.customerOrderService.getByOrderNo(orderPayment.getOrderNo());
            InstCustomer instCustomer = this.instCustomerService.getById(customerOrder.getCustomerId());

            UserMsgFormBean userMsgFormBean = new UserMsgFormBean();
            userMsgFormBean.setUserId(customerOrder.getOperatorId());
            userMsgFormBean.setMsgTitle("客户"+instCustomer.getRealName()+"的订单("+orderPayment.getOrderNo()+")已完成");
            userMsgFormBean.setMsgContent("客户"+instCustomer.getRealName()+"的订单("+orderPayment.getOrderNo()+")已完成");
            userMsgFormBean.setOrderNo(orderPayment.getOrderNo());
            this.userMsgManager.add(userMsgFormBean);

            //结算表待支付变成已支付
            List<CustomerOrderSchedule> orderScheduleList = this.customerOrderScheduleService
                    .getByOrderNo(orderPayment.getOrderNo());
            for (CustomerOrderSchedule orderSchedule : orderScheduleList) {
                this.orderSettleService.updateStatus(orderSchedule.getId(), OrderSettleStatus.SETTLING.getValue(),
                        OrderSettleStatus.SETTLED.getValue());
            }
        }
        this.orderPaymentService.update(orderPayment);
    }

    /**
     * offlinePayment:(线下支付). <br/>
     *
     * @param orderNo
     * @throws BizException
     * @author zhoulei
     * @since JDK 1.8
     */
    public void offlinePayment(String orderNo, Byte payType) throws BizException {
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setOrderNo(orderNo);
        if(payType == 2){
            orderPayment.setPayType(PayMethod.SCAN_PAY.getValue());
        }else if(payType == 3){
            orderPayment.setPayType(PayMethod.COMPANY_TURN_ACCOUNT.getValue());
        }else if(payType == 4){
            orderPayment.setPayType(PayMethod.CASH_PAY.getValue());
        }
        orderPayment.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
        this.orderPaymentService.update(orderPayment);
    }

    /**
     * modifyPayAmt:(修改订单支付金额). <br/>
     *
     * @param customerOrder
     * @throws BizException
     * @author hetao
     */
    public void modifyPayAmt(CustomerOrder customerOrder) throws BizException {
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setOrderNo(customerOrder.getOrderNo());
        orderPayment.setPayAmt(customerOrder.getOrderAmt());
        this.orderPaymentService.updatePayAmt(orderPayment);
    }

}
