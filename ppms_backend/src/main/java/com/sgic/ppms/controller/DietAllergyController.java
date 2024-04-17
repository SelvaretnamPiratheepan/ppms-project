package com.sgic.ppms.controller;

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

import com.sgic.ppms.dto.DietAllergyDto;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.DietAllergyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = EndpointBundle.DIET_ALLERGY)
public class DietAllergyController {

	@Autowired
	private DietAllergyService dietAllergyService;

	private ResponseEntity<ResponseWrapper<DietAllergyDto>> errorResponse(RestApiResponseStatus status,
			String message) {
		ResponseWrapper<DietAllergyDto> response = new ResponseWrapper<>(status.getCode(), message, null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@GetMapping(value = EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<DietAllergyDto>> getDietAllergyById(@PathVariable("id") Long id) {
		DietAllergyDto dietAllergyDto = dietAllergyService.getDietAllergyById(id);
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(), ValidationMessages.FOUND, dietAllergyDto));
	}

	@GetMapping(value = EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<DietAllergyDto>> getDietAllergyByChildId(
			@PathVariable("childId") String childId) {
		DietAllergyDto dietAllergyDto = dietAllergyService.getDietAllergyByChildId(childId);
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(), ValidationMessages.FOUND, dietAllergyDto));
	}

	@PutMapping(value = EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<DietAllergyDto>> updateDietAllergy(@PathVariable("id") Long id,
			@RequestBody @Valid DietAllergyDto dietAllergyDto) {
		String childId = dietAllergyDto.getChildId();
		if (dietAllergyService.childFoundDietAllergy(childId)
				&& !childId.equals(dietAllergyService.getChildIdbyId(id))) {
			return errorResponse(RestApiResponseStatus.FAILURE, ValidationMessages.CHILDID_EXISTS);
		}
		Long allergyId = dietAllergyDto.getAllergyId();
		if (dietAllergyService.allergyExistsDietAllergy(allergyId)
				&& !allergyId.equals(dietAllergyService.getAllergyIdById(id))) {
			return errorResponse(RestApiResponseStatus.FAILURE, ValidationMessages.ALLERGY_ALREADY_EXISTS);
		}
		DietAllergyDto updatedDietAllergyDto = dietAllergyService.updateDietAllergy(id, dietAllergyDto);
		return ResponseEntity.accepted().body(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				ValidationMessages.UPDATE_SUCCESS, updatedDietAllergyDto));
	}

	@PostMapping(value = EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<DietAllergyDto>> createDietAllergy(
			@RequestBody @Valid DietAllergyDto dietAllergyDto) {
		String childId = dietAllergyDto.getChildId();
		if (dietAllergyService.childFoundDietAllergy(childId)) {
			return errorResponse(RestApiResponseStatus.FAILURE, ValidationMessages.CHILDID_EXISTS);
		}
		Long allergyId = dietAllergyDto.getAllergyId();
		if (dietAllergyService.allergyExistsDietAllergy(allergyId)) {
			return errorResponse(RestApiResponseStatus.FAILURE, ValidationMessages.ALLERGY_ALREADY_EXISTS);
		}
		DietAllergyDto createdDietAllergyDto = dietAllergyService.createDietAllergy(dietAllergyDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdDietAllergyDto));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<DietAllergyDto>> deleteDietAllergy(@PathVariable("id") Long id) {
		if (!dietAllergyService.dietAllergyExists(id)) {
			return errorResponse(RestApiResponseStatus.FAILURE, ValidationMessages.DIET_ALLERGY_NOT_FOUND);
		}
		dietAllergyService.deleteDietAllergy(id);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));
	}
}