package com.sgic.ppms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseDTO {

	private long diseaseid;
	private String icdname;
	private String icd10;
	private int icdcode;
	private long id;

}
