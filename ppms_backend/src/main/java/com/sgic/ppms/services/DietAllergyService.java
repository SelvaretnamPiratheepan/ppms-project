package com.sgic.ppms.services;

import com.sgic.ppms.dto.DietAllergyDto;

public interface DietAllergyService {

	DietAllergyDto updateDietAllergy(Long id, DietAllergyDto dietAllergyDto);

	DietAllergyDto createDietAllergy(DietAllergyDto dietAllergyDto);

	void deleteDietAllergy(Long id);

	DietAllergyDto getDietAllergyById(Long id);

	DietAllergyDto getDietAllergyByChildId(String childId);

	boolean dietAllergyExists(Long id);

	boolean childFoundDietAllergy(String childId);

	boolean allergyExistsDietAllergy(Long allergyId);

	Long getAllergyIdById(Long id);

	String getChildIdbyId(Long id);
}