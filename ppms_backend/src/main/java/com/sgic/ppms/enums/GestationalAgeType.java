package com.sgic.ppms.enums;

public enum GestationalAgeType {
	DAYS(1), WEEKS(7), MONTHS(30);

	private final int durationInDays;

	GestationalAgeType(int durationInDays) {
		this.durationInDays = durationInDays;
	}

	public int getDurationInDays() {
		return durationInDays;
	}
}
