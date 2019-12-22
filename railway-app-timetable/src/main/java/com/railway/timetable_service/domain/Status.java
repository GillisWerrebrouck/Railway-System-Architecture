package com.railway.timetable_service.domain;

public enum Status {
	UNKNOWN,
	AWAITING_INITIATION,
	STARTED,
	PENDING,
	SUCCESSFUL,
	FAILED,
	AUTO_RESCHEDULE_FAILED,
	DISCARDED
}
