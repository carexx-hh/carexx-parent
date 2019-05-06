package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.user.UserAccountDetailFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.pay.PayMethod;
import com.sh.carexx.common.enums.pay.PayStatus;
import com.sh.carexx.common.enums.pay.PayType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.keygen.KeyGenerator;
import com.sh.carexx.model.uc.OrderPayment;
import com.sh.carexx.model.uc.UserAccount;
import com.sh.carexx.model.uc.UserAccountDetails;
import com.sh.carexx.uc.service.UserAccountDetailService;
import com.sh.carexx.uc.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: UserAccountDetailManager <br/>
 * Function: 个人账户 <br/>
 * Date: 2019年3月15日 下午5:29:57 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
@Service
public class UserAccountDetailManager {
    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private UserAccountDetailService userAccountDetailService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private OrderPaymentManager orderPaymentManager;
    @Autowired
    private CustomerOrderScheduleManager customerOrderScheduleManager;

    /**
     * add:(支付信息添加). <br/>
     *
     * @param userAccountDetailFormBean
     * @throws BizException
     * @author hetao
     * @since JDK 1.8
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void add(UserAccountDetailFormBean userAccountDetailFormBean) throws BizException{
        UserAccount userAccount = this.userAccountService.getByUserId(userAccountDetailFormBean.getUserId());//获取当前账户
        BigDecimal balance = userAccount.getAccountBalance();//账户余额
        BigDecimal payAmt = userAccountDetailFormBean.getPayAmt();//本次交易金额
        UserAccountDetails userAccountDetails = new UserAccountDetails();//创建对象接收传过来的数据
        userAccountDetails.setUserId(userAccountDetailFormBean.getUserId());
        userAccountDetails.setPayNo(userAccountDetailFormBean.getPayNo());
        userAccountDetails.setOrderNo(userAccountDetailFormBean.getOrderNo());
        userAccountDetails.setPayType(userAccountDetailFormBean.getPayType());
        if(userAccountDetailFormBean.getPayType() == PayType.RECHARGE.getValue()){ //账户充值
            balance = balance.add(payAmt);
        }else if(userAccountDetailFormBean.getPayType() == PayType.ReFund.getValue()){ //账户提现
            if(payAmt.compareTo(balance) == 1){
                throw new BizException(ErrorCode.USER_ACCOUNT_BALANCE_NOT_ENOUGH);
            }
            BigDecimal poundage = (payAmt.multiply(new BigDecimal("0.002"))).setScale(2,BigDecimal.ROUND_HALF_UP);
            if(poundage.compareTo(new BigDecimal("0.02")) == -1){
                poundage = new BigDecimal("0.02");
            }
            userAccountDetails.setPayPoundage(poundage);
            balance = balance.subtract(payAmt.add(poundage));
        }else if(userAccountDetailFormBean.getPayType() == PayType.ORDERPAY.getValue()){ //订单支付
            if(payAmt.compareTo(balance) == 1){
                throw new BizException(ErrorCode.USER_ACCOUNT_BALANCE_NOT_ENOUGH);
            }
            balance = balance.subtract(payAmt);
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setPayType(PayMethod.ONLINE_PAY.getValue());
            orderPayment.setOrderNo(userAccountDetailFormBean.getOrderNo());
            orderPayment.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
            this.orderPaymentManager.syncPayResult(orderPayment);
            this.customerOrderScheduleManager.finishScheduling(userAccountDetailFormBean.getOrderNo());
            userAccountDetailFormBean.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
            userAccountDetailFormBean.setPayTime(new Date());
            userAccountDetails.setPayNo(this.keyGenerator.generatePayNo());
        }
        userAccountDetails.setPayAmt(payAmt);
        userAccountDetails.setPayAmtAfter(balance);
        userAccountDetails.setPayChnl(userAccountDetailFormBean.getPayChnl());
        userAccountDetails.setPayChnlTransNo(userAccountDetailFormBean.getPayChnlTransNo());
        userAccountDetails.setPayStatus(userAccountDetailFormBean.getPayStatus());
        userAccountDetails.setPayTime(userAccountDetailFormBean.getPayTime());
        this.userAccountDetailService.save(userAccountDetails);//添加账户记录

        userAccount.setAccountBalance(balance);
        this.userAccountService.updateBalance(userAccount);//修改账户余额
    }
}
