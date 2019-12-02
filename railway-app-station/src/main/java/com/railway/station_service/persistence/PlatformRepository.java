package com.railway.station_service.persistence;

import org.springframework.data.repository.CrudRepository;

import com.railway.station_service.domain.Platform;

public interface PlatformRepository  extends CrudRepository<Platform, Long>  {
	
}
