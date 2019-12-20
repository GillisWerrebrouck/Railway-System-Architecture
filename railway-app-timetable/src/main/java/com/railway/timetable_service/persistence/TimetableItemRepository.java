package com.railway.timetable_service.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.railway.timetable_service.domain.TimetableItem;

public interface TimetableItemRepository extends CrudRepository<TimetableItem, Long> {
	List<TimetableItem> findByRouteId(Long routeId);
	
	@Query("select t from Timetable t where time BETWEEN t.startDateTime and t.endDateTime")
	List<TimetableItem> findByRouteIdFromTime(Long routeId, LocalDateTime time);
}
