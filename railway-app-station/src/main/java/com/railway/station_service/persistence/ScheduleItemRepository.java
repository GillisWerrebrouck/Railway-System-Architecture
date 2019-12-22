package com.railway.station_service.persistence;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.railway.station_service.domain.ScheduleItem;
import com.railway.station_service.domain.ScheduleItemResponse;

public interface ScheduleItemRepository extends CrudRepository<ScheduleItem, Long> {
	@Transactional
	@Modifying
	@Query("delete from ScheduleItem where timetableId = ?1")
	void deleteAllByTimetableId(Long timetableId);
		
	@Query("SELECT new com.railway.station_service.domain.ScheduleItemResponse(s.id, s.name, p.id, p.platformNumber, si.id, si.arrivalDateTime, si.departureDateTime, si.delayInMinutes) FROM ScheduleItem si " + 
			"JOIN Platform p ON si.platform=p.id " + 
			"JOIN Station s ON p.station=s.id " + 
			"WHERE si.timetableId = ?1 ORDER BY si.arrivalDateTime")
	Collection<ScheduleItemResponse> getScheduleItemsByTimetableId(Long timetableId);
}
