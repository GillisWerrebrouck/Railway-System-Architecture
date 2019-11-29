package com.railway.train_service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
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
			/*MongoClientURI uri = new MongoClientURI(
				    "mongodb+srv://TrainService:panda@cluster0-6ipq1.mongodb.net/test?retryWrites=true&w=majority");

				MongoClient mongoClient = new MongoClient(uri);
				MongoDatabase database = mongoClient.getDatabase("trainService");
				MongoCollection c = database.getCollection("trains");
				c*/
				
			logger.info("startBegin");
			Map<String,String> m = new HashMap<String, String>();
			m.put("kapotte deur", "da wordt een nieuwe deur");
			TechnicalDetails t = new TechnicalDetails(Fuel.DIESEL, LocalDate.parse("2019-01-01"), m);
			
			Train treintje = new Train(Type.IC, 9.999, 100, 15, Status.ACTIVE, t);
			trainRepository.save(treintje);
			trainRepository.findAll().forEach((te) -> System.out.println(te.toString()));
			System.out.println("start");
		};
	}
	
	/*
	 * spring.data.mongodb.host=[host]
spring.data.mongodb.port=[port]
spring.data.mongodb.authentication-database=[authentication_database]
spring.data.mongodb.username=[username]
spring.data.mongodb.password=[password]
spring.data.mongodb.database=rest_tutorial*/

}
