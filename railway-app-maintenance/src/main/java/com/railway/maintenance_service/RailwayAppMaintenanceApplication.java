package com.railway.maintenance_service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import com.railway.maintenance_service.adapters.messaging.Channels;
import com.railway.maintenance_service.domain.MaintenanceType;
import com.railway.maintenance_service.domain.ScheduleItem;
import com.railway.maintenance_service.domain.Status;
import com.railway.maintenance_service.persistence.MaintenanceRepository;

@SpringBootApplication
@EnableBinding(Channels.class)
public class RailwayAppMaintenanceApplication {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppMaintenanceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppMaintenanceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateDatabase(MaintenanceRepository maintenanceRepository) {
		return (args)->{
			ScheduleItem scheduleItem01 = new ScheduleItem("5df29bec04c7bf325077c427", LocalDateTime.of(2018, Month.DECEMBER, 20, 13, 30, 0, 0), LocalDateTime.of(2018, Month.DECEMBER, 20, 15, 45, 0, 0), Status.SCHEDULED, "train wrecked", MaintenanceType.MAINTENANCE);
			
			Collection<String> staffIds = new ArrayList<>();
			staffIds.add("001");
			staffIds.add("002");
			staffIds.add("003");
			scheduleItem01.setStaffIds(staffIds);
			
			maintenanceRepository.save(scheduleItem01);
			
			logger.info("ScheduleItem01: " + scheduleItem01.toString());
		};
	}
}
