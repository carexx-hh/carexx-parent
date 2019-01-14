package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.user.UserAccountDetailsQueryFormBean;
import com.sh.carexx.model.uc.UserAccount;
import com.sh.carexx.model.uc.UserAccountDetails;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * ClassName: 用户账户交易记录 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface UserAccountDetailsMapper {

    /**
     *
     * selectById:(通过id查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccountDetails selectById(Long id);

    /**
     *
     * selectByAccountId:(通过账户id查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    List<UserAccount> selectByAccountId(UserAccountDetailsQueryFormBean userAccountDetailsQueryFormBean);

    /**
     *
     * selectByPayNo:(通过交易号查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccount selectByPayNo(String payNo);

    /**
     *
     * selectSumAmtByPayType:(通过交易类型查询总金额). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    BigDecimal selectSumAmtByPayType(UserAccountDetailsQueryFormBean userAccountDetailsQueryFormBean);

    /**
     *
     * insert:(新增交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    int insert(UserAccountDetails userAccountDetails);
}