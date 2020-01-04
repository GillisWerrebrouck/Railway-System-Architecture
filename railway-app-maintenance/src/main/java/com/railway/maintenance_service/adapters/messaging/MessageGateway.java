package com.railway.maintenance_service.adapters.messaging;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = Channels.RESERVE_STAFF_M)
	public void reserveStaff(StaffRequest request);
  
	@Gateway(requestChannel = Channels.NOTIFY_INFRASTRUCTURE_DAMAGE)
	public void requestInfrastructureDamage(InfrastructureDamageRequest request);
  
	@Gateway(requestChannel = Channels.CHANGE_TRAIN_STATUS)
	public void changeTrainStatus(ChangeStatusRequest request);
}
