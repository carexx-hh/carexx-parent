package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.user.UserAccountDetailQueryFormBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accountdetail")
public class UserAccountDetailController extends BaseController{
    @RequestMapping(value = "/list")
    public String queryUserAccountDetail(UserAccountDetailQueryFormBean userAccountDetailQueryFormBean) {
        userAccountDetailQueryFormBean.setUserId(this.getCurrentUser().getId());
        return this.ucServiceClient.queryUserAccountDetail(userAccountDetailQueryFormBean);
    }
}
