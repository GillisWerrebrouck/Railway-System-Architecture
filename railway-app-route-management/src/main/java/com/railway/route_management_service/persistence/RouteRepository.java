package com.railway.route_management_service.persistence;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.domain.Station;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, Long> {
//	@Query("MATCH p =(x:Station)-[* { active:true }]-(y:Station)\r\n" + 
//			"WHERE x.name = '{startStation}' AND y.name = '{endStation}'\r\n" + 
//			"RETURN nodes(p) AS stations,\r\n" + 
//			"       reduce(distance = 0, connection IN relationships(p) | distance + connection.distance) AS distance\r\n" + 
//			"ORDER BY distance LIMIT 1")
//	Collection<Route> findShortestPath(String startStation, String endStation);
	@Query("MATCH p =(x:Station)-[* { active:true }]-(y:Station)\r\n" + 
			"WHERE x.name = 'Kortrijk' AND y.name = 'Deinze'\r\n" + 
			"RETURN relationships(p) AS stations,\r\n" + 
			"reduce(distance = 0, connection IN relationships(p) | distance + connection.distance) AS distance\r\n" + 
			"ORDER BY distance LIMIT 1")
	Collection<Route> findShortestPath(String startStation, String endStation);
}
