package com.sh.carexx.uc.controller;

import com.sh.carexx.bean.user.UserAccountDetailFormBean;
import com.sh.carexx.bean.user.UserAccountDetailQueryFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.model.uc.UserAccountDetails;
import com.sh.carexx.uc.manager.UserAccountDetailManager;
import com.sh.carexx.uc.service.UserAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accountdetail")
public class UserAccountDetailController {
    @Autowired
    private UserAccountDetailService userAccountDetailService;

    @Autowired
    private UserAccountDetailManager userAccountDetailManager;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicRetVal add(@RequestBody UserAccountDetailFormBean userAccountDetailFormBean) {
        try {
            this.userAccountDetailManager.add(userAccountDetailFormBean);
        } catch (BizException e) {
            return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
        }
        return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String queryUserAccountDetail(@RequestBody UserAccountDetailQueryFormBean userAccountDetailQueryFormBean) {
        List<UserAccountDetails> result = this.userAccountDetailService.selectByUserId(userAccountDetailQueryFormBean);
        return new DataRetVal(CarexxConstant.RetCode.SUCCESS,result).toJSON();
    }
}