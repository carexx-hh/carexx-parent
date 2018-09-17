package com.sh.carexx.uc.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sh.carexx.bean.usermsg.UserMsgFormBean;
import com.sh.carexx.common.enums.MsgStatus;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.UserMsg;
import com.sh.carexx.model.uc.UserMsgStatus;
import com.sh.carexx.uc.service.UserMsgService;
import com.sh.carexx.uc.service.UserMsgStatusService;

/**
 * 
 * ClassName: UserMsgManager <br/>
 * Function: 消息通知 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * Date: 2018年7月12日 下午1:59:12 <br/>
 * 
 * @author zhoulei
 * @since JDK 1.8
 */
@Service
public class UserMsgManager {

	@Autowired
	public UserMsgService userMsgService;

	@Autowired
	public UserMsgStatusService userMsgStatusService;

	/**
	 * 
	 * add:(添加消息). <br/>
	 * 
	 * @author zhoulei
	 * @param userMsgFormBean
	 * @throws BizException
	 * @since JDK 1.8
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public void add(UserMsgFormBean userMsgFormBean) throws BizException {
		UserMsg userMsg = new UserMsg();
		Byte msgType = 1;
		userMsg.setMsgType(msgType);
		userMsg.setUserId(userMsgFormBean.getUserId());
		userMsg.setMsgTitle(userMsgFormBean.getMsgTitle());
		userMsg.setMsgContent(userMsgFormBean.getMsgContent());
		userMsg.setOrderNo(userMsgFormBean.getOrderNo());
		this.userMsgService.save(userMsg);

		UserMsgStatus userMsgStatus = new UserMsgStatus();
		userMsgStatus.setUserId(userMsg.getUserId());
		userMsgStatus.setMsgId(userMsg.getId());
		userMsgStatus.setMsgStatus(MsgStatus.UNREAD.getValue());
		this.userMsgStatusService.save(userMsgStatus);
	}

	/**
	 * 
	 * addMsgStatus:(读消息). <br/>
	 * 
	 * @author zhoulei
	 * @param userId
	 * @param msgId
	 * @throws BizException
	 * @since JDK 1.8
	 */
	public void addMsgStatus(Integer userId, Long msgId) throws BizException {
		UserMsgStatus userMsgStatus = this.userMsgStatusService.getByMsgId(msgId);
		if (userMsgStatus != null) {
			return;
		}
		userMsgStatus = new UserMsgStatus();
		userMsgStatus.setUserId(userId);
		userMsgStatus.setMsgId(msgId);
		userMsgStatus.setMsgStatus(MsgStatus.READ.getValue());
		this.userMsgStatusService.save(userMsgStatus);
	}

	public void read(Long msgId, Integer userId) throws BizException {
		this.userMsgStatusService.updateStatus(msgId, userId, MsgStatus.UNREAD.getValue(), MsgStatus.READ.getValue());
	}

	/**
	 * 
	 * delete:(删除消息). <br/>
	 * 
	 * @author zhoulei
	 * @param id
	 * @throws BizException
	 * @since JDK 1.8
	 */
	public void delete(String ids) throws BizException {
		String[] strArr = ids.split(",");
		long[] longArr = new long[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			longArr[i] = Long.valueOf(strArr[i]);
		}
		for (int i = 0; i < longArr.length; i++) {
			this.userMsgStatusService.delete(longArr[i]);
		}
	}

}
