package com.sh.carexx.mapp.controller;

import com.sh.carexx.bean.order.CalcServiceFeeFormBean;
import com.sh.carexx.bean.order.CustomerAppointOrderFormBean;
import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.bean.order.MappCustomerOrderQueryFormBean;
import com.sh.carexx.bean.order.OrderPaymentFormBean;
import com.sh.carexx.bean.user.UserAccountDetailFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.enums.pay.PayType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.mapp.wechat.WechatPayManager;
import com.sh.carexx.model.uc.OrderPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/customerorder")
public class CustomerOrderController extends BaseController {
	@Autowired
	private WechatPayManager wechatPayManager;

	@RequestMapping(value = "/list_order")
	public String queryForListByCustomerId(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		customerOrderQueryFormBean.setUserId(this.getCurrentUser().getId());
		return this.ucServiceClient.queryCustomerOrderListByUserId(customerOrderQueryFormBean);
	}

	@RequestMapping(value = "/done_order")
	public String queryForListByCustomerId() {
		Integer userId = this.getCurrentUser().getId();
		return this.ucServiceClient.queryDoneOrderByUserId(userId);
	}

	@RequestMapping(value="/detail/{orderNo}")
	public String queryOrderDetailByOrderNo(@PathVariable("orderNo") String orderNo){
		return this.ucServiceClient.queryOrderDetailByOrderNo(orderNo);
	}

	@RequestMapping(value = "/add_appointOrder")
	public BasicRetVal addAppointOrder(@Valid CustomerAppointOrderFormBean customerAppointOrderFormBean,
			BindingResult bindingResult) {
		if (customerAppointOrderFormBean.getuserId() == null || customerAppointOrderFormBean.getInpatientAreaId() == null) {
			customerAppointOrderFormBean.setuserId(this.getCurrentUser().getId().toString());
		}
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		return this.ucServiceClient.addCustomerAppointOrder(customerAppointOrderFormBean);
	}

	@RequestMapping(value = "/pay")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
	public BasicRetVal pay(@Valid OrderPaymentFormBean orderPaymentFormBean, BindingResult bindingResult) {
		this.fillFormBean(orderPaymentFormBean);
		if (bindingResult.hasErrors()) {
			return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
		}
		try {
			OrderPayment orderPayment = this.ucServiceClient.getOrderPayment(orderPaymentFormBean.getOrderNo());
			return new DataRetVal(CarexxConstant.RetCode.SUCCESS,
					this.wechatPayManager.getWechatPayInfo(orderPayment, orderPaymentFormBean));
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
	}

	@RequestMapping(value = "/account_pay")
	public BasicRetVal accountPay(@Valid UserAccountDetailFormBean userAccountDetailFormBean){
		this.ucServiceClient.modifyOrderServiceEndTime(userAccountDetailFormBean.getOrderNo());
		OrderPayment orderPayment = this.ucServiceClient.getOrderPayment(userAccountDetailFormBean.getOrderNo());
		userAccountDetailFormBean.setUserId(this.getCurrentUser().getId());
		userAccountDetailFormBean.setPayType(PayType.ORDERPAY.getValue());
		userAccountDetailFormBean.setPayAmt(orderPayment.getPayAmt().add(new BigDecimal(4)));
		return this.ucServiceClient.addUserAccountDetail(userAccountDetailFormBean);
	}

	@RequestMapping(value = "/cancel/{orderNo}")
	public BasicRetVal cancel(@PathVariable("orderNo") String orderNo) {
		return this.ucServiceClient.mappCancelCustomerOrder(orderNo);
	}

	@RequestMapping(value = "/calc_service_fee")
	public BasicRetVal calcServiceFee(@Valid CalcServiceFeeFormBean calcServiceFeeFormBean) {
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS,
				this.ucServiceClient.calcServiceFee(calcServiceFeeFormBean));
	}
	
	@RequestMapping(value = "/by_orderStatus/{orderStatus}")
	public String queryMappByOrderStatus(@PathVariable("orderStatus")String orderStatus) {
		Integer instId = this.getCurrentUserOAuth().getInstId();
		return this.ucServiceClient.queryMappByOrderStatus(orderStatus, instId);
	}
	
	@RequestMapping(value = "/by_orderStatus_and_serviceStatus")
	public String queryMappByOrderStatusAndServiceStatus(@Valid MappCustomerOrderQueryFormBean mappCustomerOrderQueryFormBean) {
		mappCustomerOrderQueryFormBean.setInstId(this.getCurrentUserOAuth().getInstId());
		return this.ucServiceClient.queryMappByOrderStatusAndServiceStatus(mappCustomerOrderQueryFormBean);
	}
	
	@RequestMapping(value = "/staff_by_orderStatus_and_serviceStatus")
	public String queryMappStaffByOrderStatusAndServiceStatus(@Valid MappCustomerOrderQueryFormBean mappCustomerOrderQueryFormBean) {
		mappCustomerOrderQueryFormBean.setInstId(this.getCurrentUserOAuth().getInstId());
		mappCustomerOrderQueryFormBean.setServiceStaffId(this.getCurrentUserOAuth().getStaffId());
		return this.ucServiceClient.queryMappByOrderStatusAndServiceStatus(mappCustomerOrderQueryFormBean);
	}

	@RequestMapping(value = "/do_orderSchedule")
	public String queryMappManagerDoOrderSchedule(@Valid MappCustomerOrderQueryFormBean mappCustomerOrderQueryFormBean) {
		mappCustomerOrderQueryFormBean.setInstId(this.getCurrentUserOAuth().getInstId());
		mappCustomerOrderQueryFormBean.setServiceStaffId(this.getCurrentUserOAuth().getStaffId());
		return this.ucServiceClient.queryMappManagerDoOrderSchedule(mappCustomerOrderQueryFormBean);
	}
	
	@RequestMapping(value="/staff_income_count")
	public String queryStaffIncomeCountForList(@Valid CustomerOrderQueryFormBean customerOrderQueryFormBean){
		customerOrderQueryFormBean.setServiceStaffId(this.getCurrentUserOAuth().getStaffId().toString());
		return this.ucServiceClient.queryStaffIncomeCountForList(customerOrderQueryFormBean);
	}
}
