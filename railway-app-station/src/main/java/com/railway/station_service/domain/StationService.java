package com.railway.station_service.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.station_service.persistence.StationRepository;

@Service
public class StationService {
	private static Logger logger = LoggerFactory.getLogger(StationService.class);
	StationRepository stationRepository;
	
	@Autowired
	public StationService(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}
	
	public void reserveStation(UUID stationId, Long timetableId, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
		
	}
}
