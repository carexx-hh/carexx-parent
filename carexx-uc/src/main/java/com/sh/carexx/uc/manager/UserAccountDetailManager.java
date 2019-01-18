package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.user.UserAccountDetailFormBean;
import com.sh.carexx.common.ErrorCode;
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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void add(UserAccountDetailFormBean userAccountDetailFormBean) throws BizException{
        UserAccount userAccount = this.userAccountService.getByUserId(userAccountDetailFormBean.getUserId());
        BigDecimal balance = userAccount.getAccountBalance();
        BigDecimal payAmt = userAccountDetailFormBean.getPayAmt();
        UserAccountDetails userAccountDetails = new UserAccountDetails();
        userAccountDetails.setUserId(userAccountDetailFormBean.getUserId());
        userAccountDetails.setPayNo(this.keyGenerator.generatePayNo());
        userAccountDetails.setOrderNo(userAccountDetailFormBean.getOrderNo());
        userAccountDetails.setPayType(userAccountDetailFormBean.getPayType());
        if(userAccountDetailFormBean.getPayType() == PayType.RECHARGE.getValue()){
            balance = balance.add(payAmt);
        }else if(userAccountDetailFormBean.getPayType() == PayType.DRAWMONEY.getValue()){
            if(payAmt.compareTo(balance) == 1){
                throw new BizException(ErrorCode.USER_ACCOUNT_BALANCE_NOT_ENOUGH);
            }
            balance = balance.subtract(payAmt);
        }else if(userAccountDetailFormBean.getPayType() == PayType.ORDERPAY.getValue()){
            if(payAmt.compareTo(balance) == 1){
                throw new BizException(ErrorCode.USER_ACCOUNT_BALANCE_NOT_ENOUGH);
            }
            balance = balance.subtract(payAmt);
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrderNo(userAccountDetailFormBean.getOrderNo());
            orderPayment.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
            this.orderPaymentManager.syncPayResult(orderPayment);
            userAccountDetailFormBean.setPayStatus(PayStatus.PAY_SUCCESS.getValue());
            userAccountDetailFormBean.setPayTime(new Date());
        }
        userAccountDetails.setPayAmt(payAmt);
        userAccountDetails.setPayAmtAfter(balance);
        userAccountDetails.setPayChnl(userAccountDetailFormBean.getPayChnl());
        userAccountDetails.setPayChnlTransNo(userAccountDetailFormBean.getPayChnlTransNo());
        userAccountDetails.setPayStatus(userAccountDetailFormBean.getPayStatus());
        userAccountDetails.setPayTime(userAccountDetailFormBean.getPayTime());
        this.userAccountDetailService.save(userAccountDetails);

        userAccount.setAccountBalance(balance);
        this.userAccountService.updateBalance(userAccount);
    }
}
