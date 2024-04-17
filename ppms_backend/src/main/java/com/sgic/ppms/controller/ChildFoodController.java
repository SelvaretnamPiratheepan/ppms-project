package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.ChildFoodAllergyDto;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.ChildFoodAllergyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.CHILD_FOOD_ALLERGY)
public class ChildFoodController {

	@Autowired
	private ChildFoodAllergyService childFoodAllergyService;

	private ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> validateChildFoodAllergy(
			ChildFoodAllergyDto childFoodAllergyDto) {
		Long allergyId = childFoodAllergyDto.getAllergyDetailId();
		int foodAllergyId = childFoodAllergyDto.getFoodAllergy().getId();
		boolean relationshipExists = childFoodAllergyService.relationshipExists(allergyId, foodAllergyId);
		if (relationshipExists) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.RELATIONSHIP_ALREADY_EXISTS);
		}

		return null;
	}

	private ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> errorResponse(HttpStatus status, String message) {
		ResponseWrapper<ChildFoodAllergyDto> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
				message, null);
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> createChildFoodAllergy(
			@RequestBody @Valid ChildFoodAllergyDto childFoodAllergyDto) {
		ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> validationResponse = validateChildFoodAllergy(
				childFoodAllergyDto);
		if (validationResponse != null) {
			return validationResponse;
		}
		ChildFoodAllergyDto createdChildFoodAllergy = childFoodAllergyService
				.createChildFoodAllergy(childFoodAllergyDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdChildFoodAllergy));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> updateChildFoodAllergy(@PathVariable("id") Long id,
			@RequestBody @Valid ChildFoodAllergyDto childFoodAllergyDto) {
		boolean childFoodAllergyFound = childFoodAllergyService.childFoodAllergyFound(id);
		if (!childFoodAllergyFound) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_FOODALLERGYID);
		}
		ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> validationResponse = validateChildFoodAllergy(
				childFoodAllergyDto);
		if (validationResponse != null) {
			return validationResponse;
		}
		ChildFoodAllergyDto updatedChildFoodAllergy = childFoodAllergyService.updateChildFoodAllergy(id,
				childFoodAllergyDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				ValidationMessages.UPDATE_SUCCESS, updatedChildFoodAllergy));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> getChildFoodAllergyById(@PathVariable("id") Long id) {

		ChildFoodAllergyDto childFoodAllergy = childFoodAllergyService.getChildFoodAllergyByid(id);
		return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(),
				ValidationMessages.FOUND, childFoodAllergy));
	}

	@GetMapping(EndpointBundle.GET_BY_ALLERGY_DETAIL_ID)
	public ResponseEntity<ResponseWrapper<List<ChildFoodAllergyDto>>> getChildFoodAllergyByAllergyId(
			@PathVariable("allergyId") Long id) {

		List<ChildFoodAllergyDto> childFoodAllergyList = childFoodAllergyService.getChildFoodAllergyByAllergyid(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(),
				ValidationMessages.FOUND, childFoodAllergyList));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<ChildFoodAllergyDto>> deleteChildFoodAllergyById(
			@PathVariable("id") Long id) {
		Boolean childFoodAllergy = childFoodAllergyService.childFoodAllergyFound(id);
		if (!childFoodAllergy) {
			return errorResponse(HttpStatus.NOT_FOUND, ValidationMessages.INVALID_FOODALLERGYID);
		}
		childFoodAllergyService.deleteChildFoodAllergyById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));
	}
}
