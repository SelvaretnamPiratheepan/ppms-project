package com.sgic.ppms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class familyHistoryDto {
	private Integer id;
	private String FamType;
	private String FamilyHistoryOfIllness;
	private Boolean Consanguinity;
	private String House;
	private String H_Type;
	private String Roof;
	private String Floor;
	private String Toilet;
	private String WaterResource;
	private String Chlorination;
	private String NearestHospital;
	private Double Distance;
	private Integer NoOfChild;
}
