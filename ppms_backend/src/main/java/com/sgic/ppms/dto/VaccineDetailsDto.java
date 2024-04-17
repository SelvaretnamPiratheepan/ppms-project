package com.sgic.ppms.dto;

import com.sgic.ppms.enums.AgeType;

import lombok.Data;

@Data
public class VaccineDetailsDto {

	private Long id;
	private int age;
	private AgeType ageType;
	private String vaccineName;
	private String icdCode;

}
