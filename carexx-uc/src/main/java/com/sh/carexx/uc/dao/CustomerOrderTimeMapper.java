package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.order.CustomerOrderTimeQueryFormBean;
import com.sh.carexx.model.uc.CustomerOrderTime;
import org.apache.ibatis.annotations.Param;

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
public interface CustomerOrderTimeMapper {

    /**
     *
     * selectById:(通过id查询班次). <br/>
     *
     * @author hetao
     * @param id
     * @return
     * @since JDK 1.8
     */
    CustomerOrderTime selectById(Long id);

    /**
     *
     * selectByInstId:(通过机构id查询班次). <br/>
     *
     * @author hetao
     * @param instId
     * @return
     * @since JDK 1.8
     */
    List<CustomerOrderTime> selectByInstId(@Param("instId") Integer instId);

    /**
     *
     * selectJobTypeExistence:(检查机构班次存在). <br/>
     *
     * @author hetao
     * @param instId,jobType
     * @return
     * @since JDK 1.8
     */
    CustomerOrderTime selectJobTypeExistence(@Param("instId") Integer instId, @Param("jobType") Byte jobType);

    /**
     *
     * selectCustomerOrderTimeCount:(分页统计查询). <br/>
     *
     * @author hetao
     * @param customerOrderTimeQueryFormBean
     * @return
     * @since JDK 1.8
     */
    Integer selectCustomerOrderTimeCount(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    /**
     *
     * selectCustomerOrderTimeList:(分页查询). <br/>
     *
     * @author hetao
     * @param customerOrderTimeQueryFormBean
     * @return
     * @since JDK 1.8
     */
    List<Map<?,?>> selectCustomerOrderTimeList(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean);

    /**
     *
     * insert:(添加机构班次). <br/>
     *
     * @author hetao
     * @param customerOrderTime
     * @return
     * @since JDK 1.8
     */
    int insert(CustomerOrderTime customerOrderTime);

    /**
     *
     * update:(修改机构班次). <br/>
     *
     * @author hetao
     * @param customerOrderTime
     * @return
     * @since JDK 1.8
     */
    int update(CustomerOrderTime customerOrderTime);
}