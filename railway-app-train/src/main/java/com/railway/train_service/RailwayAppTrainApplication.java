package com.railway.train_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import com.railway.train_service.adapters.messaging.Channels;
import com.railway.train_service.domain.FuelType;
import com.railway.train_service.domain.TechnicalDetails;
import com.railway.train_service.domain.Train;
import com.railway.train_service.domain.TrainType;
import com.railway.train_service.persistence.TrainRepository;

@SpringBootApplication
@EnableBinding(Channels.class)
public class RailwayAppTrainApplication {
	private static final Logger logger = LoggerFactory.getLogger(RailwayAppTrainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppTrainApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateDatabase(TrainRepository trainRepository) {
		return (args) -> {
			trainRepository.deleteAll();
			
			TechnicalDetails technicalDetails01 = new TechnicalDetails(FuelType.DIESEL);
			TechnicalDetails technicalDetails02 = new TechnicalDetails(FuelType.HYBRID);
			TechnicalDetails technicalDetails03 = new TechnicalDetails(FuelType.ELECTRIC);

			Train train01 = new Train(TrainType.IC, 150, 60, technicalDetails01);
			Train train02 = new Train(TrainType.IR, 150, 60, technicalDetails02);
			Train train03 = new Train(TrainType.P, 100, 30, technicalDetails03);

			trainRepository.save(train01);
			trainRepository.save(train02);
			trainRepository.save(train03);

			logger.info("Train01: " + train01.toString());
			logger.info("Train02: " + train02.toString());
			logger.info("Train03: " + train03.toString());
		};
	}
}
