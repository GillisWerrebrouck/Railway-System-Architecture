package com.railway.timetable_service.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.railway.timetable_service.domain.TimetableItem;

public interface TimetableItemRepository extends CrudRepository<TimetableItem, Long> {
	@Query("SELECT t FROM TimetableItem t WHERE routeId = ?1 AND startDateTime>=CURRENT_DATE")
	List<TimetableItem> findByRouteId(Long routeId);
	
	@Query("SELECT t FROM TimetableItem t WHERE t.endDateTime >= CURRENT_DATE AND routeId = ?1")
	List<TimetableItem> findByRouteIdInFromNow(Long routeId);
  
	@Query("SELECT t FROM TimetableItem t WHERE t.trainOperatorRequestId=?1 OR t.trainConductorRequestId=?1")
	TimetableItem findByStaffRequestId(UUID staffRequestId);
	
	List<TimetableItem> findByRouteIdAndStartDateTimeBetweenOrderByStartDateTime(Long routeId, LocalDateTime fromDate, LocalDateTime toDate);
}
