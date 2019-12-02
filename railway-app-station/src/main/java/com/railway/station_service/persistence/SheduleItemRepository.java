package com.railway.station_service.persistence;

import org.springframework.data.repository.CrudRepository;

import com.railway.station_service.domain.ScheduleItem;

public interface SheduleItemRepository extends CrudRepository<ScheduleItem, Long> {
	
}
