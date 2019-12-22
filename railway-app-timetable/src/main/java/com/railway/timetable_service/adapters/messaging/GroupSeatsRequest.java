package com.railway.timetable_service.adapters.messaging;

import java.util.UUID;

public class GroupSeatsRequest {

    private Long ticketId;
    private Long timeTableId;
    private int amountOfSeats;
    private UUID groupSeatsRequestId;

    public GroupSeatsRequest(Long ticketId, Long timeTableId, int amountOfSeats, UUID groupSeatsRequestId) {
        this.ticketId = ticketId;
        this.timeTableId = timeTableId;
        this.amountOfSeats = amountOfSeats;
        this.groupSeatsRequestId = groupSeatsRequestId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Long getTimeTableId() {
        return timeTableId;
    }

    public int getAmountOfSeats() {
        return amountOfSeats;
    }

    public UUID getGroupSeatsRequestId() {
        return groupSeatsRequestId;
    }
}
