package com.railway.route_management_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.RouteConnection;
import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@SpringBootApplication
public class RailwayAppRouteManagementApplication {
	private static Logger logger = LoggerFactory.getLogger(RailwayAppRouteManagementApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppRouteManagementApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateDatabase(StationRepository stationRepository, RouteRepository routeRepository) {
		return (args) ->{
			logger.info("Populating graph database with test data ...");

			stationRepository.deleteAll();
			routeRepository.deleteAll();
			
			Station station01 = new Station("Gent-Sint-Pieters");
			Station station02 = new Station("Gent-Dampoort");
			Station station03 = new Station("Kortrijk");
			Station station04 = new Station("Waregem");
			Station station05 = new Station("Gentbrugge");
			Station station06 = new Station("De Pinte");
			Station station07 = new Station("Deinze");

			Connection con01 = new Connection(station01, station02, 10L);
			Connection con02 = new Connection(station01, station05, 18L);
			Connection con03 = new Connection(station01, station06, 22L);
			Connection con04 = new Connection(station01, station07, 27L);

			Connection con05 = new Connection(station02, station05, 20L);
			Connection con06 = new Connection(station02, station06, 28L);
			Connection con07 = new Connection(station02, station07, 32L);

			Connection con08 = new Connection(station03, station04, 17L);
			Connection con09 = new Connection(station03, station05, 23L);

			Connection con10 = new Connection(station04, station06, 29L);
			Connection con11 = new Connection(station04, station07, 46L);

			Connection con12 = new Connection(station06, station07, 21L);
						
			stationRepository.save(station01);
			stationRepository.save(station02);
			stationRepository.save(station03);
			stationRepository.save(station04);
			stationRepository.save(station05);
			stationRepository.save(station06);
			stationRepository.save(station07);
			
			
			Route route01 = new Route("Kortrijk - Deinze");
			RouteConnection routeCon01 = new RouteConnection(route01, station03, con08.getId());
			RouteConnection routeCon02 = new RouteConnection(route01, station04, con10.getId());
			RouteConnection routeCon03 = new RouteConnection(route01, station06, con12.getId());
			RouteConnection routeCon04 = new RouteConnection(route01, station07, null);
			
			Route route02 = new Route("Kortrijk - Deinze");
			RouteConnection routeCon05 = new RouteConnection(route02, station03, con08.getId());
			RouteConnection routeCon06 = new RouteConnection(route02, station04, con10.getId());
			RouteConnection routeCon07 = new RouteConnection(route02, station07, null);

			routeRepository.save(route01);
			routeRepository.save(route02);
		};
	}
}
