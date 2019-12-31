package com.railway.delay_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.railway.delay_service.adapters.messaging.Channels;

@EnableBinding(Channels.class)
@SpringBootApplication
public class RailwayAppDelayApplication {
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppDelayApplication.class, args);
	}
}
