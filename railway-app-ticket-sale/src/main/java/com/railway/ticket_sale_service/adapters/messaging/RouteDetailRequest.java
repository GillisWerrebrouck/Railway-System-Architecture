package com.railway.ticket_sale_service.adapters.messaging;

import java.util.UUID;

public class RouteDetailRequest {

    private Long ticketId;
    private UUID startStationId;
    private UUID endStationId;
    private UUID routeDetailRequestId;

    public RouteDetailRequest(Long ticketId, UUID startStationId, UUID endStationId) {
        this.ticketId = ticketId;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.routeDetailRequestId = UUID.randomUUID();
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
