package com.sh.carexx.uc.service;

import com.sh.carexx.bean.order.CustomerOrderTimeQueryFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CustomerOrderTime;

import java.util.List;
import java.util.Map;

/**
 *
 * ClassName: CustomerOrderTimeMapper <br/>
 * Function: 机构班次时间 <br/>
 * Date: 2018年11月23日 上午11:09:12 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface CustomerOrderTimeService {

    /**
     *
     * getById:(通过id查询班次). <br/>
     *
     * @author hetao
     * @param id
     * @return
     * @since JDK 1.8
     */
    CustomerOrderTime getById (Long id);

    /**
     *
     * getByInstId:(通过机构id查询班次). <br/>
     *
     * @author hetao
     * @param instId
     * @return
     * @since JDK 1.8
     */
    List<CustomerOrderTime> getByInstId(Integer instId);

    /**
     *
     * queryJobTypeExistence:(检查机构班次存在). <br/>
     *
     * @author hetao
     * @param instId,jobType
     * @return
     * @since JDK 1.8
     */
    CustomerOrderTime queryJobTypeExistence(Integer instId,Byte jobType);

    /**
     *
     * getCustomerOrderTimeCount:(分页统计查询). <br/>
     *
     * @author hetao
     * @param customerOrderTimeQueryFormBean
     * @return
     * @since JDK 1.8
     */
    Integer getCustomerOrderTimeCount(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    /**
     *
     * queryCustomerOrderTimeList:(分页查询). <br/>
     *
     * @author hetao
     * @param customerOrderTimeQueryFormBean
     * @return
     * @since JDK 1.8
     */
    List<Map<?,?>> queryCustomerOrderTimeList(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    /**
     *
     * save:(添加机构班次). <br/>
     *
     * @author hetao
     * @param customerOrderTime
     * @return
     * @since JDK 1.8
     */
    void save(CustomerOrderTime customerOrderTime) throws BizException;

    /**
     *
     * update:(修改机构班次). <br/>
     *
     * @author hetao
     * @param customerOrderTime
     * @return
     * @since JDK 1.8
     */
    void update(CustomerOrderTime customerOrderTime) throws BizException;
}
