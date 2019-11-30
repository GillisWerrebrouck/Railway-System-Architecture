package com.railway.staff_service.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.railway.staff_service.domain.ScheduleItem;

public interface ScheduleItemRepository extends MongoRepository<ScheduleItem, Long> {
	Optional<ScheduleItem> findById(String id);
	
	List<ScheduleItem> findByStartDate(LocalDateTime startDate);
	
	List<ScheduleItem> findByEndDate(LocalDateTime endDate);
}
