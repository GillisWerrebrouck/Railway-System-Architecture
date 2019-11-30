package com.railway.staff_service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.railway.staff_service.domain.ScheduleItem;
import com.railway.staff_service.domain.StaffMember;
import com.railway.staff_service.domain.StaffMemberType;
import com.railway.staff_service.persistence.ScheduleItemRepository;
import com.railway.staff_service.persistence.StaffMembersRepository;

@SpringBootApplication
public class RailwayAppStaffApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppStaffApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabase(StaffMembersRepository staffMemberRepository, ScheduleItemRepository scheduleRecordRepository) {
		return (args) ->{
			staffMemberRepository.deleteAll();
			scheduleRecordRepository.deleteAll();

			StaffMember staffMember01 = new StaffMember("Adrian", "Cannon", new GregorianCalendar(1980, Calendar.FEBRUARY, 21).getTime(), StaffMemberType.MECHANIC);
			StaffMember staffMember02 = new StaffMember("Allisson", "Hanson", new GregorianCalendar(1985, Calendar.MARCH, 30).getTime(), StaffMemberType.MECHANIC);
			StaffMember staffMember03 = new StaffMember("Allan", "Brandt", new GregorianCalendar(1982, Calendar.DECEMBER, 4).getTime(), StaffMemberType.CONDUCTOR);
			StaffMember staffMember04 = new StaffMember("Sara", "Durham", new GregorianCalendar(1978, Calendar.OCTOBER, 3).getTime(), StaffMemberType.TRAIN_OPERATOR);

			staffMemberRepository.save(staffMember01);
			staffMemberRepository.save(staffMember02);
			staffMemberRepository.save(staffMember03);
			staffMemberRepository.save(staffMember04);

			ScheduleItem scheduleItem01 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 18, 13, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 18, 15, 0, 0, 0), staffMember01);
			ScheduleItem scheduleItem02 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 18, 15, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 18, 18, 0, 0, 0), staffMember01);
			ScheduleItem scheduleItem03 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 20, 8, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 20, 10, 0, 0, 0), staffMember02);
			ScheduleItem scheduleItem04 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 22, 15, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 22, 17, 30, 0, 0), staffMember03);
			ScheduleItem scheduleItem05 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 22, 15, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 22, 17, 30, 0, 0), staffMember04);
			
			scheduleRecordRepository.save(scheduleItem01);
			scheduleRecordRepository.save(scheduleItem02);
			scheduleRecordRepository.save(scheduleItem03);
			scheduleRecordRepository.save(scheduleItem04);
			scheduleRecordRepository.save(scheduleItem05);
		};
	}
}
