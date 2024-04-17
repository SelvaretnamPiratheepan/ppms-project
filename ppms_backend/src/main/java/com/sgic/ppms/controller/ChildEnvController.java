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

import com.sgic.ppms.dto.ChildEnvAllergyDto;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.ChildEnvAllergyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.CHILD_ENV_ALLERGY)
public class ChildEnvController {

	@Autowired
	private ChildEnvAllergyService childEnvAllergyService;

	private ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> errorResponse(HttpStatus status, String message) {
		ResponseWrapper<ChildEnvAllergyDto> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
				message, null);
		return ResponseEntity.status(status).body(response);
	}

	private ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> validateChildEnvAllergyDto(
			ChildEnvAllergyDto childEnvAllergyDto) {

		Long allergyId = childEnvAllergyDto.getAllergyDetailId();
		Long envAllergyId = childEnvAllergyDto.getEnvAllergy().getId();
		boolean relationshipExists = childEnvAllergyService.relationshipExists(allergyId, envAllergyId);
		if (relationshipExists) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.RELATIONSHIP_ALREADY_EXISTS);
		}

		return null;
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> createChildEnvAllergy(
			@RequestBody @Valid ChildEnvAllergyDto childEnvAllergyDto) {
		ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> validationResponse = validateChildEnvAllergyDto(
				childEnvAllergyDto);
		if (validationResponse != null) {
			return validationResponse;
		}
		ChildEnvAllergyDto createdChildEnvironmentalAllergy = childEnvAllergyService
				.createChildEnvironmentalAllergy(childEnvAllergyDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.CREATE_SUCCESS,
						createdChildEnvironmentalAllergy));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> updateChildEnvAllergy(@PathVariable("id") Long id,
			@RequestBody @Valid ChildEnvAllergyDto childEnvAllergyDto) {
		ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> validationResponse = validateChildEnvAllergyDto(
				childEnvAllergyDto);
		if (validationResponse != null) {
			return validationResponse;
		}
		ChildEnvAllergyDto updatedChildEnvAllergy = childEnvAllergyService.updateChildEnvironmentalAllergy(id,
				childEnvAllergyDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				ValidationMessages.UPDATE_SUCCESS, updatedChildEnvAllergy));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> getChildEnvAllergyById(@PathVariable("id") Long id) {
		ChildEnvAllergyDto childEnvAllergy = childEnvAllergyService.getChildEnvironmentalAllergyByid(id);
		ResponseWrapper<ChildEnvAllergyDto> response = new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(),
				ValidationMessages.FOUND, childEnvAllergy);
		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}

	@GetMapping(EndpointBundle.GET_BY_ALLERGY_DETAIL_ID)
	public ResponseEntity<ResponseWrapper<List<ChildEnvAllergyDto>>> getChildEnvAllergyByAllergyId(
			@PathVariable("allergyId") Long id) {
		List<ChildEnvAllergyDto> childEnvAllergyList = childEnvAllergyService
				.getChildEnvironmentalAllergyByAllergyid(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(),
				ValidationMessages.FOUND, childEnvAllergyList));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<ChildEnvAllergyDto>> deleteChildEnvAllergyById(@PathVariable("id") Long id) {
		Boolean childEnvAllergy = childEnvAllergyService.childEnvAllergyFound(id);
		if (!childEnvAllergy) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_ENV_ALLERGY);
		}
		childEnvAllergyService.deleteChildEnvironmentalAllergyById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));
	}
}
