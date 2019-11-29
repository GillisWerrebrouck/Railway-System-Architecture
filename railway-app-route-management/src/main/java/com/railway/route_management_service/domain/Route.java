package com.railway.route_management_service.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.railway.route_management_service.helpers.Constants;

@NodeEntity
public class Route {
	@Id
	@GeneratedValue 
	@JsonProperty
	private Long id;

	private String name;

	@SuppressWarnings("unused")
	private Route() {
		// Empty constructor required as of Neo4j API 2.0.5
	}
	
	public Route(String name) {
		this.name = name;
	}

	// Neo4j doesn't have bidirectional relationships.
	// UNDIRECTED means it will ignore the direction of the relationship when querying.
	@Relationship(type = Constants.ROUTE_STATION_RELATIONSHIP, direction = Relationship.OUTGOING)
	public Set<Station> stations = new HashSet<Station>();

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Station> getStations() {
		return stations;
	}
	
	public void addStation(Station station) {
		stations.add(station);
	}
}
