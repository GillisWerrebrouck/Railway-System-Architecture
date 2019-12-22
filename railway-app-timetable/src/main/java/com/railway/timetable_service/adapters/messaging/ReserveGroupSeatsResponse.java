package com.railway.timetable_service.adapters.messaging;

import java.util.UUID;

public class ReserveGroupSeatsResponse {
    private Long ticketId;
    private Long timeTableId;
    private boolean groupSeatsReserved;
    private UUID reserveGroupSeatsRequestId;

    public ReserveGroupSeatsResponse(Long ticketId, Long timeTableId, boolean groupSeatsReserved, UUID reserveGroupSeatsRequestId) {
        this.ticketId = ticketId;
        this.timeTableId = timeTableId;
        this.groupSeatsReserved = groupSeatsReserved;
        this.reserveGroupSeatsRequestId = reserveGroupSeatsRequestId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Long getTimeTableId() {
        return timeTableId;
    }

    public boolean isGroupSeatsReserved() {
        return groupSeatsReserved;
    }

    public UUID getReserveGroupSeatsRequestId() {
        return reserveGroupSeatsRequestId;
    }
}
