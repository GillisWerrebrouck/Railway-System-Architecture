package com.railway.station_service;

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
	public CommandLineRunner populateDatabase (StationRepository stationRepository, PlatformRepository platformRepository, InformationPanelRepository informationPanelRepository) {

		return(args)->{
			
			Address a = new Address("Maria Hendrikaplein", "Gent", "Oost-Vlaanderen", "België" );
			Station s = new Station("Gent-Sint-Pieters", a);
			
			Platform p1 = new Platform(7);
			Platform p2 = new Platform(8);
			
			InformationPanel panel1 = new InformationPanel("Trein perron 7 heeft vertraging");
			InformationPanel panel2 = new InformationPanel("Trein perron 7 heeft vertraging");
			InformationPanel panel3 = new InformationPanel("Trein perron 8 heeft vertraging");

			s.setName("Gent-Sint-Pieters");
			s.setAddress(a);
			s.getPlatforms().add(p1);
			s.getPlatforms().add(p2);
			
			p1.setStation(s);
			p2.setStation(s);
			
			panel1.setPlatform(p1);
			panel2.setPlatform(p1);
			panel3.setPlatform(p2);
			
			p2.getInformationpanels().add(panel3);
			p1.getInformationpanels().add(panel1);
			p1.getInformationpanels().add(panel2);
			
			
			stationRepository.save(s);
			platformRepository.save(p1);
			platformRepository.save(p2);
			informationPanelRepository.save(panel1);
			informationPanelRepository.save(panel2);
			informationPanelRepository.save(panel3);
			
			
			System.out.println("werkt");
		};



	}
}


