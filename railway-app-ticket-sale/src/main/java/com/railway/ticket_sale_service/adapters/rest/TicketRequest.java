package com.railway.ticket_sale_service.adapters.rest;

import java.time.LocalDateTime;

public class TicketRequest {

    private String startStationId;
    private String endStationId;
    private LocalDateTime startDateTime;
    private int amountOfSeats;
    private Long timetableId;

    public TicketRequest(String startStationId, String endStationId, LocalDateTime startDateTime, int amountOfSeats, Long timetableId) {
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.startDateTime = startDateTime;
        this.amountOfSeats = amountOfSeats;
        this.timetableId = timetableId;
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

    public Long getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Long timetableId) {
        this.timetableId = timetableId;
    }
}
