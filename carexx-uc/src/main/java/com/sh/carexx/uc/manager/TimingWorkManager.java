package com.sh.carexx.uc.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sh.carexx.common.exception.BizException;

@Service
@Component
public class TimingWorkManager {

	@Autowired
	private CustomerOrderManager customerOrderManager;
	
	@Autowired
	private CustomerOrderScheduleManager customerOrderScheduleManager;
	
    @Scheduled(cron = "0 14 11 * * ?")
	public void extendedOrder() {
		try {
			System.out.println("定时任务开始");
			this.customerOrderScheduleManager.timingShedule();
			this.customerOrderManager.modifyOrderAmtAndHoliday();
			System.out.println("定时任务结束");
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
}
