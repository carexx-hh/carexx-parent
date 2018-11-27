package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.pay.PayMethod;
import com.sh.carexx.common.enums.staff.JobType;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.ValidUtils;
import com.sh.carexx.model.uc.CareServiceRatio;
import com.sh.carexx.model.uc.CustomerOrder;
import com.sh.carexx.model.uc.CustomerOrderTime;
import com.sh.carexx.uc.dao.CareServiceRatioMapper;
import com.sh.carexx.uc.dao.CustomerOrderMapper;
import com.sh.carexx.uc.dao.CustomerOrderTimeMapper;
import com.sh.carexx.uc.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	private CustomerOrderMapper customerOrderMapper;

	@Autowired
	private CareServiceRatioMapper careServiceRatioMapper;
	
	@Autowired
	private CustomerOrderTimeMapper customerOrderTimeMapper;

	@Override
	public void save(CustomerOrder customerOrder) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.insert(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void confirmCompleted(CustomerOrder customerOrder) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.confirmCompleted(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public List<CustomerOrder> getAllOrder() {
		return this.customerOrderMapper.selectAllOrder();
	}

	@Override
	public List<Map<?, ?>> getByUserId(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		return this.customerOrderMapper.selectByUserId(customerOrderQueryFormBean);
	}

	@Override
	public List<Map<?, ?>> getDoneOrderByUserId(Integer userId) {
		return this.customerOrderMapper.selectDoneOrderByUserId(userId);
	}

	@Override
	public List<Map<?, ?>> getOrderDetail(String orderNo) {
		return this.customerOrderMapper.selectOrderDetail(orderNo);
	}

	@Override
	public Integer getCustomerOrderCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceStartTime())) {
			customerOrderQueryFormBean.setServiceStartTime(
					customerOrderQueryFormBean.getServiceStartTime() + CarexxConstant.Datetime.DAY_BEGIN_SUFFIX);
		}
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())) {
			customerOrderQueryFormBean.setServiceEndTime(
					customerOrderQueryFormBean.getServiceEndTime() + CarexxConstant.Datetime.DAY_END_SUFFIX);
		}
		return this.customerOrderMapper.selectCustomerOrderCount(customerOrderQueryFormBean);
	}

	@Override
	public List<Map<?, ?>> queryCustomerOrderList(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceStartTime())) {
			customerOrderQueryFormBean.setServiceStartTime(
					customerOrderQueryFormBean.getServiceStartTime() + CarexxConstant.Datetime.DAY_BEGIN_SUFFIX);
		}
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())) {
			customerOrderQueryFormBean.setServiceEndTime(
					customerOrderQueryFormBean.getServiceEndTime() + CarexxConstant.Datetime.DAY_END_SUFFIX);
		}
		return this.customerOrderMapper.selectCustomerOrderList(customerOrderQueryFormBean);
	}

	@Override
	public Integer getByWorkTypeIdCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		String serviceStartTime = customerOrderQueryFormBean.getServiceStartTime();
		
		if (customerOrderQueryFormBean.getJobType() == JobType.DAY_JOB.getValue()) {
			CustomerOrderTime customerOrderTime = this.customerOrderTimeMapper.selectJobTypeExistence(
					customerOrderQueryFormBean.getInstId(), customerOrderQueryFormBean.getJobType());
			 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			 String startTime = formatter.format(customerOrderTime.getStartTime());
			 String endTime = formatter.format(customerOrderTime.getEndTime());

			if (ValidUtils.isDate(serviceStartTime)) {
				customerOrderQueryFormBean.setServiceStartTime(
						serviceStartTime + " " + startTime);
			}
			if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())
					|| customerOrderQueryFormBean.getServiceEndTime() == null) {
				customerOrderQueryFormBean.setServiceEndTime(
						serviceStartTime + " " + endTime);
			}
		}else if(customerOrderQueryFormBean.getJobType() == JobType.NIGHT_JOB.getValue()) {
			CustomerOrderTime customerOrderTime = this.customerOrderTimeMapper.selectJobTypeExistence(
					customerOrderQueryFormBean.getInstId(), customerOrderQueryFormBean.getJobType());
			 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			 String startTime = formatter.format(customerOrderTime.getStartTime());
			 String endTime = formatter.format(customerOrderTime.getEndTime());

			if (ValidUtils.isDate(serviceStartTime)) {
				customerOrderQueryFormBean.setServiceStartTime(
						serviceStartTime + " " + startTime);
			}
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date serviceEndTime = null;
			try {
				serviceEndTime = format.parse(serviceStartTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(serviceEndTime);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为后一天
			
			if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())
					|| customerOrderQueryFormBean.getServiceEndTime() == null) {
				customerOrderQueryFormBean.setServiceEndTime(format.format(calendar.getTime()));
				customerOrderQueryFormBean.setServiceEndTime(customerOrderQueryFormBean.getServiceEndTime() + " " + endTime);
			}
		} else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date serviceEndTime = null;
			try {
				serviceEndTime = format.parse(serviceStartTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(serviceEndTime);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为后一天
			customerOrderQueryFormBean.setServiceEndTime(format.format(calendar.getTime()));

			List<CustomerOrderTime> customerOrderTimeList = this.customerOrderTimeMapper
					.selectByInstId(customerOrderQueryFormBean.getInstId());
			for (CustomerOrderTime customerOrderTime : customerOrderTimeList) {
				 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				 String startTime = formatter.format(customerOrderTime.getStartTime());
				 String endTime = formatter.format(customerOrderTime.getEndTime());
				if (customerOrderTime.getJobType() == JobType.DAY_JOB.getValue()
						&& ValidUtils.isDate(serviceStartTime)) {
					customerOrderQueryFormBean.setServiceStartTime(
							serviceStartTime + " " + startTime);
				}
				if (customerOrderTime.getJobType() == JobType.NIGHT_JOB.getValue()
						&& ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())) {
					customerOrderQueryFormBean.setServiceEndTime(
							customerOrderQueryFormBean.getServiceEndTime() + " " + endTime);
				}
			}
		}
		return this.customerOrderMapper.selectByWorkTypeIdCount(customerOrderQueryFormBean);
	}

	@Override
	public List<Map<?, ?>> queryByWorkTypeIdList(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
String serviceStartTime = customerOrderQueryFormBean.getServiceStartTime();
		
		if (customerOrderQueryFormBean.getJobType() == JobType.DAY_JOB.getValue()) {
			CustomerOrderTime customerOrderTime = this.customerOrderTimeMapper.selectJobTypeExistence(
					customerOrderQueryFormBean.getInstId(), customerOrderQueryFormBean.getJobType());
			 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			 String startTime = formatter.format(customerOrderTime.getStartTime());
			 String endTime = formatter.format(customerOrderTime.getEndTime());

			if (ValidUtils.isDate(serviceStartTime)) {
				customerOrderQueryFormBean.setServiceStartTime(
						serviceStartTime + " " + startTime);
			}
			if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())
					|| customerOrderQueryFormBean.getServiceEndTime() == null) {
				customerOrderQueryFormBean.setServiceEndTime(
						serviceStartTime + " " + endTime);
			}
		}else if(customerOrderQueryFormBean.getJobType() == JobType.NIGHT_JOB.getValue()) {
			CustomerOrderTime customerOrderTime = this.customerOrderTimeMapper.selectJobTypeExistence(
					customerOrderQueryFormBean.getInstId(), customerOrderQueryFormBean.getJobType());
			 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			 String startTime = formatter.format(customerOrderTime.getStartTime());
			 String endTime = formatter.format(customerOrderTime.getEndTime());

			if (ValidUtils.isDate(serviceStartTime)) {
				customerOrderQueryFormBean.setServiceStartTime(
						serviceStartTime + " " + startTime);
			}
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date serviceEndTime = null;
			try {
				serviceEndTime = format.parse(serviceStartTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(serviceEndTime);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为后一天
			
			if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())
					|| customerOrderQueryFormBean.getServiceEndTime() == null) {
				customerOrderQueryFormBean.setServiceEndTime(format.format(calendar.getTime()));
				customerOrderQueryFormBean.setServiceEndTime(customerOrderQueryFormBean.getServiceEndTime() + " " + endTime);
			}
		} else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date serviceEndTime = null;
			try {
				serviceEndTime = format.parse(serviceStartTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(serviceEndTime);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为后一天
			customerOrderQueryFormBean.setServiceEndTime(format.format(calendar.getTime()));

			List<CustomerOrderTime> customerOrderTimeList = this.customerOrderTimeMapper
					.selectByInstId(customerOrderQueryFormBean.getInstId());
			for (CustomerOrderTime customerOrderTime : customerOrderTimeList) {
				 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				 String startTime = formatter.format(customerOrderTime.getStartTime());
				 String endTime = formatter.format(customerOrderTime.getEndTime());
				if (customerOrderTime.getJobType() == JobType.DAY_JOB.getValue()
						&& ValidUtils.isDate(serviceStartTime)) {
					customerOrderQueryFormBean.setServiceStartTime(
							serviceStartTime + " " + startTime);
				}
				if (customerOrderTime.getJobType() == JobType.NIGHT_JOB.getValue()
						&& ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())) {
					customerOrderQueryFormBean.setServiceEndTime(
							customerOrderQueryFormBean.getServiceEndTime() + " " + endTime);
				}
			}
		}
		return this.customerOrderMapper.selectByWorkTypeIdList(customerOrderQueryFormBean);
	}

	@Override
	public CustomerOrder getByOrderNo(String orderNo) {
		return this.customerOrderMapper.selectByOrderNo(orderNo);
	}

	@Override
	public List<Map<?, ?>> queryOrderExistence(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		return this.customerOrderMapper.selectOrderExistence(customerOrderQueryFormBean);
	}

	@Override
	public List<Map<?, ?>> queryMappByOrderStatus(String orderStatus, Integer instId) {
		return this.customerOrderMapper.selectMappByOrderStatus(orderStatus, instId);
	}

	@Override
	public void updateStatus(String orderNo, Byte srcStatus, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.updateStatus(orderNo, srcStatus, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void updateOrderCancel(String orderNo, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.updateOrderCancel(orderNo, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}

	}

	@Override
	public void updateOrderDelete(String orderNo, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.updateOrderDelete(orderNo, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void mappOrderCancel(String orderNo, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.mappOrderCancel(orderNo, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}

	}

	@Override
	public List<Map<String, Object>> queryIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<String, Object>> incomeCount = this.customerOrderMapper.selectIncomeCount(customerOrderQueryFormBean);
		for (Map<String, Object> map : incomeCount) {
			BigDecimal orderAmt = new BigDecimal(String.valueOf(map.get("orderAmt")));
			BigDecimal orderAdjustAmt = new BigDecimal(String.valueOf(map.get("orderAdjustAmt")));
			Integer payType = Integer.parseInt(String.valueOf(map.get("payType")));
			if (payType < 3) {
				BigDecimal pounDage = ((orderAmt.add(orderAdjustAmt)).multiply(new BigDecimal(0.006))).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				map.put("pounDage", pounDage);
			} else {
				map.put("pounDage", 0.0);
			}
		}
		return incomeCount;
	}

	@Override
	public List<Map<String, Object>> queryInstIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<String, Object>> inputInstIncomeCountList = this.customerOrderMapper
				.selectInstIncomeCount(customerOrderQueryFormBean);
		List<Map<String, Object>> outputInstIncomeCountList = new ArrayList<Map<String, Object>>();
		boolean bool = false;
		BigDecimal serviceRatio = new BigDecimal(0);

		List<CareServiceRatio> careServiceRatioList = this.careServiceRatioMapper.selectAllServiceRatio();
		if(customerOrderQueryFormBean.getServiceAddress() != null) {
			for(CareServiceRatio careServiceRatio: careServiceRatioList) {
				if(careServiceRatio.getServiceAddress() == customerOrderQueryFormBean.getServiceAddress()) {
					serviceRatio = careServiceRatio.getServiceRatio();
				}
			}
		}
		for (Map<String, Object> inputInstIncomeCountMap : inputInstIncomeCountList) {
			int index = 0;
			if (outputInstIncomeCountList.size() != 0 || outputInstIncomeCountList != null) {
				for (Map<String, Object> outputInstIncomeCountMap : outputInstIncomeCountList) {
					if (Integer.parseInt(String.valueOf(outputInstIncomeCountMap.get("instId"))) == Integer
							.parseInt(String.valueOf(inputInstIncomeCountMap.get("instId")))) {
						BigDecimal inputOrderAmt = new BigDecimal(
								String.valueOf(inputInstIncomeCountMap.get("orderAmt")))
										.add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));
						BigDecimal outputOrderAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("orderAmt"))).add(inputOrderAmt);

						BigDecimal inputAdjustAmt = new BigDecimal(
								String.valueOf(inputInstIncomeCountMap.get("adjustAmt")));
						BigDecimal outputAdjustAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("adjustAmt"))).add(inputAdjustAmt);

						BigDecimal inputStaffSettleAmt = new BigDecimal(
								String.valueOf(inputInstIncomeCountMap.get("staffSettleAmt")));
						BigDecimal outputStaffSettleAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("staffSettleAmt")))
										.add(inputStaffSettleAmt);

						BigDecimal inputInstSettleAmt = new BigDecimal(
								String.valueOf(inputInstIncomeCountMap.get("instSettleAmt")))
										.add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));
						BigDecimal outputInstSettleAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("instSettleAmt"))).add(inputInstSettleAmt);

						Byte payType = Byte.valueOf(String.valueOf(inputInstIncomeCountMap.get("payType")));

						BigDecimal outputPounDage = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("pounDage")));
						if (payType == PayMethod.ONLINE_PAY.getValue() || payType == PayMethod.SCAN_PAY.getValue()) {
							BigDecimal inputPounDage = inputOrderAmt.multiply(new BigDecimal(0.006)).setScale(2,
									BigDecimal.ROUND_HALF_UP);
							outputPounDage = outputPounDage.add(inputPounDage);
						}
						BigDecimal onlinePayAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("onlinePayAmt")));
						BigDecimal scanPayAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("scanPayAmt")));
						BigDecimal cashPayAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("cashPayAmt")));
						BigDecimal companyTurnAccountAmt = new BigDecimal(
								String.valueOf(outputInstIncomeCountMap.get("companyTurnAccountAmt")));
						if (payType == PayMethod.ONLINE_PAY.getValue()) {
							onlinePayAmt = onlinePayAmt.add(inputOrderAmt);
						} else if (payType == PayMethod.SCAN_PAY.getValue()) {
							scanPayAmt = scanPayAmt.add(inputOrderAmt);
						} else if (payType == PayMethod.CASH_PAY.getValue()) {
							cashPayAmt = cashPayAmt.add(inputOrderAmt);
						} else if (payType == PayMethod.COMPANY_TURN_ACCOUNT.getValue()) {
							companyTurnAccountAmt = companyTurnAccountAmt.add(inputOrderAmt);
						}

						if(customerOrderQueryFormBean.getServiceAddress() == null) {
							for(CareServiceRatio careServiceRatio: careServiceRatioList) {
								if(careServiceRatio.getServiceAddress() == Byte.valueOf(String.valueOf(inputInstIncomeCountMap.get("serviceAddress")))) {
									serviceRatio = careServiceRatio.getServiceRatio();
								}
							}
						}
						BigDecimal inputServiceCharge = new BigDecimal(
								String.valueOf(inputInstIncomeCountMap.get("serviceCharge")));
						BigDecimal outputServiceCharge = new BigDecimal(
								String.valueOf(inputOrderAmt)).multiply(serviceRatio).add(inputServiceCharge);
						
						outputInstIncomeCountMap.put("orderAmt", outputOrderAmt);
						outputInstIncomeCountMap.put("adjustAmt", outputAdjustAmt);
						outputInstIncomeCountMap.put("onlinePayAmt", onlinePayAmt);
						outputInstIncomeCountMap.put("scanPayAmt", scanPayAmt);
						outputInstIncomeCountMap.put("cashPayAmt", cashPayAmt);
						outputInstIncomeCountMap.put("companyTurnAccountAmt", companyTurnAccountAmt);
						outputInstIncomeCountMap.put("staffSettleAmt", outputStaffSettleAmt);
						outputInstIncomeCountMap.put("instSettleAmt", outputInstSettleAmt);
						outputInstIncomeCountMap.put("pounDage", outputPounDage);
						outputInstIncomeCountMap.put("serviceCharge", outputServiceCharge);
						outputInstIncomeCountList.set(index, outputInstIncomeCountMap);

						bool = true;
						break;
					}
					index++;
				}
			}

			if (bool == true) {
				bool = false;
				continue;
			}

			BigDecimal onlinePayAmt = new BigDecimal(0);
			BigDecimal scanPayAmt = new BigDecimal(0);
			BigDecimal cashPayAmt = new BigDecimal(0);
			BigDecimal companyTurnAccountAmt = new BigDecimal(0);

			Map<String, Object> outputInstIncomeCountMap = new HashMap<String, Object>();

			BigDecimal outputOrderAmt = new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("orderAmt")))
					.add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));
			BigDecimal outputInstSettleAmt = new BigDecimal(
					String.valueOf(inputInstIncomeCountMap.get("instSettleAmt")))
							.add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));

			Byte payType = Byte.valueOf(String.valueOf(inputInstIncomeCountMap.get("payType")));
			BigDecimal outputPounDage = new BigDecimal(0.0);
			if (payType == PayMethod.ONLINE_PAY.getValue() || payType == PayMethod.SCAN_PAY.getValue()) {
				BigDecimal inputPounDage = outputOrderAmt.multiply(new BigDecimal(0.006)).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				outputPounDage = outputPounDage.add(inputPounDage);
			}

			if (payType == PayMethod.ONLINE_PAY.getValue()) {
				onlinePayAmt = onlinePayAmt.add(outputOrderAmt);
			} else if (payType == PayMethod.SCAN_PAY.getValue()) {
				scanPayAmt = scanPayAmt.add(outputOrderAmt);
			} else if (payType == PayMethod.CASH_PAY.getValue()) {
				cashPayAmt = cashPayAmt.add(outputOrderAmt);
			} else if (payType == PayMethod.COMPANY_TURN_ACCOUNT.getValue()) {
				companyTurnAccountAmt = companyTurnAccountAmt.add(outputOrderAmt);
			}
			
			if(customerOrderQueryFormBean.getServiceAddress() == null) {
				for(CareServiceRatio careServiceRatio: careServiceRatioList) {
					if(careServiceRatio.getServiceAddress() == Byte.valueOf(String.valueOf(inputInstIncomeCountMap.get("serviceAddress")))) {
						serviceRatio = careServiceRatio.getServiceRatio();
					}
				}
			}
			BigDecimal outputServiceCharge = new BigDecimal(
					String.valueOf(outputOrderAmt)).multiply(serviceRatio);
			
			
			outputInstIncomeCountMap.put("instId", inputInstIncomeCountMap.get("instId"));
			outputInstIncomeCountMap.put("instName", inputInstIncomeCountMap.get("instName"));
			outputInstIncomeCountMap.put("orderAmt", outputOrderAmt);
			outputInstIncomeCountMap.put("adjustAmt", inputInstIncomeCountMap.get("adjustAmt"));
			outputInstIncomeCountMap.put("onlinePayAmt", onlinePayAmt);
			outputInstIncomeCountMap.put("scanPayAmt", scanPayAmt);
			outputInstIncomeCountMap.put("cashPayAmt", cashPayAmt);
			outputInstIncomeCountMap.put("companyTurnAccountAmt", companyTurnAccountAmt);
			outputInstIncomeCountMap.put("staffSettleAmt", inputInstIncomeCountMap.get("staffSettleAmt"));
			outputInstIncomeCountMap.put("instSettleAmt", outputInstSettleAmt);
			outputInstIncomeCountMap.put("pounDage", outputPounDage);
			outputInstIncomeCountMap.put("serviceCharge", outputServiceCharge);
			outputInstIncomeCountList.add(outputInstIncomeCountMap);
		}
		return outputInstIncomeCountList;
	}

	@Override
	public List<Map<String, Object>> queryStaffIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		return this.customerOrderMapper.selectStaffIncomeCount(customerOrderQueryFormBean);
	}

	@Override
	public void update(CustomerOrder customerOrder) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.update(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void updateOrderAmtAndHoliday(CustomerOrder customerOrder) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.updateOrderAmtAndHoliday(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void updateServiceEndTime(CustomerOrder customerOrder) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.updateServiceEndTime(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

}
