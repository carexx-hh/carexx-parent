package com.sh.carexx.uc.service;

import java.util.List;
import java.util.Map;

import com.sh.carexx.bean.care.CareServiceRatioFormBean;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.CareServiceRatio;

/**
 * 
 * ClassName: CareServiceRatioService <br/>
 * Function: 服务比例 <br/>
 * Date: 2018年11月6日 下午3:20:14 <br/>
 * 
 * @author zhoulei
 * @since JDK 1.8
 */
public interface CareServiceRatioService {

	/**
	 * 
	 * getById:(通过id查找). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	CareServiceRatio getById(Integer id);

	/**
	 * 
	 * getByServiceAddress:(通过服务地址查询). <br/>
	 * 
	 * @author zhoulei
	 * @param serviceAddress
	 * @return
	 * @since JDK 1.8
	 */
	CareServiceRatio getByServiceAddress(byte serviceAddress);

	/**
	 * 
	 * getServiceRatioCount:(分页统计服务比例). <br/>
	 * 
	 * @author zhoulei
	 * @param careServiceRatioFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer getServiceRatioCount(CareServiceRatioFormBean careServiceRatioFormBean);

	/**
	 * 
	 * queryServiceRatioList:(分页统计服务比例). <br/>
	 * 
	 * @author zhoulei
	 * @param careServiceRatioFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> queryServiceRatioList(CareServiceRatioFormBean careServiceRatioFormBean);
	
	/**
	 * 
	 * queryServiceRatioAll:(查询全部可用的服务比例). <br/> 
	 * 
	 * @author zhoulei 
	 * @return 
	 * @since JDK 1.8
	 */
	List<CareServiceRatio> queryAllServiceRatio();

	/**
	 * 
	 * save:(添加服务比例). <br/>
	 * 
	 * @author hetao
	 * @param careService
	 * @since JDK 1.8
	 */
	void save(CareServiceRatio careServiceRatio) throws BizException;

	/**
	 * 
	 * update:(修改服务比例). <br/>
	 * 
	 * @author zhoulei
	 * @param careServiceRatio
	 * @throws BizException
	 * @since JDK 1.8
	 */
	void update(CareServiceRatio careServiceRatio) throws BizException;

	/**
	 * 
	 * updateServiceRatioStatus:(修改服务比例状态). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @param srcStatus
	 * @param targetStatus
	 * @throws BizException
	 * @since JDK 1.8
	 */
	void updateServiceRatioStatus(Integer id, Byte srcStatus, Byte targetStatus) throws BizException;
}
