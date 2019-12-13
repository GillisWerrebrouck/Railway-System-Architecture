package com.railway.route_management_service.adapters.messaging;

import java.util.UUID;

public class RouteDetailRequest {

    private Long routeId;
    private UUID startStationId;
    private UUID endStationId;
    private Long ticketId;
    private UUID routeDetailRequestId;

    public RouteDetailRequest(Long routeId, UUID startStationId, UUID endStationId, Long ticketId, UUID routeDetailRequestId) {
        this.routeId = routeId;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.ticketId = ticketId;
        this.routeDetailRequestId = routeDetailRequestId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public UUID getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(UUID startStationId) {
        this.startStationId = startStationId;
    }

    public UUID getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(UUID endStationId) {
        this.endStationId = endStationId;
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
