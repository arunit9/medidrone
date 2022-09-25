package com.app.medidrone.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.medidrone.service.DroneDispatcherService;

@Component
@EnableAsync
public class SystemMonitor {

	@Autowired
	private DroneDispatcherService droneDispatcherService;

	/**
	 * Audit the battery capacities of all registered drones periodically
	 * @throws InterruptedException
	 */
	@Async
	@Scheduled(fixedRateString = "${medidrone.audit.period}")
    public void scheduleBatteryCheck() throws InterruptedException {
		droneDispatcherService.auditDroneBattery();
    }
}
