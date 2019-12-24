package com.railway.station_service.adapters.messaging;

public class RouteConnection {
	private Long id;
	private StationOnRoute station;
	private boolean startOfRoute;
	
	public RouteConnection(Long id, StationOnRoute station, boolean startOfRoute) {
		this.id = id;
		this.station = station;
		this.startOfRoute = startOfRoute;
	}
	
	public Long getId() {
		return id;
	}
	
	public StationOnRoute getStation() {
		return station;
	}
	
	public boolean isStartOfRoute() {
		return startOfRoute;
	}
}
