package com.railway.timetable_service.adapters.messaging;

import com.railway.timetable_service.domain.NotEnoughGroupSeatsException;
import com.railway.timetable_service.domain.TimetableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class TimetableCommandHandler {

    private static Logger logger = LoggerFactory.getLogger(TimetableEventHandler.class);
    private final TimetableService timetableItemService;

    @Autowired
    public TimetableCommandHandler(TimetableService timetableItemService) {
        this.timetableItemService = timetableItemService;
    }

    @StreamListener(Channels.RESERVE_GROUP_SEATS)
    @SendTo(Channels.GROUP_SEATS_RESERVED)
    public ReserveGroupSeatsResponse reserveGroupSeats(ReserveGroupSeatsRequest groupSeatsRequest){
        logger.info("[Timetable Event Handler] successfully reserved train");
        try {
            timetableItemService.reserveGroupSeats(groupSeatsRequest);
            logger.info("[Timetable Event Handler] successfully reserved group seats");
            return new ReserveGroupSeatsResponse(groupSeatsRequest.getTicketId(), groupSeatsRequest.getTimeTableId(), true, groupSeatsRequest.getReserveGroupSeatsRequestId());
        } catch (NotEnoughGroupSeatsException e) {
            logger.info("[Timetable Event Handler] failed to reserve group seats, not enough group seats available");
            return new ReserveGroupSeatsResponse(groupSeatsRequest.getTicketId(), groupSeatsRequest.getTimeTableId(), false, groupSeatsRequest.getReserveGroupSeatsRequestId());
        } catch (NullPointerException en){
            logger.info("[Timetable Event Handler] no timetableItem found with the id " + groupSeatsRequest.getTimeTableId());
            return new ReserveGroupSeatsResponse(groupSeatsRequest.getTicketId(), null, false, groupSeatsRequest.getReserveGroupSeatsRequestId());
        }
    }
}
