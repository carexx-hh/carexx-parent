package com.sh.carexx.uc.dao;

import com.sh.carexx.model.uc.UserAccount;

/**
 *
 * ClassName: 用户个人账户 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface UserAccountMapper {

    /**
     *
     * selectById:(通过id查询账户信息). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccount selectById(Integer id);

    /**
     *
     * selectByUserId:(通过用户id查询账户信息). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccount selectByUserId(Integer userId);

    /**
     *
     * insert:(添加方法). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    int insert(UserAccount userAccount);

    /**
     *
     * updateBalance:(修改账户金额). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    int updateBalance(UserAccount userAccount);


}