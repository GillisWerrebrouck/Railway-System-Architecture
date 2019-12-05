package com.railway.timetable_service.adapters.messaging;

public class Station {
	private Long id;
	private String name;
	
	public Station(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
