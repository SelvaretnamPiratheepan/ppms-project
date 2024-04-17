package com.sgic.ppms.dto;

import com.sgic.ppms.enums.GestationalAgeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BirthHistoryDto {

	private String childId;
	private Long postnatalHistoryId;
	private String antenatalHistory;
	private String modeOfDelivery;
	private int gestationalAge;
	private GestationalAgeType gestationalAgeType;
	private float birthWeight;
}
