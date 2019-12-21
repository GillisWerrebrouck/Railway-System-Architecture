package com.railway.ticket_sale_service.domain;

public enum TicketStatus {
    BOOKED,
    DETAILS_FETCHED,
    FETCHING_DETAILS_FAILED,
    GROUP_SEATS_RESERVED,
    RESERVE_GROUP_SEATS_FAILED,
    DETAILS_FETCHED_GROUP_SEATS_RESERVED;
}
