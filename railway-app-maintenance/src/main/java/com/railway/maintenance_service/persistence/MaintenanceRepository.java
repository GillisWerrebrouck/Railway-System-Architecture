package com.railway.maintenance_service.persistence;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.railway.maintenance_service.domain.ScheduleItem;

public interface MaintenanceRepository extends CrudRepository<ScheduleItem, Long> {
	public ScheduleItem findByRequestId(UUID requestId);
}
