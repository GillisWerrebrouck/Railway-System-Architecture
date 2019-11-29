package com.railway.train_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase; 

@SpringBootApplication
public class RailwayAppTrainApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(RailwayAppTrainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppTrainApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner testRepo(TrainRepository trainRepository) {
		return (args) -> {
			MongoClientURI uri = new MongoClientURI(
				    "mongodb+srv://TrainService:panda@cluster0-6ipq1.mongodb.net/test?retryWrites=true&w=majority");

				MongoClient mongoClient = new MongoClient(uri);
				MongoDatabase database = mongoClient.getDatabase("test");
				/*
			logger.info("startBegin");
			Train treintje = new Train(Type.IC, 9.999, 100, 15, Status.ACTIVE, null);
			trainRepository.save(treintje);
			trainRepository.findAll().forEach((t) -> System.out.println(t.toString()));
			System.out.println("start");*/
		};
	}

}
