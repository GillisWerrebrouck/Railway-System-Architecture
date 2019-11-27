package com.railway.station_service;

import java.awt.List;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RailwayAppStationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppStationApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner saveStation (StationRepository stationRepository) {

		return(args)->{

			Address a = new Address();
			ArrayList<Platform> pl = new ArrayList<>();
			ArrayList<InformationPanel> in = new ArrayList<>();
			Platform p = new Platform(1, in);
			pl.add(p);
			
			Station s = new Station("Gent-Sint-Pieters",a, pl);
			stationRepository.save(s);
			System.out.println("ietske");
		};



	}
}


