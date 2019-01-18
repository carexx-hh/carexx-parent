package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.user.UserAccountDetailQueryFormBean;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.UserAccount;
import com.sh.carexx.model.uc.UserAccountDetails;
import com.sh.carexx.uc.dao.UserAccountDetailMapper;
import com.sh.carexx.uc.service.UserAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserAccountDetailServiceImpl implements UserAccountDetailService {
    @Autowired
    private UserAccountDetailMapper userAccountDetailMapper;

    @Override
    public UserAccountDetails getById(Long id) {
        return this.userAccountDetailMapper.selectById(id);
    }

    @Override
    public List<UserAccount> getByAccountId(UserAccountDetailQueryFormBean userAccountDetailQueryFormBean) {
        return this.userAccountDetailMapper.selectByAccountId(userAccountDetailQueryFormBean);
    }

    @Override
    public UserAccount getByPayNo(String payNo) {
        return this.userAccountDetailMapper.selectByPayNo(payNo);
    }

    @Override
    public BigDecimal getSumAmtByPayType(UserAccountDetailQueryFormBean userAccountDetailQueryFormBean) {
        return this.userAccountDetailMapper.selectSumAmtByPayType(userAccountDetailQueryFormBean);
    }

    @Override
    public void save(UserAccountDetails userAccountDetails) throws BizException {
        int rows = 0;
        try {
            rows = this.userAccountDetailMapper.insert(userAccountDetails);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
        if (rows != 1) {
            throw new BizException(ErrorCode.DB_ERROR);
        }
    }
}
