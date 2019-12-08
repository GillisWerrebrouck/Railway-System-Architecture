package com.railway.timetable_service.adapters.messaging;

public class RouteConnection {
	private Long id;
	private Station station;
	private boolean isStartOfRoute;
	
	public RouteConnection(Long id, Station station, boolean isStartOfRoute) {
		this.id = id;
		this.station = station;
		this.isStartOfRoute = isStartOfRoute;
	}
	
	public Long getId() {
		return id;
	}
	
	public Station getStation() {
		return station;
	}
	
	public boolean isStartOfRoute() {
		return isStartOfRoute;
	}
}
