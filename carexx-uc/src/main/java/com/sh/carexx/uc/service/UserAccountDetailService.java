package com.sh.carexx.uc.service;

import com.sh.carexx.bean.user.UserAccountDetailQueryFormBean;
import com.sh.carexx.common.exception.BizException;
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
public interface UserAccountDetailService {

    /**
     *
     * getById:(通过id查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccountDetails getById(Long id);

    /**
     *
     * getByAccountId:(通过账户id查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    List<UserAccount> getByAccountId(UserAccountDetailQueryFormBean userAccountDetailQueryFormBean);

    /**
     *
     * getByPayNo:(通过交易号查询交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    UserAccount getByPayNo(String payNo);

    /**
     *
     * getSumAmtByPayType:(通过交易类型查询总金额). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    BigDecimal getSumAmtByPayType(UserAccountDetailQueryFormBean userAccountDetailQueryFormBean);

    /**
     *
     * save:(新增交易记录). <br/>
     *
     * @author hetao
     * @return
     * @since JDK 1.8
     */
    void save(UserAccountDetails userAccountDetails) throws BizException;
}
