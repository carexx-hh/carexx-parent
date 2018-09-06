package com.sh.carexx.uc.dao;

import org.apache.ibatis.annotations.Param;

import com.sh.carexx.model.uc.UserMsgStatus;

/**
 * 
 * ClassName: UserMsgStatusMapper <br/> 
 * Function: 消息阅读状态 <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * Date: 2018年5月2日 上午10:53:27 <br/> 
 * 
 * @author zhoulei 
 * @since JDK 1.8
 */
public interface UserMsgStatusMapper {

	/**
	 * 
	 * insert:(添加消息阅读状态方法). <br/> 
	 * 
	 * @author zhoulei 
	 * @param userMsgStatus
	 * @return 
	 * @since JDK 1.8
	 */
	int insert(UserMsgStatus userMsgStatus);
	
	/**
	 * 
	 * selectByMsgId:(通过消息id查找对应数据). <br/> 
	 * 
	 * @author zhoulei 
	 * @param msgId
	 * @return 
	 * @since JDK 1.8
	 */
	UserMsgStatus selectByMsgId(Long msgId);

	/**
	 * 
	 * updateStatus:(修改消息状态). <br/> 
	 * 
	 * @author zhoulei 
	 * @param msgId
	 * @param userId
	 * @param srcStatus
	 * @param targetStatus
	 * @return 
	 * @since JDK 1.8
	 */
	int updateStatus(@Param("msgId") Long msgId, @Param("userId") Integer userId, @Param("srcStatus") Byte srcStatus,
			@Param("targetStatus") Byte targetStatus);
	
	/**
	 * 
	 * delete:(删除消息阅读状态方法). <br/> 
	 * 
	 * @author zhoulei 
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 */
	int delete(Long id);
}