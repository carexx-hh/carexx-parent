package com.sh.carexx.uc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sh.carexx.bean.care.CareServiceRatioFormBean;
import com.sh.carexx.model.uc.CareServiceRatio;

public interface CareServiceRatioMapper {

	/**
	 * 
	 * selectById:(通过id查询). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	CareServiceRatio selectById(Integer id);

	/**
	 * 
	 * selectByServiceAddress:(通过服务地址查询). <br/>
	 * 
	 * @author zhoulei
	 * @param serviceAddress
	 * @return
	 * @since JDK 1.8
	 */
	CareServiceRatio selectByServiceAddress(byte serviceAddress);

	/**
	 * 
	 * selectServiceRatioCount:(分页统计服务比例). <br/>
	 * 
	 * @author zhoulei
	 * @param careServiceRatioFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectServiceRatioCount(CareServiceRatioFormBean careServiceRatioFormBean);

	/**
	 * 
	 * selectServiceRatioList:(分页查询服务比例). <br/>
	 * 
	 * @author zhoulei
	 * @param careServiceRatioFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectServiceRatioList(CareServiceRatioFormBean careServiceRatioFormBean);
	
	/**
	 * 
	 * selectServiceRatioAll:(查询全部可用的服务比例). <br/> 
	 * 
	 * @author zhoulei 
	 * @return 
	 * @since JDK 1.8
	 */
	List<CareServiceRatio> selectAllServiceRatio();

	/**
	 * 
	 * insert:(添加服务比例). <br/>
	 * 
	 * @author zhoulei
	 * @param careServiceRatio
	 * @return
	 * @since JDK 1.8
	 */
	int insert(CareServiceRatio careServiceRatio);

	/**
	 * 
	 * update:(修改服务比例). <br/> 
	 * 
	 * @author zhoulei 
	 * @param careServiceRatio
	 * @return 
	 * @since JDK 1.8
	 */
	int update(CareServiceRatio careServiceRatio);

	/**
	 * 
	 * updateServiceRatioStatus:(修改服务比例状态). <br/> 
	 * 
	 * @author zhoulei 
	 * @param id
	 * @param srcStatus
	 * @param targetStatus
	 * @return 
	 * @since JDK 1.8
	 */
	int updateServiceRatioStatus(@Param("id") Integer id, @Param("srcStatus") Byte srcStatus,
			@Param("targetStatus") Byte targetStatus);
}