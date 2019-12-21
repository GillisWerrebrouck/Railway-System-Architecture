package com.railway.route_management_service.adapters.messaging;

import java.util.UUID;

public class RouteDetailResponse {

    private String startStationName;
    private String endStationName;
    private double distance;
    private Long ticketId;
    private UUID routeDetailRequestId;

    public RouteDetailResponse(String startStationName, String endStationName, double distance, Long ticketId, UUID routeDetailRequestId) {
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.distance = distance;
        this.ticketId = ticketId;
        this.routeDetailRequestId = routeDetailRequestId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public UUID getRouteDetailRequestId() {
        return routeDetailRequestId;
    }

    public void setRouteDetailRequestId(UUID routeDetailRequestId) {
        this.routeDetailRequestId = routeDetailRequestId;
    }
}
