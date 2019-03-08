package com.sh.carexx.uc.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class TimingWorkManager {

	@Autowired
	private CustomerOrderManager customerOrderManager;
	
	@Autowired
	private CustomerOrderScheduleManager customerOrderScheduleManager;
	
//    @Scheduled(cron = "0 0 08,20 * * ?")
//	public void extendedOrder() {
//		try {
//			System.out.println("定时任务开始");
//			this.customerOrderScheduleManager.timingShedule();
//			this.customerOrderManager.modifyOrderAmtAndHoliday();
//			System.out.println("定时任务结束");
//		} catch (BizException e) {
//			e.printStackTrace();
//		}
//	}
}
