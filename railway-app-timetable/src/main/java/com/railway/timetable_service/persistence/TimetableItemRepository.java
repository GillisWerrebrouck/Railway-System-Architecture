package com.railway.timetable_service.persistence;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.railway.timetable_service.domain.TimetableItem;

public interface TimetableItemRepository extends CrudRepository<TimetableItem, Long> {
	List<TimetableItem> findByRouteId(Long routeId);
	
	@Query("select t from TimetableItem t where :time BETWEEN t.startDateTime and t.endDateTime and t.routeId = :routeId")
	List<TimetableItem> findByRouteIdFromTime(@Param("routeId") Long routeId,@Param("time") LocalDateTime time);
}
