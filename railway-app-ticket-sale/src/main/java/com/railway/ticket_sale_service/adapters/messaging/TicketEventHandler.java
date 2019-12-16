package com.railway.ticket_sale_service.adapters.messaging;

import com.railway.ticket_sale_service.domain.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class TicketEventHandler {

    private static Logger logger = LoggerFactory.getLogger(TicketEventHandler.class);

    private final TicketService ticketService;

    @Autowired
    public TicketEventHandler(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @StreamListener(Channels.ROUTE_DETAILS_FETCHED)
    public void distanceFetched(RouteDetailResponse response) {
        if(response.getDistance() > 0 && response.getStartStationName() != null && response.getEndStationName() != null) {
            logger.info("[TicketSale Event Handler] successfully fetched route details");
            ticketService.routeDetailsFetched(response);
        } else {
            logger.info("[Timetable Event Handler] failed to fetch route");
            ticketService.failedToFetchDetails(response);
        }
    }

    @StreamListener(Channels.GROUP_SEATS_RESERVED)
    public void groupSeatsReserved(ReserveGroupSeatsResponse response){
        if(response.isGroupSeatsReserved()){
            logger.info("[TicketSale Event Handler] successfully reserved group seats");
            ticketService.groupSeatsReserved(response);
        }else{
            logger.info("[TicketSale Event Handler] failed to reserve group seats");
            ticketService.failedToReserveGroupSeats(response);
        }
    }
}
