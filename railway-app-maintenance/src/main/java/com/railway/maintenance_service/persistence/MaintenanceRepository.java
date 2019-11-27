package com.railway.maintenance_service.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.railway.maintenance_service.domain.ScheduleMaintenance;

public interface MaintenanceRepository extends CrudRepository<ScheduleMaintenance, Long> {
	public List<ScheduleMaintenance> findScheduleMaintenanceByMaintenanceId(String maintenanceId);
	
	public List<ScheduleMaintenance> findScheduleMaintenanceByMaintenanceDate(LocalDate maintenanceDate);

	public List<ScheduleMaintenance> findScheduleMaintenanceByStatus(String status);

	public List<ScheduleMaintenance> findScheduleMaintenanceByCommentStartsWith(String comment);
}
