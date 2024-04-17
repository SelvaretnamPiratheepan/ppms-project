package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.ChildEnvAllergyDto;

public interface ChildEnvAllergyService {
	ChildEnvAllergyDto createChildEnvironmentalAllergy(ChildEnvAllergyDto envAllergyDto);

	ChildEnvAllergyDto updateChildEnvironmentalAllergy(Long id, ChildEnvAllergyDto envAllergyDto);

	ChildEnvAllergyDto getChildEnvironmentalAllergyByid(Long id);

	List<ChildEnvAllergyDto> getChildEnvironmentalAllergyByAllergyid(Long admitId);

	boolean relationshipExists(Long allergyDetailId, Long envAllergyId);

	void deleteChildEnvironmentalAllergyById(Long id);

	boolean childEnvAllergyFound(Long id);
}
