package com.railway.station_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.railway.station_service.domain.Address;
import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.Station;
import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.StationRepository;

@SpringBootApplication
public class RailwayAppStationApplication {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppStationApplication.class);
	private StationRepository stationRepository;
	private PlatformRepository platformRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppStationApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateDatabase (StationRepository stationRepository, PlatformRepository platformRepository) {
		return(args)->{			
			this.stationRepository = stationRepository;
			this.platformRepository = platformRepository;			

			createStation(1L, "Gent-Sint-Pieters", "Koningin Maria Hendrikaplein 1", "Gent", "Oost-Vlaanderen", "België");
			createStation(2L, "Gent-Dampoort", "Oktrooiplein 10", "Gent", "Oost-Vlaanderen", "België");
			createStation(3L, "Kortrijk", "Stationsplein 8", "Kortrijk", "West-Vlaanderen", "België");
			createStation(4L, "Waregem", "Noorderlaan 71", "Waregem", "West-Vlaanderen", "België");
			createStation(5L, "Aalter", "Stationsplein 2", "Aalter", "Oost-Vlaanderen", "België");
			createStation(6L, "De Pinte", "Stationsstraat 25", "De Pinte", "Oost-Vlaanderen", "België");
			createStation(7L, "Deinze", "Statieplein 4", "Deinze", "Oost-Vlaanderen", "België");
			createStation(8L, "Eeklo", "Koningin Astridplein 1", "Eeklo", "Oost-Vlaanderen", "België");
			createStation(9L, "Wondelgem", "Wondelgemstationplein 36", "Wondelgem", "Oost-Vlaanderen", "België");
			createStation(10L, "Oudenaarde", "Stationplein 1 Gewest", "Oudenaarde", "Oost-Vlaanderen", "België");
			createStation(11L, "Zottegem", "Stationsplein 12", "Zottegem", "Oost-Vlaanderen", "België");
			createStation(12L, "Denderleeuw", "Stationsplein", "Denderleeuw", "Oost-Vlaanderen", "België");
			createStation(13L, "Brussel-Zuid", "Fonsnylaan 47b", "Brussel", "Brussels", "België");
		};
	}
	
	public void createStation(Long id, String name, String street, String city, String province, String country) {
		Address address = new Address(street, city, province, country);
		Station station = new Station(name, address);
		station.setId(id);

		Platform p1 = new Platform(1);
		Platform p2 = new Platform(2);
		Platform p3 = new Platform(3);
		platformRepository.save(p1);
		platformRepository.save(p2);
		platformRepository.save(p3);
		
		station.getPlatforms().add(p1);
		station.getPlatforms().add(p2);
		station.getPlatforms().add(p3);
		stationRepository.save(station);
		
		p1.setStation(station);
		p2.setStation(station);
		p3.setStation(station);
		platformRepository.save(p1);
		platformRepository.save(p2);
		platformRepository.save(p3);

		logger.info("Station " + id.toString() + ": " + station.toString());
	}
}
