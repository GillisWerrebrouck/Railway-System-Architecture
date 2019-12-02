package com.railway.station_service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.railway.station_service.domain.Address;
import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.ScheduleItem;
import com.railway.station_service.domain.Station;
import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.SheduleItemRepository;
import com.railway.station_service.persistence.StationRepository;

@SpringBootApplication
public class RailwayAppStationApplication {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppStationApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppStationApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateDatabase (StationRepository stationRepository, PlatformRepository platformRepository, SheduleItemRepository sheduleItemRepository) {
		return(args)->{
			Address address = new Address("Maria Hendrikaplein", "Gent", "Oost-Vlaanderen", "België" );
			Station station = new Station("Gent-Sint-Pieters", address);
			
			Platform p1 = new Platform(7);
			Platform p2 = new Platform(8);
			
			ScheduleItem scheduleItem01 = new ScheduleItem(5465, LocalDateTime.of(2019,11,2,6,30,40,0), LocalDateTime.of(2019,11,2,6,35,40,0),4);
			ScheduleItem scheduleItem02 = new ScheduleItem(5465, LocalDateTime.of(2019,11,2,6,40,12,0), LocalDateTime.of(2019,11,2,6,45,40,0),0);
			ScheduleItem scheduleItem03 = new ScheduleItem(5465, LocalDateTime.of(2019,11,2,6,50,48,0), LocalDateTime.of(2019,11,2,6,55,40,0),16);


			station.setName("Gent-Sint-Pieters");
			station.setAddress(address);
			station.getPlatforms().add(p1);
			station.getPlatforms().add(p2);
			
			p1.setStation(station);
			p2.setStation(station);
			
			scheduleItem01.setPlatform(p1);
			scheduleItem02.setPlatform(p1);
			scheduleItem02.setPlatform(p2);
			
			p1.getReservedSlots().add(scheduleItem01);
			p1.getReservedSlots().add(scheduleItem02);
			p2.getReservedSlots().add(scheduleItem03);
			
			stationRepository.save(station);
			platformRepository.save(p1);
			platformRepository.save(p2);
			sheduleItemRepository.save(scheduleItem01);
			sheduleItemRepository.save(scheduleItem02);
			sheduleItemRepository.save(scheduleItem03);
			
			logger.info(station.toString());
		};
	}
}
