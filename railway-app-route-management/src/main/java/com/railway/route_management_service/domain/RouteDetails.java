package com.railway.route_management_service.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class RouteDetails {
    private String departureStation;
    private String arrivalStation;
    private double distance;

    @SuppressWarnings("unused")
	private RouteDetails(){}

    public RouteDetails(String departureStation, String arrivalStation, double distance) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.distance = distance;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
