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
	
    @Scheduled(cron = "0/5 0 0 * * ? ")
	public void extendedOrder() {
    	System.out.println("1");
		try {
			System.out.println("2");
			this.customerOrderScheduleManager.timingShedule();
			this.customerOrderManager.updateServiceEndTime();
			
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
}
