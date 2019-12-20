package com.railway.timetable_service.adapters.messaging;

public class RouteConnection {
	private Long id;
	private Station station;
	private boolean startOfRoute;
	
	@SuppressWarnings("unused")
	private RouteConnection() {}
	
	public RouteConnection(Long id, Station station, boolean startOfRoute) {
		this.id = id;
		this.station = station;
		this.startOfRoute = startOfRoute;
	}
	
	public Long getId() {
		return id;
	}
	
	public Station getStation() {
		return station;
	}
	
	public boolean isStartOfRoute() {
		return startOfRoute;
	}
}
