package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.HaematologyDto;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.HaematologyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping(EndpointBundle.HAEMATOLOGY)
public class HaematologyController {

	@Autowired
	private HaematologyService haematologyService;

	private ResponseEntity<ResponseWrapper<HaematologyDto>> handleErrorResponse(HttpStatus status,
			RestApiResponseStatus apiStatus, String message) {
		ResponseWrapper<HaematologyDto> response = new ResponseWrapper<>(apiStatus.getCode(), message, null);
		return ResponseEntity.status(status).body(response);
	}

	private ResponseEntity<ResponseWrapper<List<HaematologyDto>>> handleListResponse(HttpStatus status,
			RestApiResponseStatus apiStatus, String message, List<HaematologyDto> data) {
		ResponseWrapper<List<HaematologyDto>> response = new ResponseWrapper<>(apiStatus.getCode(), message, data);
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<HaematologyDto>> createHaematology(
			@RequestBody @Valid HaematologyDto haematologyDto) {
		HaematologyDto createdHaematology = haematologyService.createHaematology(haematologyDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdHaematology));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<HaematologyDto>> updateHaematology(@PathVariable("id") Long id,
			@RequestBody @Valid HaematologyDto haematologyDto) {
		HaematologyDto updatedHaematology = haematologyService.updateHaematology(id, haematologyDto);
		return ResponseEntity.accepted().body(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				ValidationMessages.UPDATE_SUCCESS, updatedHaematology));
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<List<HaematologyDto>>> getHaematologyByAdmitId(
			@PathVariable("admitId") Long admitId) {
		List<HaematologyDto> haematologyList = haematologyService.getHaematologybyAdmitId(admitId);
		return handleListResponse(HttpStatus.OK, RestApiResponseStatus.FOUND, ValidationMessages.FOUND,
				haematologyList);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<HaematologyDto>> getHaematologyById(@PathVariable("id") Long Id) {
		HaematologyDto haematology = haematologyService.getHaematologybyId(Id);
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(), ValidationMessages.FOUND, haematology));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<HaematologyDto>> deleteById(@PathVariable("id") long id) {
		if (!haematologyService.haematologyFound(id)) {
			return handleErrorResponse(HttpStatus.BAD_REQUEST, RestApiResponseStatus.NOT_FOUND,
					ValidationMessages.INVALID_HAEMATOLOGYID);
		}
		haematologyService.deleteById(id);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));
	}
}