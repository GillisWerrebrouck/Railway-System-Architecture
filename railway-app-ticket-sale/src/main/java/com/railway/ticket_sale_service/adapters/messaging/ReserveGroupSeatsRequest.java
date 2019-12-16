package com.railway.ticket_sale_service.adapters.messaging;

import java.util.UUID;

public class ReserveGroupSeatsRequest {

    private Long ticketId;
    private Long timeTableId;
    private int amountOfSeats;
    private UUID reserveGroupSeatsRequestId;

    public ReserveGroupSeatsRequest(Long ticketId, Long timeTableId, int amountOfSeats) {
        this.ticketId = ticketId;
        this.timeTableId = timeTableId;
        this.amountOfSeats = amountOfSeats;
        this.reserveGroupSeatsRequestId = UUID.randomUUID();
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

    public UUID getReserveGroupSeatsRequestId() {
        return reserveGroupSeatsRequestId;
    }
}
