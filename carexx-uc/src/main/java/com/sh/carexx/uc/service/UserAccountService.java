package com.sh.carexx.uc.service;

import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.UserAccount;

/**
 *
 * ClassName: 用户个人账户 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface UserAccountService {

    /**
     *
     * getById:(通过id查询账户信息). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccount getById(Integer id);

    /**
     *
     * getByUserId:(通过用户id查询账户信息). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccount getByUserId(Integer userId);

    /**
     *
     * save:(添加方法). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    void save(UserAccount userAccount) throws BizException;

    /**
     *
     * updateBalance:(修改账户金额). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    void updateBalance(UserAccount userAccount) throws BizException;
}
