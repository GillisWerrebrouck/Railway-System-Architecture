package com.railway.timetable_service.adapters.messaging;

public class Station {
	private Long id;
	// UUID as String because UUID is not supported in neo4j in the Route Management Service
	private String stationId;
	private String name;
	
	public Station(Long id, String stationId, String name) {
		this.id = id;
		this.stationId = stationId;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getStationId() {
		return stationId;
	}
	
	public String getName() {
		return name;
	}
}
