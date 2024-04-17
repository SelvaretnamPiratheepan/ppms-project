package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.ChildFoodAllergyDto;

public interface ChildFoodAllergyService {
	ChildFoodAllergyDto createChildFoodAllergy(ChildFoodAllergyDto childFoodAllergyDto);

	ChildFoodAllergyDto updateChildFoodAllergy(Long id, ChildFoodAllergyDto childFoodAllergyDto);

	ChildFoodAllergyDto getChildFoodAllergyByid(Long id);

	List<ChildFoodAllergyDto> getChildFoodAllergyByAllergyid(Long allergyDetailId);

	void deleteChildFoodAllergyById(Long id);

	boolean childFoodAllergyFound(Long id);

	boolean relationshipExists(Long allergyId, int foodAllergyId);
}
