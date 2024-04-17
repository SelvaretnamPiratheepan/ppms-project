package com.sgic.ppms.enums;

import lombok.Getter;

@Getter
public enum Result {
	NORMAL("Normal"), ABNORMAL("Abnormal");

	private final String displayName;

	Result(String displayName) {

		this.displayName = displayName;
	}
}
