package com.railway.staff_service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import com.railway.staff_service.adapters.messaging.Channels;
import com.railway.staff_service.domain.StaffMember;
import com.railway.staff_service.domain.StaffMemberType;
import com.railway.staff_service.persistence.StaffMembersRepository;

@SpringBootApplication
@EnableBinding(Channels.class)
public class RailwayAppStaffApplication {
	private static final Logger logger = LoggerFactory.getLogger(RailwayAppStaffApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppStaffApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabase(StaffMembersRepository staffMemberRepository) {
		return (args) ->{
//			StaffMember staffMember01 = new StaffMember("Adrian", "Cannon", new GregorianCalendar(1980, Calendar.FEBRUARY, 21).getTime(), StaffMemberType.MECHANIC);
//			StaffMember staffMember02 = new StaffMember("Allisson", "Hanson", new GregorianCalendar(1985, Calendar.MARCH, 30).getTime(), StaffMemberType.MECHANIC);
//			StaffMember staffMember03 = new StaffMember("Allan", "Brandt", new GregorianCalendar(1982, Calendar.DECEMBER, 4).getTime(), StaffMemberType.CONDUCTOR);
//			StaffMember staffMember04 = new StaffMember("Sara", "Durham", new GregorianCalendar(1978, Calendar.OCTOBER, 3).getTime(), StaffMemberType.TRAIN_OPERATOR);
//			StaffMember staffMember05 = new StaffMember("Tommy", "Oneill", new GregorianCalendar(1976, Calendar.OCTOBER, 18).getTime(), StaffMemberType.TRAIN_OPERATOR);
//			StaffMember staffMember06 = new StaffMember("Charles", "Werner", new GregorianCalendar(1972, Calendar.SEPTEMBER, 2).getTime(), StaffMemberType.CONDUCTOR);
//			StaffMember staffMember07 = new StaffMember("Juliette", "Massey", new GregorianCalendar(1971, Calendar.OCTOBER, 4).getTime(), StaffMemberType.MECHANIC);
//			StaffMember staffMember08 = new StaffMember("Brent", "Chang", new GregorianCalendar(1973, Calendar.JULY, 30).getTime(), StaffMemberType.MECHANIC);
//			StaffMember staffMember09 = new StaffMember("Morgan", "Allen", new GregorianCalendar(1975, Calendar.JANUARY, 13).getTime(), StaffMemberType.CONDUCTOR);
//			StaffMember staffMember10 = new StaffMember("Alexia", "Carter", new GregorianCalendar(1978, Calendar.APRIL, 25).getTime(), StaffMemberType.CONDUCTOR);
//
////			ScheduleItem scheduleItem01 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 18, 13, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 18, 15, 0, 0, 0));
////			ScheduleItem scheduleItem02 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 18, 15, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 18, 18, 0, 0, 0));
////			ScheduleItem scheduleItem03 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 20, 8, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 20, 10, 0, 0, 0));
////			ScheduleItem scheduleItem04 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 22, 15, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 22, 17, 30, 0, 0));
////			ScheduleItem scheduleItem05 = new ScheduleItem(LocalDateTime.of(2019, Calendar.DECEMBER, 22, 15, 0, 0, 0), LocalDateTime.of(2019, Calendar.DECEMBER, 22, 17, 30, 0, 0));
////
////			staffMember01.addScheduleItem(scheduleItem01);
////			staffMember02.addScheduleItem(scheduleItem02);
////			staffMember03.addScheduleItem(scheduleItem03);
////			staffMember03.addScheduleItem(scheduleItem04);
////			staffMember04.addScheduleItem(scheduleItem05);
//			
//			staffMemberRepository.save(staffMember01);
//			staffMemberRepository.save(staffMember02);
//			staffMemberRepository.save(staffMember03);
//			staffMemberRepository.save(staffMember04);
//			staffMemberRepository.save(staffMember05);
//			staffMemberRepository.save(staffMember06);
//			staffMemberRepository.save(staffMember07);
//			staffMemberRepository.save(staffMember08);
//			staffMemberRepository.save(staffMember09);
//			staffMemberRepository.save(staffMember10);
//			
//			logger.info("StaffMember01: " + staffMember01.toString());
//			logger.info("StaffMember02: " + staffMember02.toString());
//			logger.info("StaffMember03: " + staffMember03.toString());
//			logger.info("StaffMember04: " + staffMember04.toString());
//			logger.info("StaffMember05: " + staffMember05.toString());
//			logger.info("StaffMember06: " + staffMember06.toString());
//			logger.info("StaffMember07: " + staffMember07.toString());
//			logger.info("StaffMember08: " + staffMember08.toString());
//			logger.info("StaffMember09: " + staffMember09.toString());
//			logger.info("StaffMember10: " + staffMember10.toString());
		};
	}
}
