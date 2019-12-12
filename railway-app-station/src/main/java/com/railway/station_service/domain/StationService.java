package com.railway.station_service.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.station_service.persistence.PlatformRepository;
import com.railway.station_service.persistence.ScheduleItemRepository;
import com.railway.station_service.persistence.StationRepository;

@Service
public class StationService {
	StationRepository stationRepository;
	PlatformRepository platformRepository;
	ScheduleItemRepository scheduleItemRepository;
	
	@Autowired
	public StationService(StationRepository stationRepository, PlatformRepository platformRepository, ScheduleItemRepository scheduleItemRepository) {
		this.stationRepository = stationRepository;
		this.platformRepository = platformRepository;
		this.scheduleItemRepository = scheduleItemRepository;
	}
	
	public boolean reserveStation(UUID stationId, Long timetableId, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
		Station station = stationRepository.findById(stationId).orElse(null);
		if(station == null) return false;
		
		Collection<Platform> platforms = station.getPlatforms();
		for(Platform platform : platforms) {
			if(isPlatformAvailable(platform.getReservedSlots(), arrivalDateTime, departureDateTime)) {
				ScheduleItem scheduleItem = new ScheduleItem(timetableId, arrivalDateTime, departureDateTime);
				scheduleItem.setPlatform(platform);
				scheduleItemRepository.save(scheduleItem);
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isPlatformAvailable(Collection<ScheduleItem> reservedSlots, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
		boolean isPlatformAvailable = true;
		
		for(ScheduleItem scheduleItem : reservedSlots) {
			if(!(arrivalDateTime.isBefore(scheduleItem.getArrivalDateTime()) && departureDateTime.isBefore(scheduleItem.getArrivalDateTime()) || 
					arrivalDateTime.isAfter(scheduleItem.getDepartureDateTime()) && departureDateTime.isAfter(scheduleItem.getDepartureDateTime()))) {
				isPlatformAvailable = false;
			}
		}
		
		return isPlatformAvailable;
	}

	public void discardStationReservations(Long timetableId) {
		scheduleItemRepository.deleteAllByTimetableId(timetableId);
	}
}
