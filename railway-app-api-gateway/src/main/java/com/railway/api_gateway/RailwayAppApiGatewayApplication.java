package com.railway.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import com.railway.api_gateway.adapters.messaging.Channels;

@SpringBootApplication
@EnableBinding(Channels.class)
public class RailwayAppApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(RailwayAppApiGatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				//maintenance service
				.route(r -> r.host("*").and().path("/maintenance/**").uri("http://maintenance-service:2005"))
				//route management service
				.route(r -> r.host("*").and().path("/network/**").uri("http://route-service:2000"))
				//staff service
				.route(r -> r.host("*").and().path("/staff/**").uri("http://staff-service:2006"))
				//station service
				.route(r -> r.host("*").and().path("/station/**").uri("http://station-service:2002"))
				//ticket sale service
				.route(r -> r.host("*").and().path("/ticket/**").uri("http://ticket-sale-service:2007"))
				//ticket validation service
				.route(r -> r.host("*").and().path("/ticket-validation/**").uri("http://ticket-validation-service:2008"))
				//timetable service
				.route(r -> r.host("*").and().path("/timetable/**").uri("http://timetable-service:2001"))
				//train service
				.route(r -> r.host("*").and().path("/train/**").uri("http://train-service:2003"))
				//delay service
				.route(r -> r.host("*").and().path("/delay/**").uri("http://delay-service:2004"))
				.build();
	}
}
