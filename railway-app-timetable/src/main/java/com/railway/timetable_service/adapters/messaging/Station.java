package com.railway.timetable_service.adapters.messaging;

public class Station {
	// UUID as String because UUID is not supported in neo4j in the Route Management Service
	private String id;
	private String name;
	
	public Station(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
