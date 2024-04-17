
package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.DrugAllergyDto;
import com.sgic.ppms.entities.DrugAllergy;
import com.sgic.ppms.util.ResponseWrapper;

public interface DrugAllergyService {
	ResponseWrapper<List<DrugAllergy>> getAllDrugAllergies();

	ResponseWrapper<DrugAllergy> getDrugAllergyById(long id);

	ResponseWrapper<DrugAllergy> createDrugAllergy(DrugAllergyDto drugAllergyDto);

	ResponseWrapper<DrugAllergy> updateDrugAllergy(Long id, DrugAllergyDto drugAllergyDto);

	ResponseWrapper<Void> deleteDrugAllergy(Long id);
}
