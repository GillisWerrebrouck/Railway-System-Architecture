package com.railway.station_service.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, UUID>{
	Station findByName(@Param("name") String name);

	@Query("select s.platforms from Station s where s.id = ?1")
	List<Platform> getPlatformsByStationId(UUID id);
	
	Station getStationById(UUID id);
}
