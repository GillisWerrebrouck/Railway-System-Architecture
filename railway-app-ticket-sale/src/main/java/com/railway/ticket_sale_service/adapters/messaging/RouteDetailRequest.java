package com.railway.ticket_sale_service.adapters.messaging;

import java.util.UUID;

public class RouteDetailRequest {

    private Long[] ticketIds;
    private UUID startStationId;
    private UUID endStationId;
    private UUID routeDetailRequestId;

    public RouteDetailRequest(Long[] ticketIds, UUID startStationId, UUID endStationId) {
        this.ticketIds = ticketIds;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.routeDetailRequestId = UUID.randomUUID();
    }

    public RouteDetailRequest(Long ticketId, UUID startStationId, UUID endStationId) {
        this(new Long[]{ticketId}, startStationId, endStationId);
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

    public Long[] getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(Long[] ticketIds) {
        this.ticketIds = ticketIds;
    }

    public UUID getRouteDetailRequestId() {
        return routeDetailRequestId;
    }

    public void setRouteDetailRequestId(UUID routeDetailRequestId) {
        this.routeDetailRequestId = routeDetailRequestId;
    }
}
