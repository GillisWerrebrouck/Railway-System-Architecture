package com.railway.route_management_service.adapters.rest;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.RouteConnection;
import com.railway.route_management_service.domain.Station;
import com.railway.route_management_service.domain.RouteService;
import com.railway.route_management_service.domain.exception.BadRequestException;
import com.railway.route_management_service.domain.exception.QueryFailedException;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network")
public class RouteRestController {
	protected final StationRepository stationRepository;
	protected final ConnectionRepository connectionRepository;
	protected final RouteRepository routeRepository;
	protected final RouteConnectionRepository routeConnectionRepository;
	protected final RouteService routeService;
	
	@Autowired
	public RouteRestController(StationRepository stationRepository, ConnectionRepository connectionRepository, RouteRepository routeRepository, RouteConnectionRepository routeConnectionRepository, RouteService routeService) {
		this.stationRepository = stationRepository;
		this.connectionRepository = connectionRepository;
		this.routeRepository = routeRepository;
		this.routeConnectionRepository = routeConnectionRepository;
		this.routeService = routeService;
	}
	
	// Initial stations/connections/routes/routeConnections shouldn't be created in a bean on startup because 
	// they would get created again if a service is scaled or restarted by kubernetes
	@SuppressWarnings("unused")
	@PostMapping("/init")
	@ResponseStatus(HttpStatus.OK)
	public void initNetwork() {
		stationRepository.deleteAll();
		routeRepository.deleteAll();

		Station station01 = new Station(UUID.fromString("11018de0-1943-42b2-929d-a707f751f79c"), "Gent-Sint-Pieters");
		stationRepository.save(station01);
		Station station02 = new Station(UUID.fromString("a39b1971-fc82-49b2-809a-444105e03c8d"), "Gent-Dampoort");
		stationRepository.save(station02);
		Station station03 = new Station(UUID.fromString("cf204af6-a407-47ea-af89-f0f989e7bd8a"), "Kortrijk");
		stationRepository.save(station03);
		Station station04 = new Station(UUID.fromString("73df5f20-33e9-4518-bc23-3cf143c59198"), "Waregem");
		stationRepository.save(station04);
		Station station05 = new Station(UUID.fromString("bc51f294-6823-41cd-be82-d5ba2da4b04d"), "Aalter");
		stationRepository.save(station05);
		Station station06 = new Station(UUID.fromString("d6951807-1fcc-4966-aa30-d4f399685a90"), "De Pinte");
		stationRepository.save(station06);
		Station station07 = new Station(UUID.fromString("1454163f-f24f-490f-9e06-f97ffaf008e3"), "Deinze");
		stationRepository.save(station07);
		Station station08 = new Station(UUID.fromString("4181c609-7ed5-473e-befe-8013cd75c24c"), "Eeklo");
		stationRepository.save(station08);
		Station station09 = new Station(UUID.fromString("7b8d9768-7310-4b7d-b6e4-f58abbeac63e"), "Wondelgem");
		stationRepository.save(station09);
		Station station10 = new Station(UUID.fromString("5514be96-c4f1-4dd7-ace4-ac5cbd29c17d"), "Oudenaarde");
		stationRepository.save(station10);
		Station station11 = new Station(UUID.fromString("5b659978-1c39-4372-97ec-a6e4d1418ef3"), "Zottegem");
		stationRepository.save(station11);
		Station station12 = new Station(UUID.fromString("b44c17fc-6df1-4808-83d6-838f5637c9c7"), "Denderleeuw");
		stationRepository.save(station12);
		Station station13 = new Station(UUID.fromString("05cce0f7-1409-4224-926a-db3b4c4a8ce5"), "Brussel-Zuid");
		stationRepository.save(station13);

		Connection con01 = new Connection(station01, station02, 10L, 120);
		connectionRepository.save(con01);
		Connection con02 = new Connection(station01, station05, 28L, 120);
		connectionRepository.save(con02);
		Connection con03 = new Connection(station01, station06, 14L, 120);
		connectionRepository.save(con03);
		Connection con04 = new Connection(station01, station08, 29L, 120);
		connectionRepository.save(con04);
		Connection con05 = new Connection(station01, station11, 38L, 120);
		connectionRepository.save(con05);
		Connection con06 = new Connection(station01, station12, 42L, 120);
		connectionRepository.save(con06);
		
		Connection con07 = new Connection(station02, station09, 15L, 120);
		connectionRepository.save(con07);
		Connection con08 = new Connection(station02, station11, 28L, 120);
		connectionRepository.save(con08);
		
		Connection con09 = new Connection(station03, station04, 17L, 120);
		connectionRepository.save(con09);
		
		Connection con10 = new Connection(station04, station07, 36L, 120);
		connectionRepository.save(con10);
		
		Connection con11 = new Connection(station06, station07, 19L, 120);
		connectionRepository.save(con11);
		Connection con12 = new Connection(station06, station10, 27L, 120);
		connectionRepository.save(con12);

		Connection con13 = new Connection(station08, station09, 22L, 120);
		connectionRepository.save(con13);

		Connection con14 = new Connection(station11, station12, 20L, 120);
		connectionRepository.save(con14);

		Connection con15 = new Connection(station12, station13, 38L, 120);
		connectionRepository.save(con15);

		Route route01 = new Route("Kortrijk - De Pinte");
		RouteConnection routeCon01 = new RouteConnection(route01, station03, true, con09.getId());
		RouteConnection routeCon02 = new RouteConnection(route01, station04, false, con10.getId());
		RouteConnection routeCon04 = new RouteConnection(route01, station07, false, con11.getId());
		RouteConnection routeCon03 = new RouteConnection(route01, station06, false, null);
		
		Route route02 = new Route("Kortrijk - Deinze");
		RouteConnection routeCon05 = new RouteConnection(route02, station03, true, con09.getId());
		RouteConnection routeCon06 = new RouteConnection(route02, station04, false, con10.getId());
		RouteConnection routeCon07 = new RouteConnection(route02, station07, false, null);
		
		Route route03 = new Route("Brussel-Zuid - Gent-Sint-Pieters");
		RouteConnection routeCon08 = new RouteConnection(route03, station13, true, con15.getId());
		RouteConnection routeCon09 = new RouteConnection(route03, station12, false, con14.getId());
		RouteConnection routeCon10 = new RouteConnection(route03, station11, false, con08.getId());
		RouteConnection routeCon11 = new RouteConnection(route03, station02, false, con01.getId());
		RouteConnection routeCon12 = new RouteConnection(route03, station01, false, null);

		Route route04 = new Route("Deinze - Zottegem");
		RouteConnection routeCon13 = new RouteConnection(route04, station07, true, con11.getId());
		RouteConnection routeCon14 = new RouteConnection(route04, station06, false, con03.getId());
		RouteConnection routeCon15 = new RouteConnection(route04, station01, false, con05.getId());
		RouteConnection routeCon16 = new RouteConnection(route04, station11, false, null);
		
		Route route05 = new Route("Gent-Sint-Pieters - Brussel-Zuid");
		RouteConnection routeCon17 = new RouteConnection(route05, station01, true, con01.getId());
		RouteConnection routeCon18 = new RouteConnection(route05, station02, false, con08.getId());
		RouteConnection routeCon19 = new RouteConnection(route05, station11, false, con14.getId());
		RouteConnection routeCon20 = new RouteConnection(route05, station12, false, con15.getId());
		RouteConnection routeCon21 = new RouteConnection(route05, station13, false, null);

		routeRepository.save(route01);
		routeRepository.save(route02);
		routeRepository.save(route03);
		routeRepository.save(route04);
		routeRepository.save(route05);
	}
	
	@GetMapping("/route/all")
	public Iterable<Route> getAllRoutes() {
		return this.routeRepository.findAll();
	}
	
	@GetMapping("/route")
	public Collection<Route> getRoutes(@RequestParam UUID startStationId, @RequestParam UUID endStationId) {
		Iterator<Route> routes = this.routeRepository.getRoutes(startStationId, endStationId).iterator();
		Collection<Route> suitableRoutes = new ArrayList<>();
		
		while(routes.hasNext()) {
			Route route = routes.next();
			Collection<Connection> connections = this.connectionRepository.findConnectionsByRouteId(route.getId());
			
			Station station = getStart(route);
			UUID previousStationId = UUID.fromString(station.getStationId());
			boolean startOfRouteFound = false;
			boolean isRouteSuitable = true;
			
			// loop over all stations on the route
			while(connections.size() > 0) {
				// check if the requested end station occurs after the requested start station on this route
				// this filters out routes that are in the requested direction (start end --> end station)
				if(station.getStationId().compareTo(startStationId.toString()) == 0) {
					startOfRouteFound = true;
				} else if (!startOfRouteFound && station.getStationId().compareTo(endStationId.toString()) == 0) {
					routes.remove();
					isRouteSuitable = false;
					break;
				}
				
				// get the next station on this route
				Connection connection = getNextRoutePart(connections, previousStationId);
				if(connection.getStationX().getStationId().compareTo(previousStationId.toString()) == 0) {
					station = connection.getStationY();
				} else {
					station = connection.getStationX();
				}
				connections.removeIf(r -> r.getId() == connection.getId());
				previousStationId=UUID.fromString(station.getStationId());
			}
			
			// only routes in the correct direction are returned
			if(isRouteSuitable) {
				suitableRoutes.add(route);
			}
		}
		return suitableRoutes;
	}

	private Station getStart(Route route) {
		// find the start station of the route
		Station startStation = null;
		for(RouteConnection r : route.getRouteConnections()) {
			if(r.isStartOfRoute()) {
				startStation = r.getStation();
				break;
			}
		}
		return startStation;
	}
	
	private Connection getNextRoutePart(Collection<Connection> connections, UUID previousStationId) {
		Connection connection = null;
		for(Connection c : connections) {
			if(c.getStationX().getStationId().compareTo(previousStationId.toString()) == 0 || 
					c.getStationY().getStationId().compareTo(previousStationId.toString()) == 0) {
				connection = c;
			}
		}
		return connection;
	}
	
	// get the shortest (distance) route between two station nodes
	@GetMapping("/route/shortest")
	public Collection<Connection> getShortestPath(@RequestParam String startStation, @RequestParam String endStation) {
		return this.connectionRepository.findShortestPath(startStation, endStation);
	}

	// get a predefined route by id
	@GetMapping("/route/{id}")
	public Optional<Route> getRouteById(@PathVariable Long id) {
		return this.routeRepository.findById(id);
	}

	// get the connections of a predefined route by route id
	@GetMapping("/route/{id}/connections")
	public Collection<Connection> getRouteConnectionsByRouteId(@PathVariable Long id) {
		Route route = this.routeRepository.findById(id).orElse(null);
		
		Collection<Connection> orderedConnections = new ArrayList<>();
		
		Station station = getStart(route);
		UUID previousStationId = UUID.fromString(station.getStationId());
		Collection<Connection> connections = this.connectionRepository.findConnectionsByRouteId(route.getId());
		
		// loop over all stations on the route
		while(connections.size() > 0) {
			// get the next station on this route
			Connection connection = getNextRoutePart(connections, previousStationId);
			orderedConnections.add(connection);
			if(connection.getStationX().getStationId().compareTo(previousStationId.toString()) == 0) {
				station = connection.getStationY();
			} else {
				station = connection.getStationX();
			}
			connections.removeIf(r -> r.getId() == connection.getId());
			previousStationId=UUID.fromString(station.getStationId());
		}
		
		return orderedConnections;
	}

	// get the connections of a predefined route by route id
	@GetMapping("/route/{id}/stations")
	public Collection<Station> getRouteStationsByRouteId(@PathVariable Long id) {
		Route route = this.routeRepository.findById(id).orElse(null);
		
		Collection<Station> orderedStations = new ArrayList<>();
		
		Station station = getStart(route);
		orderedStations.add(station);
		UUID previousStationId = UUID.fromString(station.getStationId());
		Collection<Connection> connections = this.connectionRepository.findConnectionsByRouteId(route.getId());
		
		// loop over all stations on the route
		while(connections.size() > 0) {
			// get the next station on this route
			Connection connection = getNextRoutePart(connections, previousStationId);
			if(connection.getStationX().getStationId().compareTo(previousStationId.toString()) == 0) {
				station = connection.getStationY();
				orderedStations.add(station);
			} else {
				station = connection.getStationX();
				orderedStations.add(station);
			}
			connections.removeIf(r -> r.getId() == connection.getId());
			previousStationId=UUID.fromString(station.getStationId());
		}
		
		return orderedStations;
	}
	
	// create a new route node
	@PostMapping("/route")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createRoute(@RequestBody Route route) throws QueryFailedException {
		try {
			if (route.getName().compareTo("") == 0) {
				throw new Exception("Missing name attribute for route node");
			}
			this.routeRepository.save(route);
		} catch (Exception e) {
			String errorMessage = "Route with name \"" + route.getName() + "\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
	}

	@PutMapping("/route")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateRoute(@RequestBody Route route) throws BadRequestException, QueryFailedException {
		try {
			if (route.getName().compareTo("") == 0) {
				throw new BadRequestException("Missing name attribute for route node");
			}
			this.routeRepository.save(route);
		} catch (Exception e) {
			String errorMessage = "Route with name \"" + route.getName() + "\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
	}
	
	// create a new route-station relationship
	@PostMapping("/route/{id}/part")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createRoutePart(@RequestBody RouteConnection routeConnection, @PathVariable Long id) throws QueryFailedException {
		try {
			this.routeConnectionRepository.createRouteStationRelationship(id, routeConnection.getStation().getId(), routeConnection.getConnectionId());
		} catch (Exception e) {
			String errorMessage = "Route-station relationship between route \"" + id + "\" and station \""
								  + routeConnection.getStation().getId() +"\" could not be created: " + e.getMessage();
			throw new QueryFailedException(errorMessage);
		}
	}
	
	@DeleteMapping("/route/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteRouteById(@PathVariable Long id) throws QueryFailedException {
		try {
			this.routeRepository.deleteById(id);
		} catch (Exception e) {
			String errorMessage = "Could not delete route with id " + id.toString();
			throw new QueryFailedException(errorMessage);
		}
	}
	
	@DeleteMapping("/route/part/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteRouteStationRelationshipById(@PathVariable Long id) throws QueryFailedException {
		try {
			this.routeRepository.deleteRouteStationRelationshipById(id);
		} catch (Exception e) {
			String errorMessage = "Could not delete route part with id " + id.toString();
			throw new QueryFailedException(errorMessage);
		}
	}
}
