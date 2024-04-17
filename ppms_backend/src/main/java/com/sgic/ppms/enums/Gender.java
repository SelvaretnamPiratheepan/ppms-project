package com.sgic.ppms.enums;

import lombok.Getter;

@Getter
public enum Gender {
	FEMALE("Female"), MALE("Male"), OTHER("Other");

	private final String displayName;

	Gender(String displayName) {
		this.displayName = displayName;
	}

}