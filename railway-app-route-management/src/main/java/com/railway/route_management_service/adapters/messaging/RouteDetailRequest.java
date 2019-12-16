package com.railway.route_management_service.adapters.messaging;

import java.util.UUID;

public class RouteDetailRequest {

    private Long ticketId;
    private UUID startStationId;
    private UUID endStationId;
    private UUID routeDetailRequestId;

    public RouteDetailRequest(Long ticketId, UUID startStationId, UUID endStationId, UUID routeDetailRequestId) {
        this.ticketId = ticketId;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.routeDetailRequestId = routeDetailRequestId;
    }

    public UUID getStartStationId() {
        return startStationId;
    }

    public UUID getEndStationId() {
        return endStationId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public UUID getRouteDetailRequestId() {
        return routeDetailRequestId;
    }

}
