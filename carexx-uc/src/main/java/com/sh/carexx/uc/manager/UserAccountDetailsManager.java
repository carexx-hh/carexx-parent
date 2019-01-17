package com.sh.carexx.uc.manager;

import com.sh.carexx.bean.user.UserAccountDetailsFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.keygen.KeyGenerator;
import com.sh.carexx.model.uc.UserAccountDetails;
import com.sh.carexx.uc.service.UserAccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountDetailsManager {
    @Autowired
    private KeyGenerator keyGenerator;
    @Autowired
    private UserAccountDetailsService userAccountDetailsService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void add(UserAccountDetailsFormBean userAccountDetailsFormBean) throws BizException{
        UserAccountDetails userAccountDetails = new UserAccountDetails();
        userAccountDetails.setAccountId(userAccountDetailsFormBean.getAccountId());
        userAccountDetails.setOrderNo("111");
    }
}
