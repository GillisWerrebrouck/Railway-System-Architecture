package com.railway.route_management_service.persistence;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Route;
import com.railway.route_management_service.helpers.Constants;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, Long> {
	@Query("MATCH (r:Route)-[u:" + Constants.ROUTE_STATION_RELATIONSHIP + "]-(s:Station) WHERE ID(u)={id} DELETE u")
	void deleteRouteStationRelationshipById(Long id);
}
