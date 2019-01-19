package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.user.UserAccountDetailFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.enums.pay.PayChnl;
import com.sh.carexx.common.enums.pay.PayMethod;
import com.sh.carexx.common.enums.pay.PayType;
import com.sh.carexx.common.util.JAXBUtils;
import com.sh.carexx.common.util.WebUtils;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.mapp.wechat.WechatManager;
import com.sh.carexx.mapp.wechat.WechatPayManager;
import com.sh.carexx.mapp.wechat.bean.WepayNotifyReq;
import com.sh.carexx.mapp.wechat.bean.WepayNotifyRsp;
import com.sh.carexx.model.uc.OrderPayment;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/callback")
public class CallbackController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WechatManager wechatManager;

	@Autowired
	private WechatPayManager wechatPayManager;

	@RequestMapping(value = "/wechat_push_msg")
	public String processMsg(HttpServletRequest request) {
		String msgSignature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String result = null;
		try {
			String postData = new String(WebUtils.getRequestPostBody(request), "utf-8");
			result = this.wechatManager.processMsg(msgSignature, timestamp, nonce, postData);
		} catch (Exception e) {
			this.logger.error("处理微信推送消息失败", e);
		}
		return StringUtils.isBlank(result) ? "success" : result;
		// return request.getParameter("echostr");
	}

	@RequestMapping(value = "/wechat_pay_notify")
	public String wepayNotify(HttpServletRequest request) {
		WepayNotifyRsp rsp = new WepayNotifyRsp();
		WepayNotifyReq req = null;
		try {
			String notifyBody = new String(WebUtils.getRequestPostBody(request), WechatPayManager.CHARSET);
			this.logger.info("收到微信支付回调通知，参数：[{}]", notifyBody);
			req = JAXBUtils.convert2JavaBean(notifyBody, WepayNotifyReq.class);
		} catch (Exception e) {
			rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
			rsp.setReturn_msg("微信支付回调通知请求异常");
			try {
				return JAXBUtils.convert2Xml(rsp, false, WechatPayManager.CHARSET);
			} catch (Exception e1) {
				return "";
			}
		}
		try {
			if (WechatPayManager.TRADE_SUCCESS.equals(req.getReturn_code())) {
				this.ucServiceClient.modifyOrderServiceEndTime(req.getOut_trade_no());
				OrderPayment orderPayment = new OrderPayment();
				orderPayment.setPayType(PayMethod.ONLINE_PAY.getValue());
				orderPayment.setPayChnl(PayChnl.WECHATPAY.getValue());
				orderPayment.setOrderNo(req.getOut_trade_no());
				orderPayment.setPayChnlTransNo(req.getTransaction_id());
				orderPayment.setPayStatus(this.wechatPayManager.translateTradeStatus(req.getResult_code()));
				BasicRetVal retVal = this.ucServiceClient.syncPayResult(orderPayment);
				if (retVal != null && CarexxConstant.RetCode.SUCCESS == retVal.getCode()) {
					rsp.setReturn_code(WechatPayManager.TRADE_SUCCESS);
					rsp.setReturn_msg("OK");
				} else {
					rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
					rsp.setReturn_msg("微信支付回调通知处理失败");
					this.logger.error("微信支付回调通知处理失败");
				}
			} else {
				rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
				rsp.setReturn_msg("微信支付回调通知异常");
			}
		} catch (Exception e) {
			rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
			rsp.setReturn_msg("微信支付回调通知处理失败");
			this.logger.error("微信支付回调通知处理失败", e);
		}
		try {
			return JAXBUtils.convert2Xml(rsp, false, WechatPayManager.CHARSET);
		} catch (Exception e) {
			return "";
		}
	}

	@RequestMapping(value = "/recharge_notify")
	public String rechargeNotify(HttpServletRequest request) {
		WepayNotifyRsp rsp = new WepayNotifyRsp();
		WepayNotifyReq req = null;
		try {
			String notifyBody = new String(WebUtils.getRequestPostBody(request), WechatPayManager.CHARSET);
			this.logger.info("收到微信支付回调通知，参数：[{}]", notifyBody);
			req = JAXBUtils.convert2JavaBean(notifyBody, WepayNotifyReq.class);
		} catch (Exception e) {
			rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
			rsp.setReturn_msg("微信支付回调通知请求异常");
			try {
				return JAXBUtils.convert2Xml(rsp, false, WechatPayManager.CHARSET);
			} catch (Exception e1) {
				return "";
			}
		}
		try {
			if (WechatPayManager.TRADE_SUCCESS.equals(req.getReturn_code())) {
				UserAccountDetailFormBean userAccountDetailFormBean = new UserAccountDetailFormBean();
				userAccountDetailFormBean.setPayNo(req.getOut_trade_no());
				BigDecimal payAmt = new BigDecimal(req.getTotal_fee()).multiply(new BigDecimal(100));
				userAccountDetailFormBean.setPayAmt(payAmt);
				userAccountDetailFormBean.setPayType(PayType.RECHARGE.getValue());
				userAccountDetailFormBean.setPayChnl(PayChnl.WECHATPAY.getValue());
				userAccountDetailFormBean.setPayChnlTransNo(req.getTransaction_id());
				userAccountDetailFormBean.setPayStatus(this.wechatPayManager.translateTradeStatus(req.getResult_code()));
				userAccountDetailFormBean.setPayTime(new Date());
				BasicRetVal retVal = this.ucServiceClient.addUserAccountDetail(userAccountDetailFormBean);
				if (retVal != null && CarexxConstant.RetCode.SUCCESS == retVal.getCode()) {
					rsp.setReturn_code(WechatPayManager.TRADE_SUCCESS);
					rsp.setReturn_msg("OK");
				} else {
					rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
					rsp.setReturn_msg("微信支付回调通知处理失败");
					this.logger.error("微信支付回调通知处理失败");
				}
			} else {
				rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
				rsp.setReturn_msg("微信支付回调通知异常");
			}
		} catch (Exception e) {
			rsp.setReturn_code(WechatPayManager.TRADE_FAIL);
			rsp.setReturn_msg("微信支付回调通知处理失败");
			this.logger.error("微信支付回调通知处理失败", e);
		}
		try {
			return JAXBUtils.convert2Xml(rsp, false, WechatPayManager.CHARSET);
		} catch (Exception e) {
			return "";
		}
	}
}
