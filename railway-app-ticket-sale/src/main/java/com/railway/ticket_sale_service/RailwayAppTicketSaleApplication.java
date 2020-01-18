package com.railway.ticket_sale_service;

import com.railway.ticket_sale_service.adapters.messaging.Channels;
import com.railway.ticket_sale_service.domain.Ticket;
import com.railway.ticket_sale_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableBinding(Channels.class)
public class RailwayAppTicketSaleApplication {
//	private static Logger logger = LoggerFactory.getLogger(RailwayAppTicketSaleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppTicketSaleApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabase(TicketRepository ticketRepository) {
		return (args) -> {
//			logger.info("Create some tickets to test the repository ...");
//
//			ticketRepository.deleteAll();
//
//			Ticket singleTicket1 = new Ticket("Gent", "Brussel", LocalDateTime.now(), 1L, 9.70, 1);
//			Ticket groupTicket1 = new Ticket("Oudenaarde", "Kortrijk", LocalDateTime.now(), 2L, 153.40, 16);
//
//			ticketRepository.save(singleTicket1);
//			ticketRepository.save(groupTicket1);
//
//			ticketRepository.findAll().forEach(ticket -> logger.info(ticket.toString()));
		};
	}
}
