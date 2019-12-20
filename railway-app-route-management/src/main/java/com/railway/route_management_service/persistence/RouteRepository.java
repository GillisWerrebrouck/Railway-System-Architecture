package com.railway.route_management_service.persistence;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.helpers.Constants;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, Long> {
	@Query("MATCH (r:Route)-[u:" + Constants.ROUTE_STATION_RELATIONSHIP + "]-(s:Station) WHERE ID(u)={id} DELETE u")
	void deleteRouteStationRelationshipById(Long id);
	
	@Query("MATCH p=(r1:Route)-[rc1:USES_STATION]-(x:Station)-[c:CONNECTED_WITH*]-(y:Station)-[rc2:USES_STATION]-(r2:Route)\n" + 
			"WHERE x.stationId = {startStationId} AND y.stationId = {endStationId} AND r1=r2 AND ALL(z IN nodes(p) WHERE (z)-[:USES_STATION]-() )\n" + 
			"RETURN distinct r1")
	Collection<Route> getRoutes(UUID startStationId, UUID endStationId);
}
