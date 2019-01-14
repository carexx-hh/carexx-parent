package com.sh.carexx.uc.service.impl;

import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.UserAccount;
import com.sh.carexx.uc.dao.UserAccountMapper;
import com.sh.carexx.uc.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public UserAccount getById(Integer id) {
        return this.userAccountMapper.selectById(id);
    }

    @Override
    public UserAccount getByUserId(Integer userId) {
        return this.userAccountMapper.selectByUserId(userId);
    }

    @Override
    public void save(UserAccount userAccount) throws BizException {
        int rows = 0;
        try {
            rows = this.userAccountMapper.insert(userAccount);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
        if (rows != 1) {
            throw new BizException(ErrorCode.DB_ERROR);
        }
    }

    @Override
    public void updateBalance(UserAccount userAccount) throws BizException {
        int rows = 0;
        try {
            rows = this.userAccountMapper.updateBalance(userAccount);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
        if (rows != 1) {
            throw new BizException(ErrorCode.DB_ERROR);
        }
    }
}
