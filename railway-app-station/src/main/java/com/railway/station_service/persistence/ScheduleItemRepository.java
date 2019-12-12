package com.railway.station_service.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.railway.station_service.domain.ScheduleItem;

public interface ScheduleItemRepository extends CrudRepository<ScheduleItem, Long> {
	@Transactional
	@Modifying
	@Query("delete from ScheduleItem where timetableId = ?1")
	void deleteAllByTimetableId(Long timetableId);
}
