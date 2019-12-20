package com.railway.timetable_service.adapters.rest;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.railway.timetable_service.adapters.messaging.Route;

@Service
public class TimetableItemRestAdapter {
	private static Logger logger = LoggerFactory.getLogger(TimetableItemRestAdapter.class);
	
	public String getRouteName(Long routeId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:2000/network/route";
		ResponseEntity<String> routeResponse = restTemplate.getForEntity(url + "/" + routeId.toString(), String.class);
		String routeName = null;
		
		if(routeResponse != null && routeResponse.getStatusCode().compareTo(HttpStatus.OK) == 0) {
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
	
	public Collection<Route> getRoutes(UUID startStationId, UUID endStationId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:2000/network/route";
		String endpoint = "?startStationId=" + startStationId.toString() + "&endStationId=" + endStationId.toString();
		ResponseEntity<List<Route>> routesResponse = restTemplate.exchange(url + endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<Route>>() {});
		
		if(routesResponse != null && routesResponse.getStatusCode().compareTo(HttpStatus.OK) == 0 && routesResponse.hasBody()) {
			Collection<Route> routes = routesResponse.getBody();
			return routes;
		}
		
		return null;
	}
	
	public Collection<ScheduleItemResponse> getStationsByTimetableItemId(Long timetableId) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:2002/station/timetable";
		ResponseEntity<List<ScheduleItemResponse>> scheduleItemResponse = restTemplate.exchange(url + "/" + timetableId.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<ScheduleItemResponse>>() {});
		
		if(scheduleItemResponse != null && scheduleItemResponse.getStatusCode().compareTo(HttpStatus.OK) == 0 && scheduleItemResponse.hasBody()) {
			Collection<ScheduleItemResponse> stations = scheduleItemResponse.getBody();
			return stations;
		}
		
		return null;
	}
}
