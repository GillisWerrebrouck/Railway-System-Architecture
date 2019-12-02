package com.railway.ticket_validation_service;

import com.railway.ticket_validation_service.domain.Ticket;
import com.railway.ticket_validation_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class RailwayAppTicketValidationApplication {
	private static Logger logger = LoggerFactory.getLogger(TicketRepository.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppTicketValidationApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabase(TicketRepository ticketRepository) {
		return (args) -> {
			logger.info("Create some tickets to test the repository ...");

			ticketRepository.deleteAll();

			Ticket singleTicket = new Ticket(UUID.fromString("2b3962ae-11e7-11ea-9858-f5f73fbce6aa"),
					"Gent", "Brussel", LocalDate.now(), 1);

			Ticket groupTicket = new Ticket(UUID.fromString("2b3962af-11e7-11ea-9858-cf905512618f"),
					"Oudenaarde", "Brussel", LocalDate.now(), 16);

			ticketRepository.save(singleTicket);
			ticketRepository.save(groupTicket);

			ticketRepository.findAll().forEach(t -> logger.info(t.toString()));
		};
	}
}
