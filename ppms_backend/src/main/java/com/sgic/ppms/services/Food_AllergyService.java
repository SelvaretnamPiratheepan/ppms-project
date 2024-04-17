package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.Food_AllergyDTO;
import com.sgic.ppms.entities.Food_Allergy;
import com.sgic.ppms.enums.RestApiResponseStatus;

public interface Food_AllergyService {
	List<Food_Allergy> getAllFoodAllergies();

	Food_Allergy createFoodAllergy(Food_AllergyDTO food_allergyDTO);

	RestApiResponseStatus deleteFoodAllergy(int id);

	Food_Allergy getFoodAllergyByName(String name);

}