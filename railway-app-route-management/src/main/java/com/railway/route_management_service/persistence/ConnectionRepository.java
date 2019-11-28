package com.railway.route_management_service.persistence;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Connection;

@Repository
public interface ConnectionRepository extends Neo4jRepository<Connection, Long> {
	@Query("MATCH (x:Station {name: {station1}}),(y:Station {name: {station2}})\r\n" + 
			"CREATE (x)-[c:CONNECTED_WITH {distance: {distance}, active: true}]->(y)\r\n" + 
			"RETURN ID(c) AS id, x AS stationX, y AS stationY, c.distance AS distance, c.active AS active")
	Connection connectStations(String station1, String station2, Long distance);
}
