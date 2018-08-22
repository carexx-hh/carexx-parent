package com.sh.carexx.uc.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sh.carexx.common.exception.BizException;

@Service
public class TimingWorkManager {

	@Autowired
	private CustomerOrderManager customerOrderManager;
	
	@Autowired
	private CustomerOrderScheduleManager customerOrderScheduleManager;
	
	@Scheduled(cron=" 0 0 8,20 * * ? ")
	public void extendedOrder() {
		try {
			this.customerOrderScheduleManager.timingShedule();
			this.customerOrderManager.updateServiceEndTime();
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
}
