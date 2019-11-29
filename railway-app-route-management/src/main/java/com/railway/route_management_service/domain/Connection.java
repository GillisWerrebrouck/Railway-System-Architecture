package com.railway.route_management_service.domain;

import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@RelationshipEntity(type = "CONNECTED_WITH")
public class Connection {
	@Id
	@GeneratedValue
    private Long id;

	@StartNode
    private Station stationX;
 
    @EndNode
    private Station stationY;

	private Long distance;
	private boolean active = true;

	@SuppressWarnings("unused")
	private Connection() {
		// Empty constructor required as of Neo4j API 2.0.5
	}
	
	public Connection(Station stationX, Station stationY, Long distance) {
		this.stationX = stationX;
		this.stationY = stationY;
		this.setDistance(distance);

		this.stationX.getConnections().add(this);
		this.stationY.getConnections().add(this);
	}

	public Station getStationX() {
		return stationX;
	}

	public Station getStationY() {
		return stationY;
	}
	
	public void setStationX(Station stationX) {
		this.stationX = stationX;
	}
	
	public void setStationY(Station stationY) {
		this.stationY = stationY;
	}
	
    public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return getDistance() + " - " + getStationX().getName();
	}
}
