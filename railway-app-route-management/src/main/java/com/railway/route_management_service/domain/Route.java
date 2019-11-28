package com.railway.route_management_service.domain;

import java.util.Collection;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

//import org.neo4j.ogm.annotation.GeneratedValue;
//import org.neo4j.ogm.annotation.Id;

public class Route {
//	@Id
//	@GeneratedValue
//	private Long id;
//
//	private Station departureStation;
//	private Station arrivalStation;
	private Set<Connection> stations;
	private Long distance;

	private Route() {
		// Empty constructor required as of Neo4j API 2.0.5
	}
	
	public Route(Set<Connection> stations, Long distance) {
		this.stations = stations;
		this.distance = distance;
	}
//	
//	public Route(Station departureStation, Station arrivalStation, Long distance) {
//		this.departureStation = departureStation;
//		this.arrivalStation = arrivalStation;
//		this.distance = distance;
//	}
//	
//	public Route(ArrayList<Station> stations) {
//		this.stations = stations;
//	}
	
//	public Station getDepartureStation() {
//		return (stations.size()>0) ? stations.get(0) : null;
//	}
//	
//	public Station getArrivalStation() {
//		return (stations.size()>0) ? stations.get(stations.size()-1) : null;
//	}
	
//	public Station getArrivalStation() {
//		return arrivalStation;
//	}
//	
//	public void setArrivalStation(Station arrivalStation) {
//		this.arrivalStation = arrivalStation;
//	}
//	
//	public Station getDepartureStation() {
//		return departureStation;
//	}
//	
//	public void setDepartureStation(Station departureStation) {
//		this.departureStation = departureStation;
//	}
	
	public Set<Connection> getStations() {
		return stations;
	}
	
	public void setStations(Set<Connection> stations) {
		this.stations = stations;
	}
	
	public Long getDistance() {
		return distance;
	}
	
	public void setDistance(Long distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return "distance: " + distance + ", " + stations.toString();
	}
}
