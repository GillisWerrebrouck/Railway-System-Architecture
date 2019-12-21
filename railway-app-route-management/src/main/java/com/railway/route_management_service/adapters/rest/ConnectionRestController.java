package com.railway.route_management_service.adapters.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.railway.route_management_service.adapters.messaging.RouteUsageResponse;
import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.RouteService;
import com.railway.route_management_service.domain.exception.BadRequestException;
import com.railway.route_management_service.domain.exception.QueryFailedException;
import com.railway.route_management_service.domain.exception.SelfReferentialNodeException;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network/connection")
public class ConnectionRestController extends RouteRestController implements UpdateConnectionListener {
	private static Logger logger = LoggerFactory.getLogger(RouteRestController.class);
	private final Map<Long, DeferredResult<Connection>> deferredResults;
	
	public ConnectionRestController(StationRepository stationRepository, ConnectionRepository connectionRepository, RouteRepository routeRepository, RouteConnectionRepository routeConnectionRepository, RouteService routeService) {
		super(stationRepository, connectionRepository, routeRepository, routeConnectionRepository, routeService);
		this.deferredResults = new HashMap<>(10);
	}
	
	@PostConstruct
	private void registerListener() {
		routeService.registerUpdateConnectionListener(this);
	}

	@GetMapping
	public Iterable<Connection> getRailwayNetwork() {
		return this.connectionRepository.findAll();
	}

	@GetMapping("/{id}")
	public Connection getConnectionById(@PathVariable("id") Long id) {
		return this.connectionRepository.findById(id).orElse(null);
	}

	// create a new (direct) connection between two stations
	@PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void connectStations(@RequestBody Connection connection) throws SelfReferentialNodeException, QueryFailedException {
		if (connection.getStationX().getStationId().compareTo(connection.getStationY().getStationId()) == 0) {
			String errorMessage = "Self-referential nodes are not allowed";
			throw new SelfReferentialNodeException(errorMessage);
		}
		
		try {
			this.connectionRepository.connectStations(connection.getStationX().getStationId(), connection.getStationY().getStationId(), connection.getDistance(), connection.getMaxSpeed(), connection.isActive());
		} catch (Exception e) {
			String errorMessage = "Connection between " + connection.getStationX().getStationId() + " and " + connection.getStationY().getStationId() + " could not be created";
			throw new QueryFailedException(errorMessage);
		}
	}

	// Infrabel will use this endpoint to update a connection
	// only to update the active state of a connection
	@PutMapping
	public DeferredResult<Connection> getConnectionById(@RequestBody Connection connection) throws BadRequestException {
		DeferredResult<Connection> deferredResult = new DeferredResult<>(10000l);
		
		deferredResult.onTimeout(() -> {
			deferredResult.setErrorResult("Request timeout occurred");
		});
		
		this.deferredResults.put(connection.getId(), deferredResult);
		
		if (connection.getId() == null) {
			String errorMessage = "No connection id specified";
			throw new BadRequestException(errorMessage);
		}
		
		// setting a connection active is always allowed 
		if (connection.isActive()) {
			RouteUsageResponse routeUsage = new RouteUsageResponse(connection.getId(), false, true);
			routeService.updateConnection(routeUsage);
		} else {
			routeService.checkRouteUsage(connection.getId());
		}
		
		return deferredResult;
	}
	
	private void performConnectionUpdateResponse(RouteUsageResponse response) {
		DeferredResult<Connection> deferredResult = this.deferredResults.remove(response.getConnectionId());
		if (deferredResult != null && !deferredResult.isSetOrExpired()) {
			Connection connection = connectionRepository.findById(response.getConnectionId()).orElse(null);
			deferredResult.setResult(connection);
		} else {
			logger.info("defereredResult: " + deferredResult);
		}
	}

	@Override
	public void onResult(RouteUsageResponse response) {
		this.performConnectionUpdateResponse(response);
	}
}
