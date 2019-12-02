package com.railway.train_service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RailwayAppTrainApplication {
	private static final Logger logger = LoggerFactory.getLogger(RailwayAppTrainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppTrainApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner testRepo(TrainRepository trainRepository) {
		return (args) -> {	
			Map<String,String> m = new HashMap<String, String>();
			m.put("kapotte deur", "dat wordt een nieuwe deur");
			TechnicalDetails t = new TechnicalDetails(FuelType.DIESEL, LocalDate.parse("2019-01-01"), m);
			List<ScheduleItem> items = new ArrayList<ScheduleItem>();
			items.add(new ScheduleItem(LocalDateTime.parse("2019-01-01"), LocalDateTime.parse("2019-01-10")));
			Train train1 = new Train(TrainType.IC, 9.999, 100, 15, TrainStatus.ACTIVE, t, items);
			trainRepository.save(train1);
			trainRepository.findAll().forEach((te) -> logger.info(te.toString()));
		};
	}
}
