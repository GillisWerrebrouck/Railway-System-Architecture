package com.railway.route_management_service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import com.railway.route_management_service.adapters.messaging.Channels;
import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.RouteConnection;
import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@SpringBootApplication
@EnableBinding(Channels.class)
public class RailwayAppRouteManagementApplication {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppRouteManagementApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppRouteManagementApplication.class, args);
	}
	
	@SuppressWarnings("unused")
	@Bean
	public CommandLineRunner populateDatabase(StationRepository stationRepository, ConnectionRepository connectionRepository, RouteRepository routeRepository) {
		return (args) ->{
			logger.info("Populating graph database with test data ...");

			stationRepository.deleteAll();
			routeRepository.deleteAll();

			Station station01 = new Station(UUID.fromString("11018de0-1943-42b2-929d-a707f751f79c"), "Gent-Sint-Pieters");
			stationRepository.save(station01);
			Station station02 = new Station(UUID.fromString("a39b1971-fc82-49b2-809a-444105e03c8d"), "Gent-Dampoort");
			stationRepository.save(station02);
			Station station03 = new Station(UUID.fromString("cf204af6-a407-47ea-af89-f0f989e7bd8a"), "Kortrijk");
			stationRepository.save(station03);
			Station station04 = new Station(UUID.fromString("73df5f20-33e9-4518-bc23-3cf143c59198"), "Waregem");
			stationRepository.save(station04);
			Station station05 = new Station(UUID.fromString("bc51f294-6823-41cd-be82-d5ba2da4b04d"), "Aalter");
			stationRepository.save(station05);
			Station station06 = new Station(UUID.fromString("d6951807-1fcc-4966-aa30-d4f399685a90"), "De Pinte");
			stationRepository.save(station06);
			Station station07 = new Station(UUID.fromString("1454163f-f24f-490f-9e06-f97ffaf008e3"), "Deinze");
			stationRepository.save(station07);
			Station station08 = new Station(UUID.fromString("4181c609-7ed5-473e-befe-8013cd75c24c"), "Eeklo");
			stationRepository.save(station08);
			Station station09 = new Station(UUID.fromString("7b8d9768-7310-4b7d-b6e4-f58abbeac63e"), "Wondelgem");
			stationRepository.save(station09);
			Station station10 = new Station(UUID.fromString("5514be96-c4f1-4dd7-ace4-ac5cbd29c17d"), "Oudenaarde");
			stationRepository.save(station10);
			Station station11 = new Station(UUID.fromString("5b659978-1c39-4372-97ec-a6e4d1418ef3"), "Zottegem");
			stationRepository.save(station11);
			Station station12 = new Station(UUID.fromString("b44c17fc-6df1-4808-83d6-838f5637c9c7"), "Denderleeuw");
			stationRepository.save(station12);
			Station station13 = new Station(UUID.fromString("05cce0f7-1409-4224-926a-db3b4c4a8ce5"), "Brussel-Zuid");
			stationRepository.save(station13);

			Connection con01 = new Connection(station01, station02, 10L, 120);
			connectionRepository.save(con01);
			Connection con02 = new Connection(station01, station05, 28L, 120);
			connectionRepository.save(con02);
			Connection con03 = new Connection(station01, station06, 14L, 120);
			connectionRepository.save(con03);
			Connection con04 = new Connection(station01, station08, 29L, 120);
			connectionRepository.save(con04);
			Connection con05 = new Connection(station01, station11, 38L, 120);
			connectionRepository.save(con05);
			Connection con06 = new Connection(station01, station12, 42L, 120);
			connectionRepository.save(con06);
			
			Connection con07 = new Connection(station02, station09, 15L, 120);
			connectionRepository.save(con07);
			Connection con08 = new Connection(station02, station11, 28L, 120);
			connectionRepository.save(con08);
			
			Connection con09 = new Connection(station03, station04, 17L, 120);
			connectionRepository.save(con09);
			
			Connection con10 = new Connection(station04, station07, 36L, 120);
			connectionRepository.save(con10);
			
			Connection con11 = new Connection(station06, station07, 19L, 120);
			connectionRepository.save(con11);
			Connection con12 = new Connection(station06, station10, 27L, 120);
			connectionRepository.save(con12);

			Connection con13 = new Connection(station08, station09, 22L, 120);
			connectionRepository.save(con13);

			Connection con14 = new Connection(station11, station12, 20L, 120);
			connectionRepository.save(con14);

			Connection con15 = new Connection(station12, station13, 38L, 120);
			connectionRepository.save(con15);

			Route route01 = new Route("Kortrijk - De Pinte");
			RouteConnection routeCon01 = new RouteConnection(route01, station03, con09.getId());
			RouteConnection routeCon02 = new RouteConnection(route01, station04, con10.getId());
			RouteConnection routeCon04 = new RouteConnection(route01, station07, con11.getId());
			RouteConnection routeCon03 = new RouteConnection(route01, station06, null);
			
			Route route02 = new Route("Kortrijk - Deinze");
			RouteConnection routeCon05 = new RouteConnection(route02, station03, con09.getId());
			RouteConnection routeCon06 = new RouteConnection(route02, station04, con10.getId());
			RouteConnection routeCon07 = new RouteConnection(route02, station07, null);
			
			Route route03 = new Route("Brussel-Zuid - Gent-Sint-Pieters");
			RouteConnection routeCon08 = new RouteConnection(route03, station13, con15.getId());
			RouteConnection routeCon09 = new RouteConnection(route03, station12, con14.getId());
			RouteConnection routeCon10 = new RouteConnection(route03, station11, con08.getId());
			RouteConnection routeCon11 = new RouteConnection(route03, station02, con01.getId());
			RouteConnection routeCon12 = new RouteConnection(route03, station01, null);

			routeRepository.save(route01);
			routeRepository.save(route02);
			routeRepository.save(route03);

			logger.info("Station01: " + station01.toString());
			logger.info("Station02: " + station02.toString());
			logger.info("Station03: " + station03.toString());
			logger.info("Station04: " + station04.toString());
			logger.info("Station05: " + station05.toString());
		};
	}
}
