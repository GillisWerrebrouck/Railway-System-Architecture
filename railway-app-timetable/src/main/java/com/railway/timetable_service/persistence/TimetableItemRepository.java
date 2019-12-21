package com.railway.timetable_service.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.railway.timetable_service.domain.TimetableItem;

public interface TimetableItemRepository extends CrudRepository<TimetableItem, Long> {
	@Query("SELECT t FROM TimetableItem t WHERE routeId = ?1 AND startDateTime>=CURRENT_DATE")
	List<TimetableItem> findByRouteId(Long routeId);
	
	List<TimetableItem> findByRouteIdAndStartDateTimeBetween(Long routeId, LocalDateTime fromDate, LocalDateTime toDate);
}
