package com.railway.route_management_service.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.railway.route_management_service.domain.Route;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, Long> {

}
