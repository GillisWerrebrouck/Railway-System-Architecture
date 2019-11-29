package com.railway.route_management_service.helpers;

public final class Constants {
	public static final String INTER_STATION_RELATIONSHIP = "CONNECTED_WITH";
	public static final String ROUTE_STATION_RELATIONSHIP = "USES_STATION";

	// no objects should be created from this class
	private Constants() {
		throw new AssertionError();
	}
}
