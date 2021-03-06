package com.sh.carexx.uc.service;

import java.util.List;
import java.util.Map;

import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.UserMsg;

/**
 * 
 * ClassName: UserMsgService <br/>
 * Function: 用户消息 <br/>
 * Date: 2018年5月2日 上午10:17:00 <br/>
 * 
 * @author zhoulei
 * @since JDK 1.8
 */
public interface UserMsgService {

	/**
	 * 
	 * save:(消息添加方法). <br/>
	 * 
	 * @author zhoulei
	 * @param userMsg
	 * @throws BizException
	 * @since JDK 1.8
	 */
	void save(UserMsg userMsg) throws BizException;
	
	/**
	 * 
	 * getById:(通过消息id查找对应数据). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @return
	 * @since JDK 1.8
	 */
	UserMsg getById(Long id);

	/**
	 * 
	 * queryAllUserMsg:(查询用户消息). <br/> 
	 * 
	 * @author zhoulei 
	 * @param UserId
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> queryAllUserMsg(Integer userId);
	
	/**
	 * 
	 * getForCountUnread:(统计未读消息). <br/> 
	 * 
	 * @author zhoulei 
	 * @param userId
	 * @return 
	 * @since JDK 1.8
	 */
	Integer getForCountUnread(Integer userId);

}
