package com.railway.ticket_sale_service.adapters.messaging;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RouteDetailResponse {

    private String startStationName;
    private String endStationName;
    private double distance;
    private Long[] ticketIds;
    private UUID routeDetailRequestId;

    public RouteDetailResponse(String startStationName, String endStationName, double distance, Long[] ticketIds, UUID routeDetailRequestId) {
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.distance = distance;
        this.ticketIds = ticketIds;
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

    public Long[] getTicketIds() {
        return ticketIds;
    }

    public List<Long> getTicketsIdList() {
        return Arrays.asList(this.ticketIds);
    }

    public void setTicketId(Long[] ticketIds) {
        this.ticketIds = ticketIds;
    }

    public UUID getRouteDetailRequestId() {
        return routeDetailRequestId;
    }

    public void setRouteDetailRequestId(UUID routeDetailRequestId) {
        this.routeDetailRequestId = routeDetailRequestId;
    }
}
