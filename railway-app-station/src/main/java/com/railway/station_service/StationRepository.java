package com.railway.station_service;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends CrudRepository<Station, Long>{
	List<Station> findByName(@Param("name") String name);
	
	@Query("select s.platforms from Station s where s.id = ?1")
	List<Platform> getPlatforms(Long id);
}
