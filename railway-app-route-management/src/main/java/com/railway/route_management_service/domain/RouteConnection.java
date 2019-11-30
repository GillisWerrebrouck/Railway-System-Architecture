package com.railway.route_management_service.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.railway.route_management_service.helpers.Constants;

@RelationshipEntity(type = Constants.ROUTE_STATION_RELATIONSHIP)
public class RouteConnection {
	@Id
	@GeneratedValue
	@JsonProperty
    private Long id;

	@StartNode
    private Route route;
 
    @EndNode
    private Station station;

    private Long connectionId;

	@SuppressWarnings("unused")
	private RouteConnection() {
		// Empty constructor required as of Neo4j API 2.0.5
	}
	
	public RouteConnection(Route route, Station station, Long connectionId) {
		this.route = route;
		this.station = station;
		this.connectionId = connectionId;
	}
	
	public Long getId() {
		return id;
	}
	
	@JsonIgnore
	public Route getRoute() {
		return route;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setStation(Station station) {
		this.station = station;
	}
	
	public Long getConnectionId() {
		return connectionId;
	}
	
	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
	}
}
