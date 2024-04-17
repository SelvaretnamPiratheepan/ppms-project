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

import com.sgic.ppms.dto.AllergyDetailDto;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.AllergyDetailService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.ALLERGY_DETAIL)
public class AllergyDetailController {

	@Autowired
	private AllergyDetailService allergyDetailService;

	private ResponseEntity<ResponseWrapper<AllergyDetailDto>> validateChildId(String childId) {
		if (childId == null) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.EMPTY_FIELDS);
		}

		boolean childFound = allergyDetailService.childFound(childId);
		if (!childFound) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_CHILDID);
		}

		return null;
	}

	private ResponseEntity<ResponseWrapper<AllergyDetailDto>> errorResponse(HttpStatus status, String message) {
		ResponseWrapper<AllergyDetailDto> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
				message, null);
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<AllergyDetailDto>> createAllergyDetail(
			@RequestBody AllergyDetailDto allergyDetailDto) {
		ResponseEntity<ResponseWrapper<AllergyDetailDto>> validationResponse = validateChildId(
				allergyDetailDto.getChildId());
		if (validationResponse != null) {
			return validationResponse;
		}

		boolean childExists = allergyDetailService.childExistsAllergy(allergyDetailDto.getChildId());
		if (childExists) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.CHILDID_EXISTS);
		}

		AllergyDetailDto createdPlan = allergyDetailService.createAllergyDetail(allergyDetailDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdPlan));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<AllergyDetailDto>> updateAllergyDetail(@PathVariable("id") Long id,
			@RequestBody AllergyDetailDto allergyDetailDto) {
		ResponseEntity<ResponseWrapper<AllergyDetailDto>> validationResponse = validateChildId(
				allergyDetailDto.getChildId());
		if (validationResponse != null) {
			return validationResponse;
		}
		boolean childFound = allergyDetailService.childFound(allergyDetailDto.getChildId());
		if (!childFound) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_CHILDID);
		}
		boolean childExists = allergyDetailService.childExistsAllergy(allergyDetailDto.getChildId());
		String childId2 = allergyDetailService.getChildIdById(id);
		if (childExists && !allergyDetailDto.getChildId().equals(childId2)) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.CHILDID_EXISTS);
		}
		AllergyDetailDto updatedPlan = allergyDetailService.updateAllergyDetail(id, allergyDetailDto);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseWrapper<>(
				RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.UPDATE_SUCCESS, updatedPlan));
	}

	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<AllergyDetailDto>> getAllergyDetailByChildId(
			@PathVariable("childId") String childId) {
		ResponseEntity<ResponseWrapper<AllergyDetailDto>> validationResponse = validateChildId(childId);
		if (validationResponse != null) {
			return validationResponse;
		}
		boolean childExists = allergyDetailService.childExistsAllergy(childId);
		if (!childExists) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_CHILDID);
		}
		AllergyDetailDto plan = allergyDetailService.getAllergyDetailByChildid(childId);
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(), ValidationMessages.FOUND, plan));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<AllergyDetailDto>> getAllergyDetailById(@PathVariable("id") Long Id) {
		boolean allergyFound = allergyDetailService.allergyDetailExistsById(Id);
		if (!allergyFound) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_ALLERGYID);
		}

		AllergyDetailDto plan = allergyDetailService.getAllergyDetailById(Id);
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(), ValidationMessages.FOUND, plan));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<AllergyDetailDto>> deleteAllergyDetailById(@PathVariable("id") Long id) {
		boolean allergyDetail = allergyDetailService.allergyDetailExistsById(id);
		if (!allergyDetail) {
			return errorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_ALLERGYID);
		}
		allergyDetailService.deleteAllergyDetailsById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));
	}
}