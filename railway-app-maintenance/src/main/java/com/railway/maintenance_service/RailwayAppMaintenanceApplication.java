package com.railway.maintenance_service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.railway.maintenance_service.domain.ScheduleItem;
import com.railway.maintenance_service.domain.Status;

@SpringBootApplication
public class RailwayAppMaintenanceApplication {
	
	private static Logger logger = LoggerFactory.getLogger(RailwayAppMaintenanceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppMaintenanceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner printOneScheduledMaintenance() {
		return (args)->{
			logger.info("Printing one maintenance:");
			ScheduleItem scheduleItem = new ScheduleItem(LocalDateTime.now(), Status.SCHEDULED, "train wrecked");
			logger.info(scheduleItem.toString());
		};
	}

}
