package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.user.OAuthLoginFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
    @RequestMapping("/login")
    public String login(@Valid OAuthLoginFormBean oAuthLoginFormBean, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT).toJSON();
        }
        return this.ucServiceClient.doOAuthLogin(oAuthLoginFormBean);
    }
}
