package com.railway.route_management_service.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.helpers.Constants;

@Repository
public interface ConnectionRepository extends Neo4jRepository<Connection, Long> {
	@Query("MATCH p=(x:Station)-[c:" + Constants.INTER_STATION_RELATIONSHIP + "]-(y:Station) WHERE LOWER(x.name) = LOWER({name}) RETURN p")
	Collection<Connection> findConnectionsByStationName(String name);
	
	@Query("MATCH (x:Station {name: {station1}}),(y:Station {name: {station2}})\r\n" + 
			"CREATE p=(x)-[c:" + Constants.INTER_STATION_RELATIONSHIP + " {distance: {distance}, active: true}]->(y)\r\n" + 
			"RETURN COUNT(c)")
	int connectStations(String station1, String station2, Long distance);

	@Query("MATCH p =(x:Station)-[:" + Constants.INTER_STATION_RELATIONSHIP + "* { active:true }]-(y:Station)\r\n" + 
			"WHERE LOWER(x.name) = LOWER({startStation}) AND LOWER(y.name) = LOWER({endStation})\r\n" + 
			"RETURN p\r\n" +
			"ORDER BY reduce(distance = 0, connection IN relationships(p) | distance + connection.distance) LIMIT 1")
	List<Connection> findShortestPath(String startStation, String endStation);
}
