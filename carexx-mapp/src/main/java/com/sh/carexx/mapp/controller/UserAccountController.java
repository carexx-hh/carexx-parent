package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/useraccount")
public class UserAccountController extends BaseController {
    @RequestMapping(value = "/by_userId")
    public String queryByUserId() {
        Integer userId = this.getCurrentUser().getId();
        return this.ucServiceClient.queryUserAccountByUserId(userId);
    }
}
