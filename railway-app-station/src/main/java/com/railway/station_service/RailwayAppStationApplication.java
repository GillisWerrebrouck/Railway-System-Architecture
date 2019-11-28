package com.railway.station_service;

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
	public CommandLineRunner populateDatabase (StationRepository stationRepository) {

		return(args)->{
			
			Address a = new Address("Maria Hendrikaplein", "Gent", "Oost-Vlaanderen", "België" );
			ArrayList<Platform> platforms = new ArrayList<>();
			ArrayList<InformationPanel> infopanels1 = new ArrayList<>();
			ArrayList<InformationPanel> infopanels2 = new ArrayList<>();


			Station s = new Station();
			
			Platform p1 = new Platform(7, s);
			Platform p2 = new Platform(8, s);
			InformationPanel panel1 = new InformationPanel("Trein perron 7 heeft vertraging", p1);
			InformationPanel panel2 = new InformationPanel("Trein perron 7 heeft vertraging", p1);
			InformationPanel panel3 = new InformationPanel("Trein perron 8 heeft vertraging", p2);
			infopanels1.add(panel1);
			infopanels1.add(panel2);
			infopanels2.add(panel3);
			p1.setInformationpanels(infopanels1);
			p2.setInformationpanels(infopanels2);
			platforms.add(p1);
			platforms.add(p2);
			
			s.setName("Gent-Sint-Pieters");
			s.setAddress(a);
			s.setPlatforms(platforms);
			stationRepository.save(s);
			
			
			System.out.println("werkt");
		};



	}
}


