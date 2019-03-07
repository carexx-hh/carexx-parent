package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.user.UserAccountDetailFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.keygen.KeyGenerator;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.mapp.wechat.WechatPayManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/useraccount")
public class UserAccountController extends BaseController {
    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private WechatPayManager wechatPayManager;

    @RequestMapping(value = "/by_userId")
    public String queryByUserId() {
        Integer userId = this.getCurrentUser().getId();
        return this.ucServiceClient.queryUserAccountByUserId(userId);
    }

    @RequestMapping(value = "/recharge")
    public BasicRetVal recharge(@Valid UserAccountDetailFormBean userAccountDetailFormBean, BindingResult bindingResult) {
        this.fillFormBean(userAccountDetailFormBean);
        if (bindingResult.hasErrors()) {
            return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
        }
        try {
            userAccountDetailFormBean.setPayNo(this.keyGenerator.generatePayNo());
            return new DataRetVal(CarexxConstant.RetCode.SUCCESS,
                    this.wechatPayManager.getRechargeInfo(userAccountDetailFormBean));
        } catch (BizException e) {
            return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
        }
    }

    @RequestMapping(value = "/refund")
    public BasicRetVal refund(@Valid UserAccountDetailFormBean userAccountDetailFormBean, BindingResult bindingResult) {
        this.fillFormBean(userAccountDetailFormBean);
        if (bindingResult.hasErrors()) {
            return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
        }
        try {
            userAccountDetailFormBean.setUserId(this.getCurrentUser().getId());
            userAccountDetailFormBean.setPayNo(this.keyGenerator.generatePayNo());
            return new DataRetVal(CarexxConstant.RetCode.SUCCESS,
                    this.wechatPayManager.getReFundInfo(userAccountDetailFormBean));
        } catch (BizException e) {
            return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
        }
    }
}
