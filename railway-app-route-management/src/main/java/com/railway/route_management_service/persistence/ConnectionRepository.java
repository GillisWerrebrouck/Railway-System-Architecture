package com.railway.route_management_service.persistence;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.helpers.Constants;

@Repository
public interface ConnectionRepository extends Neo4jRepository<Connection, Long> {
	@Query("MATCH p=(x:Station)-[c:" + Constants.INTER_STATION_RELATIONSHIP + "]-(y:Station) WHERE LOWER(x.name) = LOWER({name}) RETURN p")
	Collection<Connection> findConnectionsByStationName(String name);
	
	@Query("MATCH (x:Station {stationId: {stationId1}}),(y:Station {stationId: {stationId2}})\r\n" + 
			"CREATE p=(x)-[c:" + Constants.INTER_STATION_RELATIONSHIP + " {distance: {distance}, maxSpeed: {maxSpeed}, active: {active}}]->(y)\r\n" + 
			"RETURN c")
	Connection connectStations(String stationId1, String stationId2, Long distance, double maxSpeed, boolean active);

	@Query("MATCH p =(x:Station)-[:" + Constants.INTER_STATION_RELATIONSHIP + "* { active:true }]-(y:Station)\r\n" + 
			"WHERE LOWER(x.name) = LOWER({startStation}) AND LOWER(y.name) = LOWER({endStation})\r\n" + 
			"RETURN p\r\n" +
			"ORDER BY reduce(distance = 0, connection IN relationships(p) | distance + connection.distance) LIMIT 1")
	Collection<Connection> findShortestPath(String startStation, String endStation);

	@Query("MATCH p=(r:Route)-[u:" + Constants.ROUTE_STATION_RELATIONSHIP + "]-(s1:Station)-[c:" + Constants.INTER_STATION_RELATIONSHIP + "]-(s2:Station) WHERE ID(r)={id} AND u.connectionId=ID(c) RETURN p")
	Collection<Connection> findRouteById(Long id);
	
	@Query("MATCH p=(r1:Route)-[rc1:USES_STATION]-(x:Station)-[c:CONNECTED_WITH]-(y:Station)\n" + 
			"WHERE ID(r1)={id} AND rc1.connectionId=ID(c)\n" + 
			"RETURN p")
	Collection<Connection> findConnectionsByRouteId(Long id);
}
