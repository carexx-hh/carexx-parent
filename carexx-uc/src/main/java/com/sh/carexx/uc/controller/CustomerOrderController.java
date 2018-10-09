package com.sh.carexx.uc.controller;

import com.sh.carexx.bean.order.*;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.DateUtils;
import com.sh.carexx.common.util.ValidUtils;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.common.web.PagerBean;
import com.sh.carexx.model.uc.OrderPayment;
import com.sh.carexx.uc.manager.CustomerOrderManager;
import com.sh.carexx.uc.manager.OrderPaymentManager;
import com.sh.carexx.uc.service.CustomerOrderService;
import com.sh.carexx.uc.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customerorder")
public class CustomerOrderController {
	@Autowired
	private CustomerOrderManager customerOrderManager;

	@Autowired
	private OrderPaymentManager orderPaymentManager;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private OrderPaymentService orderPaymentService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal add(@RequestBody CustomerOrderFormBean customerOrderFormBean) {
		try {
			this.customerOrderManager.add(customerOrderFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/add_community", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal addCommunityCustomerOrder(@RequestBody CustomerOrderFormBean customerOrderFormBean) {
		try {
			this.customerOrderManager.addCommunity(customerOrderFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}
	
	@RequestMapping(value = "/add_appointOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal addAppointOrder(@RequestBody CustomerAppointOrderFormBean customerAppointOrderFormBean) {
		try {
			this.customerOrderManager.addAppointOrder(customerAppointOrderFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		Integer totalNum = this.customerOrderService.getCustomerOrderCount(customerOrderQueryFormBean);
		List<Map<?, ?>> result = null;
		if (totalNum > 0) {
			result = this.customerOrderService.queryCustomerOrderList(customerOrderQueryFormBean);
		}
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, new PagerBean(totalNum, result)).toJSON();
	}

	@RequestMapping(value = "/list_order", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryForListByUserId(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<?, ?>> result = this.customerOrderService.getByUserId(customerOrderQueryFormBean);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS,result).toJSON();
	}

	@RequestMapping(value = "/done_order/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryDoneOrderByUserId(@PathVariable("userId") Integer userId) {
		List<Map<?, ?>> result = this.customerOrderService.getDoneOrderByUserId(userId);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS,result).toJSON();
	}

	@RequestMapping(value = "/detail/{orderNo}", method = RequestMethod.GET)
	public String queryOrderDetail(@PathVariable("orderNo") String orderNo) {
		List<Map<?, ?>> result = this.customerOrderService.getOrderDetail(orderNo);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS,result).toJSON();
	}

	@RequestMapping(value = "/sync_pay_result", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal syncPayResult(@RequestBody OrderPayment orderPayment) {
		try {
			this.orderPaymentManager.syncPayResult(orderPayment);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/get_order_payment/{orderNo}", method = RequestMethod.GET)
	public OrderPayment getOrderPayment(@PathVariable("orderNo") String orderNo) {
		return this.orderPaymentService.getByOrderNo(orderNo);
	}

	@RequestMapping(value = "/cancel/{orderNo}", method = RequestMethod.GET)
	public BasicRetVal cancel(@PathVariable("orderNo") String orderNo) {
		try {
			this.customerOrderManager.cancel(orderNo);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/mapp_cancel/{orderNo}", method = RequestMethod.GET)
	public BasicRetVal mappCancel(@PathVariable("orderNo") String orderNo) {
		try {
			this.customerOrderManager.mappCancel(orderNo);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/throughPay/{orderNo}/{payType}", method = RequestMethod.GET)
	public BasicRetVal throughPay(@PathVariable("orderNo") String orderNo, @PathVariable("payType") Byte payType) {
		try {
			this.customerOrderManager.throughPay(orderNo, payType);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/confirmcompleted", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal confirmCompleted(@RequestBody ConfirmCompletedOrderFormBean confirmCompletedOrderFormBean) {
		try {
			this.customerOrderManager.confirmCompleted(confirmCompletedOrderFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}

	@RequestMapping(value = "/calc_service_fee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BigDecimal calcServiceFee(@RequestBody CalcServiceFeeFormBean calcServiceFeeFormBean) {
		Date serviceStartTime = null;
		Date serviceEndTime = null;
		if (ValidUtils.isDateTime(calcServiceFeeFormBean.getServiceStartTime())) {
			serviceStartTime = DateUtils.toDate(calcServiceFeeFormBean.getServiceStartTime(),
					DateUtils.YYYY_MM_DD_HH_MM_SS);
		}
		if (ValidUtils.isDateTime(calcServiceFeeFormBean.getServiceEndTime())) {
			serviceEndTime = DateUtils.toDate(calcServiceFeeFormBean.getServiceEndTime(),
					DateUtils.YYYY_MM_DD_HH_MM_SS);
		}
		return this.customerOrderManager.calcServiceFee(calcServiceFeeFormBean.getInstId(),
				calcServiceFeeFormBean.getServiceId(), serviceStartTime, serviceEndTime);
	}

	@RequestMapping(value = "/income_count", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryIncomeCountForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<String, Object>> result = this.customerOrderService.queryIncomeCount(customerOrderQueryFormBean);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, result).toJSON();
	}
	
	@RequestMapping(value = "/inst_income_count", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryInstIncomeCountForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<String, Object>> result = this.customerOrderService.queryInstIncomeCount(customerOrderQueryFormBean);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, result).toJSON();
	}
	
	@RequestMapping(value = "/staff_income_count", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String queryStaffIncomeCountForList(@RequestBody CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<String, Object>> result = this.customerOrderService.queryStaffIncomeCount(customerOrderQueryFormBean);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, result).toJSON();
	}

	@RequestMapping(value = "/adjust", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BasicRetVal adjust(@RequestBody CustomerOrderAdjustFormBean customerOrderAdjustFormBean) {
		try {
			this.customerOrderManager.adjust(customerOrderAdjustFormBean);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}
	
	@RequestMapping(value = "/by_order_status/{orderStatus}/{instId}", method = RequestMethod.GET)
	public String queryMappByOrderStatus(@PathVariable("orderStatus")String orderStatus, @PathVariable("instId")Integer instId) {
		List<Map<?, ?>> resultList = null;
		resultList = this.customerOrderService.queryMappByOrderStatus(orderStatus, instId);
		return new DataRetVal(CarexxConstant.RetCode.SUCCESS, resultList).toJSON();
	}

	@RequestMapping(value = "/modify_endTime/{order}", method = RequestMethod.GET)
	public BasicRetVal modifyServiceEndTime(@PathVariable("order") String order) throws BizException{
		try {
			this.customerOrderManager.modifyServiceEndTime(order);
		} catch (BizException e) {
			return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
		}
		return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
	}
}
