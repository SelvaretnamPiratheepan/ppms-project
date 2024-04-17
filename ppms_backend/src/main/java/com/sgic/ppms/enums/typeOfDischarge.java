package com.sgic.ppms.enums;

import lombok.Getter;

@Getter
public enum typeOfDischarge {
	Discharge("Discharge"), Transfer("Transfer"), Death("Death");

	private final String displayName;

	typeOfDischarge(String displayName) {
		this.displayName = displayName;
	}

}
