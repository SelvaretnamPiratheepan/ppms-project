package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.Food_AllergyDTO;
import com.sgic.ppms.entities.Food_Allergy;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.Food_AllergyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(value = EndpointBundle.FOOD_ALLERGY)
public class Food_AllergyController {
	@Autowired
	private Food_AllergyService foodAllergyService;

	@GetMapping(value = EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<Food_Allergy>>> getAllFoodAllergies() {
		List<Food_Allergy> food_allergies = foodAllergyService.getAllFoodAllergies();

		if (food_allergies.isEmpty()) {
			ResponseWrapper<List<Food_Allergy>> notFoundResponse = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NO_RECORDS, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
		}

		ResponseWrapper<List<Food_Allergy>> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
				ValidationMessages.RETRIEVED, food_allergies);
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}

	@GetMapping(EndpointBundle.GET_BY_NAME)
	public ResponseEntity<ResponseWrapper<Food_Allergy>> getFoodAllergyByName(@PathVariable("name") String name) {
		Food_Allergy foodAllergy = foodAllergyService.getFoodAllergyByName(name);

		if (foodAllergy != null) {
			ResponseWrapper<Food_Allergy> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
					ValidationMessages.RETRIEVED, foodAllergy);
			return ResponseEntity.ok(responseWrapper);
		} else {
			ResponseWrapper<Food_Allergy> notFoundResponse = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NO_RECORDS, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
		}
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<Food_AllergyDTO>> createFoodAllergy(
			@RequestBody Food_AllergyDTO foodAllergyDTO) {
		if (foodAllergyDTO == null || foodAllergyDTO.getName() == null) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
					ValidationMessages.EMPTY_NAME, null));
		}

		if (!isValidName(foodAllergyDTO.getName())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
					ValidationMessages.INVALID_NAME, null));
		}

		Food_Allergy existingFoodAllergy = foodAllergyService.getFoodAllergyByName(foodAllergyDTO.getName());
		if (existingFoodAllergy != null) {
			Food_AllergyDTO existingFoodAllergyDTO = new Food_AllergyDTO(existingFoodAllergy.getId(),
					existingFoodAllergy.getName());
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
					ValidationMessages.NAME_EXISTS, existingFoodAllergyDTO));
		}

		Food_Allergy createdFoodAllergy = foodAllergyService.createFoodAllergy(foodAllergyDTO);
		if (createdFoodAllergy != null) {
			Food_AllergyDTO createdFoodAllergyDTO = new Food_AllergyDTO(createdFoodAllergy.getId(),
					createdFoodAllergy.getName());
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdFoodAllergyDTO));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(), ValidationMessages.CREATE_FAILED, null));
		}
	}

	private boolean isValidName(String name) {
		return name.matches("[a-zA-Z]+");
	}

	@DeleteMapping(value = EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<Void>> deleteFoodAllergy(@PathVariable int id) {
		RestApiResponseStatus status = foodAllergyService.deleteFoodAllergy(id);

		if (status == RestApiResponseStatus.DELETED) {
			ResponseWrapper<Void> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					ValidationMessages.DELETE_SUCCESS, null);
			return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
		} else if (status == RestApiResponseStatus.NOT_FOUND) {
			ResponseWrapper<Void> notFoundResponse = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.NO_RECORDS, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
		} else {
			ResponseWrapper<Void> errorResponse = new ResponseWrapper<>(
					RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(), ValidationMessages.DELETE_FAILED, null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
}