package com.sgic.ppms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllergyDetailDto {

	private Long id;
	private String childId;
	private boolean hasAllergy;
}