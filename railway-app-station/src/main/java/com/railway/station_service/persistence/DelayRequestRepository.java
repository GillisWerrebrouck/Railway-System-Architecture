package com.railway.station_service.persistence;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.railway.station_service.adapters.messaging.DelayRequest;

@Repository
public interface DelayRequestRepository extends CrudRepository<DelayRequest, UUID> {

}
