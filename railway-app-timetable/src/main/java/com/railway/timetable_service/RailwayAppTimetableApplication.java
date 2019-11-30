package com.railway.timetable_service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.railway.timetable_service.domain.TimetableItem;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@SpringBootApplication
public class RailwayAppTimetableApplication {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppTimetableApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppTimetableApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateDatabase(TimetableItemRepository timetableItemRepository) {
		return (args) ->{
			logger.info("Populating database with timetable items");

			timetableItemRepository.deleteAll();

			List<Long> staffIds = new ArrayList<Long>();
			staffIds.add(1L);
			staffIds.add(2L);

			TimetableItem timetableItem01 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 20, 10, 00, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 11, 00, 00), 1L,  1L, staffIds);
			TimetableItem timetableItem02 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 20, 13, 35, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 14, 5, 00), 1L, 2L, staffIds);
			TimetableItem timetableItem03 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 20, 15, 8, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 15, 52, 00), 2L, 3L, staffIds);
			TimetableItem timetableItem04 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 21, 8, 12, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 9, 56, 00), 2L, 3L, staffIds);

			timetableItemRepository.save(timetableItem01);
			timetableItemRepository.save(timetableItem02);
			timetableItemRepository.save(timetableItem03);
			timetableItemRepository.save(timetableItem04);
		};
	}
}
