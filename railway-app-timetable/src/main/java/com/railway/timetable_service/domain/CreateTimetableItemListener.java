package com.railway.timetable_service.domain;

public interface CreateTimetableItemListener {
	public void onCreateTimetableItemResult(TimetableItem timetableItem);
	public void onCreateTimetableItemFailed(TimetableItem timetableItem);
}
