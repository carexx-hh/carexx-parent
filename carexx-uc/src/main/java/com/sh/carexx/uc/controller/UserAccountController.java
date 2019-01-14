package com.sh.carexx.uc.controller;

import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.uc.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/useraccount")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/by_userId/{userId}", method = RequestMethod.GET)
    public String queryByUserId(@PathVariable("userId") Integer userId) {
        return new DataRetVal(CarexxConstant.RetCode.SUCCESS,this.userAccountService.getByUserId(userId)).toJSON();
    }
}
