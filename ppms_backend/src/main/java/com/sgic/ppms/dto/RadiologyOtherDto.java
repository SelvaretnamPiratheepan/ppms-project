package com.sgic.ppms.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RadiologyOtherDto {

	private Long id;
	private LocalDate date;
	private String results;
	private Long admitId;
	private Long otherTestMId;

}
