package com.railway.timetable_service.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.railway.timetable_service.domain.TimetableItem;

public interface TimetableItemRepository extends CrudRepository<TimetableItem, Long> {
	List<TimetableItem> findByRouteId(Long routeId);
	
	@Query("SELECT t FROM TimetableItem t WHERE t.trainOperatorRequestId=?1 OR t.trainConductorRequestId=?1")
	TimetableItem findByStaffRequestId(UUID staffRequestId);
}
