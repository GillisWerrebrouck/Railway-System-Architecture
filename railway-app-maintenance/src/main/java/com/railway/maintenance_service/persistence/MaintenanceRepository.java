package com.railway.maintenance_service.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.railway.maintenance_service.domain.ScheduleItem;

public interface MaintenanceRepository extends CrudRepository<ScheduleItem, Long> {
	public List<ScheduleItem> findScheduleMaintenanceById(Long id);
	
	public List<ScheduleItem> findScheduleItemByStartDate(LocalDate startDate);

	public List<ScheduleItem> findScheduleItemByStatus(String status);

	public List<ScheduleItem> findScheduleItemByCommentStartsWith(String comment);
}
