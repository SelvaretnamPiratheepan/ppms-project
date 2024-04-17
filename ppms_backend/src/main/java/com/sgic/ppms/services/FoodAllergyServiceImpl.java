package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.Food_AllergyDTO;
import com.sgic.ppms.entities.Food_Allergy;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.Food_AllergyRepository;

import jakarta.transaction.Transactional;

@Service
public class FoodAllergyServiceImpl implements Food_AllergyService {
	@Autowired
	public Food_AllergyRepository foodAllergyRepository;

	@Transactional
	public List<Food_Allergy> getAllFoodAllergies() {
		return foodAllergyRepository.findAll();
	}

	@Transactional
	public Food_Allergy getFoodAllergyByName(String name) {
		return foodAllergyRepository.findByName(name);
	}

	@Transactional
	public Food_Allergy createFoodAllergy(Food_AllergyDTO food_allergyDTO) {
		Food_Allergy food_allergy = new Food_Allergy();
		BeanUtils.copyProperties(food_allergyDTO, food_allergy);
		return foodAllergyRepository.save(food_allergy);
	}

	@Transactional
	public RestApiResponseStatus deleteFoodAllergy(int id) {
		Optional<Food_Allergy> optionalFoodAllergy = foodAllergyRepository.findById(id);
		if (optionalFoodAllergy.isPresent()) {
			foodAllergyRepository.deleteById(id);
			return RestApiResponseStatus.DELETED;
		} else {
			return RestApiResponseStatus.NOT_FOUND;
		}
	}
}