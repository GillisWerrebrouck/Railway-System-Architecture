package com.railway.maintenance_service.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.railway.maintenance_service.domain.ScheduleItem;

public interface MaintenanceRepository extends CrudRepository<ScheduleItem, Long> {
	public List<ScheduleItem> findScheduleMaintenanceById(Long id);
	
	public List<ScheduleItem> findScheduleMaintenanceByMaintenanceDate(LocalDate maintenanceDate);

	public List<ScheduleItem> findScheduleMaintenanceByStatus(String status);

	public List<ScheduleItem> findScheduleMaintenanceByCommentStartsWith(String comment);
}
