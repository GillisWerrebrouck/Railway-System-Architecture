package com.railway.station_service.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.railway.station_service.domain.Platform;
import com.railway.station_service.domain.Station;

@Repository
public interface StationRepository extends CrudRepository<Station, UUID>{
	List<Station> findByName(@Param("name") String name);

	@Query("select s.platforms from Station s where s.id = ?1")
	List<Platform> getPlatformsByStationId(UUID id);
}
