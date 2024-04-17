package com.sgic.ppms.enums;

public enum TimeUnit {
	DAYS(1), WEEKS(7), MINUTES(1440);

	private final int minutesInUnit;

	TimeUnit(int minutesInUnit) {
		this.minutesInUnit = minutesInUnit;
	}

	public int convertToUnit(int minutes) {
		return minutes / minutesInUnit;
	}

	public int convertToMinutes(int value) {
		return value * minutesInUnit;
	}
}
