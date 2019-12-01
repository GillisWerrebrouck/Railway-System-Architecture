package com.railway.station_service;

import java.sql.Date;
import java.sql.Timestamp;

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
	public CommandLineRunner populateDatabase (StationRepository stationRepository, PlatformRepository platformRepository, SheduleItemRepository sheduleItemRepository) {

		return(args)->{
			
			Address a = new Address("Maria Hendrikaplein", "Gent", "Oost-Vlaanderen", "België" );
			Station s = new Station("Gent-Sint-Pieters", a);
			
			Platform p1 = new Platform(7);
			Platform p2 = new Platform(8);
			
			SheduleItem Sitem1 = new SheduleItem(5465, new Timestamp(119, 11, 2, 12, 0, 0, 0), new Timestamp(119, 11, 2, 12, 10, 0, 0), 4);
			SheduleItem Sitem2 = new SheduleItem(4663, new Timestamp(119, 11, 2, 12, 20, 0, 0), new Timestamp(119, 11, 2, 12, 40, 0, 0), 0);
			SheduleItem Sitem3 = new SheduleItem(462, new Timestamp(119, 11, 2, 13, 0, 0, 0), new Timestamp(119, 11, 2, 13, 13, 0, 0), 16);

			s.setName("Gent-Sint-Pieters");
			s.setAddress(a);
			s.getPlatforms().add(p1);
			s.getPlatforms().add(p2);
			
			p1.setStation(s);
			p2.setStation(s);
			
			Sitem1.setPlatform(p1);
			Sitem2.setPlatform(p1);
			Sitem3.setPlatform(p2);
			
			p2.getReservedSlots().add(Sitem3);
			p1.getReservedSlots().add(Sitem1);
			p1.getReservedSlots().add(Sitem2);
			
			stationRepository.save(s);
			platformRepository.save(p1);
			platformRepository.save(p2);
			sheduleItemRepository.save(Sitem1);
			sheduleItemRepository.save(Sitem2);
			sheduleItemRepository.save(Sitem3);
		};
	}
}


