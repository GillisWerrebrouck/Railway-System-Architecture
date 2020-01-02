package com.railway.route_management_service.adapters.messaging;

import java.util.UUID;

public class RouteDetailRequest {

    private Long[] ticketIds;
    private UUID startStationId;
    private UUID endStationId;
    private UUID routeDetailRequestId;

    public RouteDetailRequest(Long[] ticketIds, UUID startStationId, UUID endStationId, UUID routeDetailRequestId) {
        this.ticketIds = ticketIds;
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

    public Long[] getTicketIds() {
        return ticketIds;
    }

    public UUID getRouteDetailRequestId() {
        return routeDetailRequestId;
    }

}
