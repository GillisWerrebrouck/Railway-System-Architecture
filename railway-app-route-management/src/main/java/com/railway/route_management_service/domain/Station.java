package com.railway.route_management_service.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.railway.route_management_service.helpers.Constants;

@NodeEntity
public class Station {
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	// UUID as String because UUID is not supported in neo4j
	private String stationId;

	private String name;
	
	@SuppressWarnings("unused")
	private Station() {
		// Empty constructor required as of Neo4j API 2.0.5
	}
	
	public Station(UUID stationId, String name) {
		this.stationId = stationId.toString();
		this.name = name;
	}
	
	// Neo4j doesn't have bidirectional relationships.
	// UNDIRECTED means it will ignore the direction of the relationship when querying.
	@Relationship(type = Constants.INTER_STATION_RELATIONSHIP, direction = Relationship.UNDIRECTED)
	public Set<Connection> connections = new HashSet<Connection>();
	
	public Long getId() {
		return id;
	}

	public String getStationId() {
		return stationId;
	}
	
	public void setStationId(UUID stationId) {
		this.stationId = stationId.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public Set<Connection> getConnections() {
		return connections;
	}
	
	@Override
	public String toString() {
		return "id: " + this.id + ", name: " + this.name;
	}
}
