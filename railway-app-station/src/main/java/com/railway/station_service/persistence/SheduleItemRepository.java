package com.railway.station_service.persistence;

import org.springframework.data.repository.CrudRepository;

import com.railway.station_service.domain.SheduleItem;

public interface SheduleItemRepository extends CrudRepository<SheduleItem, Long> {
	
}
