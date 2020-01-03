package com.railway.route_management_service.persistence;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.RouteConnection;
import com.railway.route_management_service.helpers.Constants;

@Repository
public interface RouteConnectionRepository extends Neo4jRepository<RouteConnection, Long> {
	@Query("MATCH (r:Route),(s:Station) WHERE ID(r)={routeId} AND ID(s)={stationId}\r\n" + 
			"CREATE p=(r)-[u:" + Constants.ROUTE_STATION_RELATIONSHIP + " {connectionId: {connectionId}, isStartOfRoute: {isStartOfRoute}}]->(s)\r\n" + 
			"RETURN COUNT(u)")
	int createRouteStationRelationship(Long routeId, Long stationId, Long connectionId, boolean isStartOfRoute);
}
