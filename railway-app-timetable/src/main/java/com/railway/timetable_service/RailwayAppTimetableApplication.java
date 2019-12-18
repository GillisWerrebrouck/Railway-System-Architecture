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
import org.springframework.cloud.stream.annotation.EnableBinding;

import org.springframework.context.annotation.Bean;

import com.railway.timetable_service.adapters.messaging.Channels;
import com.railway.timetable_service.adapters.messaging.TrainType;
import com.railway.timetable_service.domain.TimetableItem;
import com.railway.timetable_service.persistence.TimetableItemRepository;

@SpringBootApplication
@EnableBinding(Channels.class)
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

			TimetableItem timetableItem01 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 20, 10, 00, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 11, 00, 00),  1L, "5dec38cfd12963502efef081", TrainType.IR, staffIds);
			TimetableItem timetableItem02 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 20, 13, 35, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 14, 5, 00), 2L, "5dec38cfd12963502efef082", TrainType.IC, staffIds);
			TimetableItem timetableItem03 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 20, 15, 8, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 15, 52, 00), 3L, "5dec38cfd12963502efef083", TrainType.IR, staffIds);
			TimetableItem timetableItem04 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 21, 8, 12, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 9, 56, 00), 3L, "5dec38cfd12963502efef084", TrainType.P, staffIds);
			TimetableItem timetableItem05 = new TimetableItem(LocalDateTime.of(2018, Month.DECEMBER, 21, 8, 12, 00), LocalDateTime.of(2018, Month.DECEMBER, 20, 9, 56, 00), 53L, "5dec38cfd12963502efef084", TrainType.P, staffIds);

			timetableItemRepository.save(timetableItem01);
			timetableItemRepository.save(timetableItem02);
			timetableItemRepository.save(timetableItem03);
			timetableItemRepository.save(timetableItem04);
			timetableItemRepository.save(timetableItem05);

			logger.info(timetableItem01.toString());
			logger.info(timetableItem02.toString());
			logger.info(timetableItem03.toString());
			logger.info(timetableItem04.toString());
			logger.info(timetableItem05.toString());
		};
	}
}
