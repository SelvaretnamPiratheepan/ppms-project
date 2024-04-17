package com.sgic.ppms.services;

import com.sgic.ppms.dto.AllergyDetailDto;

public interface AllergyDetailService {
	AllergyDetailDto createAllergyDetail(AllergyDetailDto allergyDetailDto);

	AllergyDetailDto updateAllergyDetail(Long id, AllergyDetailDto allergyDetailDto);

	AllergyDetailDto getAllergyDetailByChildid(String childId);

	AllergyDetailDto getAllergyDetailById(Long childId);

	boolean childExistsAllergy(String childId);

	boolean childFound(String childId);

	void deleteAllergyDetailsById(Long id);

	boolean allergyDetailExistsById(Long Id);

	String getChildIdById(Long id);
}