package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.user.UserAccountDetailQueryFormBean;
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
public interface UserAccountDetailMapper {

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
     * selectByUserId:(通过用户id查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    List<UserAccountDetails> selectByUserId(UserAccountDetailQueryFormBean userAccountDetailQueryFormBean);

    /**
     *
     * selectByPayNo:(通过交易号查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccountDetails selectByPayNo(String payNo);

    /**
     *
     * selectSumAmtByPayType:(通过交易类型查询总金额). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    BigDecimal selectSumAmtByPayType(UserAccountDetailQueryFormBean userAccountDetailQueryFormBean);

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