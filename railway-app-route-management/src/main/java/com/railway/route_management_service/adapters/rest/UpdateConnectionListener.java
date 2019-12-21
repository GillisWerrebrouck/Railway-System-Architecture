package com.railway.route_management_service.adapters.rest;

import com.railway.route_management_service.adapters.messaging.RouteUsageResponse;

public interface UpdateConnectionListener {
	public void onResult(RouteUsageResponse response);
}
