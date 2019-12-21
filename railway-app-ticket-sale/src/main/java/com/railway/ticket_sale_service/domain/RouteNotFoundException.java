package com.railway.ticket_sale_service.domain;

public class RouteNotFoundException extends Exception {

    public RouteNotFoundException(Long routeId){
        super("No route found with id " + routeId);
    }
}
