package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.EnvironmentalAllergyDTO;
import com.sgic.ppms.util.ResponseWrapper;

public interface EnvironmentalAllergyService {

	ResponseWrapper<EnvironmentalAllergyDTO> insertEnvironmentalAllergyDetail(
			EnvironmentalAllergyDTO environmentalAllergyDTO);

	ResponseWrapper<EnvironmentalAllergyDTO> updateEnvironmentalAllergyDetail(Long id,
			EnvironmentalAllergyDTO environmentalAllergyDTO);

	ResponseWrapper<EnvironmentalAllergyDTO> deleteEnvironmentalAllergyDetail(Long id);

	ResponseWrapper<List<EnvironmentalAllergyDTO>> getAllEnvironmentalAllergies();

	Object getEnvironmentalAllergy(Long id);

	ResponseWrapper<String> getEnvironmentalAllergyName(Long id);

	ResponseWrapper<Long> getEnvironmentalAllergyIdByName(String name);

	ResponseWrapper<EnvironmentalAllergyDTO> getEnvironmentalAllergyByName(String name);

	boolean existsByName(String name);

}
