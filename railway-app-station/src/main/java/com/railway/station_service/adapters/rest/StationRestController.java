package com.railway.station_service.adapters.rest;

import java.util.ArrayList;
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
import com.railway.station_service.domain.Address;
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
	private final PlatformRepository platformRepository;
	private final ScheduleItemRepository scheduleItemRepository;
	private MessageGateway gateway;

	@Autowired
	public StationRestController(StationRepository stationRepository, PlatformRepository platformRepository, ScheduleItemRepository scheduleItemRepository, MessageGateway gateway) {
		this.stationRepository = stationRepository;
		this.platformRepository = platformRepository;
		this.scheduleItemRepository = scheduleItemRepository;
		this.gateway = gateway;
	}

	// Initial stations shouldn't be created in a bean on startup because 
	// they would get created again if a service is scaled or restarted by kubernetes
	@PostMapping("/init")
	@ResponseStatus(HttpStatus.OK)
	public void initStations(){		
		createStation(UUID.fromString("11018de0-1943-42b2-929d-a707f751f79c"), "Gent-Sint-Pieters", "Koningin Maria Hendrikaplein 1", "Gent", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("a39b1971-fc82-49b2-809a-444105e03c8d"), "Gent-Dampoort", "Oktrooiplein 10", "Gent", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("cf204af6-a407-47ea-af89-f0f989e7bd8a"), "Kortrijk", "Stationsplein 8", "Kortrijk", "West-Vlaanderen", "België");
		createStation(UUID.fromString("73df5f20-33e9-4518-bc23-3cf143c59198"), "Waregem", "Noorderlaan 71", "Waregem", "West-Vlaanderen", "België");
		createStation(UUID.fromString("bc51f294-6823-41cd-be82-d5ba2da4b04d"), "Aalter", "Stationsplein 2", "Aalter", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("d6951807-1fcc-4966-aa30-d4f399685a90"), "De Pinte", "Stationsstraat 25", "De Pinte", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("1454163f-f24f-490f-9e06-f97ffaf008e3"), "Deinze", "Statieplein 4", "Deinze", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("4181c609-7ed5-473e-befe-8013cd75c24c"), "Eeklo", "Koningin Astridplein 1", "Eeklo", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("7b8d9768-7310-4b7d-b6e4-f58abbeac63e"), "Wondelgem", "Wondelgemstationplein 36", "Wondelgem", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("5514be96-c4f1-4dd7-ace4-ac5cbd29c17d"), "Oudenaarde", "Stationplein 1 Gewest", "Oudenaarde", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("5b659978-1c39-4372-97ec-a6e4d1418ef3"), "Zottegem", "Stationsplein 12", "Zottegem", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("b44c17fc-6df1-4808-83d6-838f5637c9c7"), "Denderleeuw", "Stationsplein", "Denderleeuw", "Oost-Vlaanderen", "België");
		createStation(UUID.fromString("05cce0f7-1409-4224-926a-db3b4c4a8ce5"), "Brussel-Zuid", "Fonsnylaan 47b", "Brussel", "Brussels", "België");
	}

	private void createStation(UUID id, String name, String street, String city, String province, String country) {
		if(stationRepository.findByName(name) == null) {
			Address address = new Address(street, city, province, country);
			Station station = new Station(id, name, address);

			Platform p1 = new Platform(1);
			Platform p2 = new Platform(2);
			Platform p3 = new Platform(3);
			p1 = platformRepository.save(p1);
			p2 = platformRepository.save(p2);
			p3 = platformRepository.save(p3);

			station.getPlatforms().add(p1);
			station.getPlatforms().add(p2);
			station.getPlatforms().add(p3);
			station = stationRepository.save(station);

			p1.setStation(station);
			p2.setStation(station);
			p3.setStation(station);
			platformRepository.save(p1);
			platformRepository.save(p2);
			platformRepository.save(p3);
		}
	}

	@GetMapping
	public Iterable<Station> getStations(){
		return this.stationRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Station> stationById(@PathVariable UUID id) {
		Station station = stationRepository.findById(id).orElse(null);
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
