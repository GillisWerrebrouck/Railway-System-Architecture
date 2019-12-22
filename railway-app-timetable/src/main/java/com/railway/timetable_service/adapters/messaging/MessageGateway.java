package com.railway.timetable_service.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = Channels.GET_ROUTE)
	public void getRoute(RouteRequest routeRequest);

	@Gateway(requestChannel = Channels.RESERVE_TRAIN)
	public void reserveTrain(TrainRequest trainRequest);

	@Gateway(requestChannel = Channels.RESERVE_STATIONS)
	public void reserveStations(StationsRequest stationsRequest);

	@Gateway(requestChannel = Channels.RESERVE_STAFF)
	public void reserveStaff(StaffRequest staffRequest);

	@Gateway(requestChannel = Channels.DISCARD_TRAIN_RESERVATION)
	public void discardTrainReservation(DiscardReservationRequest discardReservationRequest);

	@Gateway(requestChannel = Channels.DISCARD_STATION_RESERVATIONS)
	public void discardStationReservations(DiscardReservationRequest discardReservationRequest);

	@Gateway(requestChannel = Channels.DISCARD_STAFF_RESERVATIONS)
	public void discardStaffReservation(DiscardStaffReservationRequest discardStaffReservationRequest);
}
