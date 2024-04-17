package com.sgic.ppms.enums;

import lombok.Getter;

@Getter
public enum FollowUpType {
	Clinic("Clinic"), Ward("ward");
	private final String displayName;

	FollowUpType (String displayName) {
		this.displayName = displayName;
	}

}
