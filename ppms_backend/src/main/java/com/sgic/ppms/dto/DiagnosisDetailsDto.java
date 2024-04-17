package com.sgic.ppms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisDetailsDto {
	private Long id;
	private Long admitId;
	private Long diseaseId;

}