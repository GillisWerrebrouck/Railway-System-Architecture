package com.railway.timetable_service.adapters.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TimetableItemRestAdapter {
	private static Logger logger = LoggerFactory.getLogger(TimetableItemRestAdapter.class);
	
	public String getRouteName(Long routeId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:2000/network/route";
		ResponseEntity<String> routeResponse = restTemplate.getForEntity(url + "/" + routeId.toString(), String.class);
		String routeName = null;
		
		if(routeResponse.getStatusCode().compareTo(HttpStatus.OK) == 0) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root;
			try {
				root = mapper.readTree(routeResponse.getBody());
				routeName = root.path("name").asText();
			} catch (JsonProcessingException e) {
				logger.debug(e.getMessage());
			}
		}
		return routeName;
	}
}
