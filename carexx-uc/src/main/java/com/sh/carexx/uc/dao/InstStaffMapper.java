package com.sh.carexx.uc.dao;

import com.sh.carexx.bean.staff.InstStaffQueryFormBean;
import com.sh.carexx.model.uc.InstStaff;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: InstStaffMapper <br/>
 * Function: 人员管理 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * Date: 2018年5月2日 上午11:40:35 <br/>
 * 
 * @author zhoulei
 * @since JDK 1.8
 */
public interface InstStaffMapper {

	/**
	 * 
	 * insert:(添加员工方法). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaff
	 * @return
	 * @since JDK 1.8
	 */
	int insert(InstStaff instStaff);

	/**
	 * 
	 * selectById:(通过id查询员工记录). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	InstStaff selectById(Integer id);
	
	/**
	 * 
	 * selectDetailById:(通过id查询详细信息). <br/> 
	 * 
	 * @author zhoulei 
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 */
	Map<?, ?> selectDetailById(Integer id);

	/**
	 * 
	 * SelectByIdNo:(通过身份证号码查询员工记录). <br/>
	 * 
	 * @author zhoulei
	 * @param idNo
	 * @return
	 * @since JDK 1.8
	 */
	InstStaff selectByIdNo(@Param("idNo") String idNo, @Param("instId") Integer instId);

	/**
	 * 
	 * selectByIdNoAndPhone:(通过身份证号和手机号码查询). <br/> 
	 * 
	 * @author zhoulei 
	 * @param idNo
	 * @param phone
	 * @return 
	 * @since JDK 1.8
	 */
	InstStaff selectByIdNoAndPhone(@Param("idNo") String idNo, @Param("phone") String phone);
	
	/**
	 * 
	 * selectInstStaffCountByServiceId:(通过服务id统计会该项技能的员工). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectInstStaffCountByServiceId(InstStaffQueryFormBean instStaffQueryFormBean);

	/**
	 * 
	 * selectInstStaffListByServiceId:(通过服务id查询会该项技能的员工). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectInstStaffListByServiceId(InstStaffQueryFormBean instStaffQueryFormBean);
	
	/**
	 * 
	 * selectInstStaffByCertificationStatus:(通过认证状态查询人员). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instId
	 * @param certificationStatus
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectInstStaffByCertificationStatus(@Param("instId")Integer instId, @Param("certificationStatus") Byte certificationStatus);

	/**
	 * 
	 * selectInstStaffServiceNumber:(服务人数查询统计). <br/> 
	 * 
	 * @author zhoulei 
	 * @param workTypeId
	 * @param serviceInstId
	 * @param currentTime
	 * @param realName
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectInstStaffServiceNumber(@Param("workTypeId")Integer workTypeId,@Param("serviceInstId")Integer serviceInstId,@Param("currentTime")Date currentTime,@Param("realName")String realName);

	/**
	 * 
	 * selectInstStaffCount:(员工信息分页统计). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectInstStaffCount(InstStaffQueryFormBean instStaffQueryFormBean);

	/**
	 * 
	 * selectInstStaffList:(员工信息分页查询). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaffQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> selectInstStaffList(InstStaffQueryFormBean instStaffQueryFormBean);
	
	/**
	 * 
	 * selectAllInstStaff:(查询所有员工信息). <br/> 
	 * 
	 * @author zhoulei 
	 * @param instStaffQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectAllInstStaff(InstStaffQueryFormBean instStaffQueryFormBean);

	/**
	 * 
	 * update:(修改员工方法). <br/>
	 * 
	 * @author zhoulei
	 * @param instStaff
	 * @return
	 * @since JDK 1.8
	 */
	int update(InstStaff instStaff);

	/**
	 * 
	 * delete:(删除员工（状态改为2）方法). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	int delete(Integer id);
	
	int updateCertificationStatus(@Param("id") Integer id, @Param("srcStatus") String srcStatus,
			@Param("targetStatus") Byte targetStatus);
	
	/**
	 * 
	 * updateMobileById:(手机绑定). <br/> 
	 * 
	 * @author zhoulei 
	 * @param id
	 * @param mobile
	 * @return 
	 * @since JDK 1.8
	 */
	int updateMobileById(@Param("id") Integer id, @Param("mobile") String mobile);
}