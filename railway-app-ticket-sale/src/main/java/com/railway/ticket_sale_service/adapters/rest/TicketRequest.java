package com.railway.ticket_sale_service.adapters.rest;

import java.time.LocalDateTime;

public class TicketRequest {

    private String startStationId;
    private String endStationId;
    private LocalDateTime startDateTime;
    private int amountOfSeats;
    private Long timeTableId;

    public TicketRequest(String startStationId, String endStationId, LocalDateTime startDateTime, int amountOfSeats, Long timeTableId) {
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.startDateTime = startDateTime;
        this.amountOfSeats = amountOfSeats;
        this.timeTableId = timeTableId;
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

    public Long getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(Long timeTableId) {
        this.timeTableId = timeTableId;
    }
}
