package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.user.UserAccountDetailsQueryFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.UserAccount;
import com.sh.carexx.model.uc.UserAccountDetails;
import com.sh.carexx.uc.dao.UserAccountDetailsMapper;
import com.sh.carexx.uc.service.UserAccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserAccountDetailsServiceImpl implements UserAccountDetailsService {
    @Autowired
    private UserAccountDetailsMapper userAccountDetailsMapper;

    @Override
    public UserAccountDetails getById(Long id) {
        return this.userAccountDetailsMapper.selectById(id);
    }

    @Override
    public List<UserAccount> getByAccountId(UserAccountDetailsQueryFormBean userAccountDetailsQueryFormBean) {
        return this.userAccountDetailsMapper.selectByAccountId(userAccountDetailsQueryFormBean);
    }

    @Override
    public UserAccount getByPayNo(String payNo) {
        return this.userAccountDetailsMapper.selectByPayNo(payNo);
    }

    @Override
    public BigDecimal getSumAmtByPayType(UserAccountDetailsQueryFormBean userAccountDetailsQueryFormBean) {
        return this.userAccountDetailsMapper.selectSumAmtByPayType(userAccountDetailsQueryFormBean);
    }

    @Override
    public void save(UserAccountDetails userAccountDetails) throws BizException {
        int rows = 0;
        try {
            rows = this.userAccountDetailsMapper.insert(userAccountDetails);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
        if (rows != 1) {
            throw new BizException(ErrorCode.DB_ERROR);
        }
    }
}
