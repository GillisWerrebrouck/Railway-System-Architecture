package com.railway.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RailwayAppApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayAppApiGatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				//delay service
				.route(r -> r.host("*").and().path("/delay/**").uri("http://localhost:2004"))
				//maintenance service
				.route(r -> r.host("*").and().path("/maintenance/**").uri("http://localhost:2005"))
				//route management service
				.route(r -> r.host("*").and().path("/route/**").uri("http://localhost:2000"))
				//staff service
				.route(r -> r.host("*").and().path("/staff/**").uri("http://localhost:2006"))
				//station service
				.route(r -> r.host("*").and().path("/station/**").uri("http://localhost:2002"))
				//ticket sale service
				.route(r -> r.host("*").and().path("/ticket/**").uri("http://localhost:2007"))
				//ticket validation service
				.route(r -> r.host("*").and().path("/ticket-validation/**").uri("http://localhost:2008"))
				//timetable service
				.route(r -> r.host("*").and().path("/timetable/**").uri("http://localhost:2001"))
				//train service
				.route(r -> r.host("*").and().path("/train/**").uri("http://localhost:2003"))
				.build();
	}

}
