package com.railway.route_management_service.domain;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.railway.route_management_service.domain.exception.QueryFailedException;
import com.railway.route_management_service.persistence.StationRepository;

@Service
public class StationService {
	private final StationRepository stationRepository;
	
	public StationService(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	public void createStation(Station station) {
		try {
			this.stationRepository.createStation(UUID.fromString(station.getStationId()), station.getName());
		} catch (Exception e) {
			String errorMessage = "Station with name \"" + station.getName() + "\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
	}

	public void deleteStation(Station station) {
		try {
			this.stationRepository.deleteStationByStationId(station.getStationId());
		} catch (Exception e) {
			String errorMessage = "Could not delete station with id " + station.getId().toString();
			throw new QueryFailedException(errorMessage);
		}
	}
}
