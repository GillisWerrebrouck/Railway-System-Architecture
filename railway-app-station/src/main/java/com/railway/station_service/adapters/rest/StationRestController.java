package com.railway.station_service.adapters.rest;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.station_service.adapters.messaging.MessageGateway;
import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.ScheduleItemResponse;
import com.railway.station_service.domain.Station;
import com.railway.station_service.domain.exception.BadRequestException;
import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.ScheduleItemRepository;
import com.railway.station_service.persistence.StationRepository;

@RestController
@RequestMapping("/station")
public class StationRestController {
	private final StationRepository stationRepository;
	private final ScheduleItemRepository scheduleItemRepository;
	private final PlatformRepository platformRepository;
	private MessageGateway gateway;

	@Autowired
	public StationRestController(StationRepository stationRepository, ScheduleItemRepository scheduleItemRepository, PlatformRepository platformRepository, MessageGateway gateway) {
		this.stationRepository = stationRepository;
		this.scheduleItemRepository = scheduleItemRepository;
		this.platformRepository = platformRepository;
		this.gateway = gateway;
	}

	@GetMapping
	public Iterable<Station> getStations(){
		return this.stationRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Station> stationById(@PathVariable UUID id) {
		Station station = stationRepository.getStationById(id);
		if(station != null) {
			return new ResponseEntity<>(station, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/timetable/{id}")
	public Collection<ScheduleItemResponse> stationByTimetableId(@PathVariable Long id) {
		return scheduleItemRepository.getScheduleItemsByTimetableId(id);
	}
	
	@GetMapping("/{id}/platform")
	public ResponseEntity<List<Platform>> platformsByStationId(@PathVariable UUID id) {
		List<Platform> platforms = stationRepository.getPlatformsByStationId(id);
		if(platforms != null) {
			return new ResponseEntity<>(platforms, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/platform/{platformId}")
	public ResponseEntity<Platform> platformByStationIdAndPlatformId(@PathVariable UUID id, @PathVariable Long platformId) {
		List<Platform> platforms = stationRepository.getPlatformsByStationId(id);
		if(platforms != null) {
			for (Platform platform : platforms) {
				if(platform.getId() == platformId) {
					return new ResponseEntity<>(platform, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Station postStation (@RequestBody Station station) {
		if (station.getName() == null) {
			throw new BadRequestException("Missing name property for station");
		}
		try {
			stationRepository.save(station);
			gateway.stationCreated(station);
			return station;
		} catch (Exception e) {
			throw new BadRequestException("Could not create given station: " + e.getMessage());
		}
	}
	
	@PostMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Platform AddPlatformToStation (@RequestBody Platform p, @PathVariable UUID id) {
		if (p.getPlatformNumber() == 0) {
			throw new BadRequestException("Missing platformnumber property for platform");
		}
		try {
			Station s = stationRepository.getStationById(id);
			s.getPlatforms().forEach((plat) -> {
				if(plat.getPlatformNumber() == p.getPlatformNumber()) {
					throw new BadRequestException("platformNumber already exists");
				}
			});
			p.setStation(s);
			Platform pWithid = platformRepository.save(p);
			s.addPlatform(pWithid);
			stationRepository.save(s);
			return pWithid;
		} catch (Exception e) {
			throw new BadRequestException("Could not create given platform: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteStation(@PathVariable UUID id) {
		try {
			stationRepository.deleteById(id);
			gateway.stationDeleted(new Station(id));
		} catch (Exception e) {
			String errorMessage = "Could not delete station with id " + id + ": " + e.getMessage();
			throw new BadRequestException(errorMessage);
		}
	}
	
	@PutMapping
	public void updateStation(@RequestBody Station station) throws BadRequestException {		
		if (station.getId() == null) {
			throw new BadRequestException("No station id specified");
		}
		
		try {
			stationRepository.save(station);
		} catch (Exception e) {
			throw new BadRequestException("Could not update station with id " + station.getId() + ": " + e.getMessage());
		}
	}
}
