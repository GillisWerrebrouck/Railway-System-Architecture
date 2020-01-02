package com.railway.route_management_service.persistence;

import com.railway.route_management_service.domain.RouteDetails;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.helpers.Constants;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, Long> {
	@Query("MATCH (r:Route)-[u:" + Constants.ROUTE_STATION_RELATIONSHIP + "]-(s:Station) WHERE ID(u)={id} DELETE u")
	void deleteRouteStationRelationshipById(Long id);
	
	@Query("MATCH p=(r1:Route)-[rc1:USES_STATION]-(x:Station)-[c:CONNECTED_WITH*]-(y:Station)-[rc2:USES_STATION]-(r2:Route)\n" + 
			"WHERE x.stationId = {startStationId} AND y.stationId = {endStationId} AND r1=r2 AND ALL(z IN nodes(p) WHERE (z)-[:USES_STATION]-() )\n" + 
			"RETURN distinct r1")
	Collection<Route> getRoutes(UUID startStationId, UUID endStationId);

	@Query("MATCH p=(:Route)-[:USES_STATION]-(x:Station)-[c:CONNECTED_WITH* { active:true }]-(y:Station)-[:USES_STATION]-(:Route)\n" +
			"WHERE x.stationId={stationXId} AND y.stationId={stationYId}\n" +
			"RETURN x.na"
			+ "me as departureStation, y.name as arrivalStation,\n" +
			"       reduce(distance = 0, connection IN c | distance + connection.distance) AS distance\n" +
			"ORDER BY distance LIMIT 1")
	RouteDetails getRouteDetails(UUID stationXId, UUID stationYId);
	
	@Query("MATCH (r:Route)-[n:USES_STATION {connectionId: {connectionId}}]-(x:Station) RETURN DISTINCT r")
	Collection<Route> getAllRoutesByConnectionId(Long connectionId);
	
	@Query("CREATE (r:Route {name: {name}}) RETURN r")
	Route createRoute(String name);
}
