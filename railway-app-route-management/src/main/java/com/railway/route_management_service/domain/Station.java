package com.railway.route_management_service.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Station {
	@Id
	@GeneratedValue 
	private Long id;
	
	private String name;
	
	@SuppressWarnings("unused")
	private Station() {
		// Empty constructor required as of Neo4j API 2.0.5
	}
	
	public Station(String name) {
		this.name = name;
	}
	
	// Neo4j doesn't have bi-directional relationships.
	// UNDIRECTED means it will ignore the direction of the relationship when querying.
	@Relationship(type = "CONNECTED_WITH", direction = Relationship.UNDIRECTED)
	public Set<Connection> connections = new HashSet<Connection>();
	
	public Set<Connection> getConnections() {
		return connections;
	}
	
	public String toString() {
		return this.name + ": connected stations => "
			+ Optional.ofNullable(this.connections).orElse(
					Collections.emptySet()).stream()
						.map((c) -> c.getStationY().getName())
						.collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
