package com.railway.route_management_service.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

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
	
    public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}
}
