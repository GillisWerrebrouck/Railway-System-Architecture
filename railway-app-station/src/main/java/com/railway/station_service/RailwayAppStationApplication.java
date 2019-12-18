package com.railway.station_service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import com.railway.station_service.adapters.messaging.Channels;
import com.railway.station_service.domain.Address;
import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.ScheduleItem;
import com.railway.station_service.domain.Station;
import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.ScheduleItemRepository;
import com.railway.station_service.persistence.StationRepository;

@SpringBootApplication
@EnableBinding(Channels.class)
public class RailwayAppStationApplication {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppStationApplication.class);
	private StationRepository stationRepository;
	private PlatformRepository platformRepository;
	private ScheduleItemRepository scheduleItemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppStationApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateDatabase (StationRepository stationRepository, PlatformRepository platformRepository, ScheduleItemRepository scheduleItemRepository) {
		return(args)->{			
			this.stationRepository = stationRepository;
			this.platformRepository = platformRepository;	
			this.scheduleItemRepository = scheduleItemRepository;

			createStation(UUID.fromString("11018de0-1943-42b2-929d-a707f751f79c"), "Gent-Sint-Pieters", "Koningin Maria Hendrikaplein 1", "Gent", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("a39b1971-fc82-49b2-809a-444105e03c8d"), "Gent-Dampoort", "Oktrooiplein 10", "Gent", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("cf204af6-a407-47ea-af89-f0f989e7bd8a"), "Kortrijk", "Stationsplein 8", "Kortrijk", "West-Vlaanderen", "België");
			createStation(UUID.fromString("73df5f20-33e9-4518-bc23-3cf143c59198"), "Waregem", "Noorderlaan 71", "Waregem", "West-Vlaanderen", "België");
			createStation(UUID.fromString("bc51f294-6823-41cd-be82-d5ba2da4b04d"), "Aalter", "Stationsplein 2", "Aalter", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("d6951807-1fcc-4966-aa30-d4f399685a90"), "De Pinte", "Stationsstraat 25", "De Pinte", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("1454163f-f24f-490f-9e06-f97ffaf008e3"), "Deinze", "Statieplein 4", "Deinze", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("4181c609-7ed5-473e-befe-8013cd75c24c"), "Eeklo", "Koningin Astridplein 1", "Eeklo", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("7b8d9768-7310-4b7d-b6e4-f58abbeac63e"), "Wondelgem", "Wondelgemstationplein 36", "Wondelgem", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("5514be96-c4f1-4dd7-ace4-ac5cbd29c17d"), "Oudenaarde", "Stationplein 1 Gewest", "Oudenaarde", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("5b659978-1c39-4372-97ec-a6e4d1418ef3"), "Zottegem", "Stationsplein 12", "Zottegem", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("b44c17fc-6df1-4808-83d6-838f5637c9c7"), "Denderleeuw", "Stationsplein", "Denderleeuw", "Oost-Vlaanderen", "België");
			createStation(UUID.fromString("05cce0f7-1409-4224-926a-db3b4c4a8ce5"), "Brussel-Zuid", "Fonsnylaan 47b", "Brussel", "Brussels", "België");
			
			logger.info("----------------------------ITEMS--------------------------------");
			for(ScheduleItem s : scheduleItemRepository.findAll()) {
				logger.info(s.toString());
			}
		};
	}
	
	public void createStation(UUID id, String name, String street, String city, String province, String country) {
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

		logger.info("Station: " + station.toString());
	}
}
