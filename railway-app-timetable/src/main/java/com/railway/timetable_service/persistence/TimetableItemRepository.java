package com.railway.timetable_service.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.railway.timetable_service.domain.TimetableItem;

public interface TimetableItemRepository extends CrudRepository<TimetableItem, Long> {
	List<TimetableItem> findByRouteId(Long routeId);
	
	@Query("SELECT t FROM TimetableItem t WHERE t.startDateTime >= CURRENT_DATE AND routeId = ?1")
	List<TimetableItem> findByRouteIdInFromNow(Long routeId);
}
