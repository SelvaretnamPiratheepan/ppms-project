package com.sgic.ppms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentalAllergyDTO {

	private Long id;
	private String allergyName;

	public EnvironmentalAllergyDTO(Long id, String allergyName) {
		this.id = id;
		this.allergyName = allergyName;
	}

	public EnvironmentalAllergyDTO() {
	}
}
