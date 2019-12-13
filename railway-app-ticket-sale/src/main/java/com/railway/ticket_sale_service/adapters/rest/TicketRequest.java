package com.railway.ticket_sale_service.adapters.rest;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketRequest {

    private String startStationId;
    private String endStationId;
    private LocalDateTime startDateTime;
    private int amountOfSeats;
    private Long routeId;

    public TicketRequest(String startStationId, String endStationId, LocalDateTime startDateTime, int amountOfSeats, Long routeId) {
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.startDateTime = startDateTime;
        this.amountOfSeats = amountOfSeats;
        this.routeId = routeId;
    }

    public String getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }

    public String getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(String endStationId) {
        this.endStationId = endStationId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getAmountOfSeats() {
        return amountOfSeats;
    }

    public void setAmountOfSeats(int amountOfSeats) {
        this.amountOfSeats = amountOfSeats;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
