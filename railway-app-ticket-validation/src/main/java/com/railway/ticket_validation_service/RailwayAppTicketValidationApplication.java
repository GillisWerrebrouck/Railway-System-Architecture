package com.railway.ticket_validation_service;

import com.railway.ticket_validation_service.adapters.messaging.Channels;
import com.railway.ticket_validation_service.domain.Ticket;
import com.railway.ticket_validation_service.persistence.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableBinding(Channels.class)
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

			Ticket singleTicket = new Ticket(92L, "Gent", "Brussel", LocalDateTime.now(), 1, UUID.randomUUID());
			Ticket groupTicket = new Ticket(99L, "Oudenaarde", "Brussel", LocalDateTime.now(), 16, UUID.randomUUID());

			ticketRepository.save(singleTicket);
			ticketRepository.save(groupTicket);

			ticketRepository.findAll().forEach(ticket -> logger.info(ticket.toString()));
		};
	}
}
