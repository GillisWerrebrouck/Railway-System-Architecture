package com.railway.staff_service;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.railway.staff_service.domain.ScheduleRecord;
import com.railway.staff_service.domain.StaffMemberType;
import com.railway.staff_service.persistence.ScheduleRecordRepository;

@SpringBootApplication
public class RailwayAppStaffApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppStaffApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabase(ScheduleRecordRepository scheduleRecordRepository) {
		return (args) ->{
			scheduleRecordRepository.deleteAll();
			final ScheduleRecord scheduleRecord1 = new ScheduleRecord(LocalDate.now(),LocalDate.now(),StaffMemberType.CONDUCTOR);
			final ScheduleRecord scheduleRecord2 = new ScheduleRecord(LocalDate.now(),LocalDate.now().plusDays(1),StaffMemberType.TRAIN_OPERATOR);
			final ScheduleRecord scheduleRecord3 = new ScheduleRecord(LocalDate.now(),LocalDate.now(),StaffMemberType.MECHANIC);
			final ScheduleRecord scheduleRecord4 = new ScheduleRecord(LocalDate.now(),LocalDate.now(),StaffMemberType.CONDUCTOR);
			final ScheduleRecord scheduleRecord5 = new ScheduleRecord(LocalDate.now(),LocalDate.now(),StaffMemberType.CONDUCTOR);
			
			scheduleRecordRepository.save(scheduleRecord1);
			scheduleRecordRepository.save(scheduleRecord2);
			scheduleRecordRepository.save(scheduleRecord3);
			scheduleRecordRepository.save(scheduleRecord4);
			scheduleRecordRepository.save(scheduleRecord5);
		};
	}
}
