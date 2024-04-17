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

import com.sgic.ppms.dto.InflammatoryMarkersDto;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.InflammatoryMarkersService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.INFLAMMATORY)
@Validated
public class InflammatoryMarkersController {

	@Autowired
	private InflammatoryMarkersService inflammatoryMarkersService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<InflammatoryMarkersDto>> createInflammatoryMarkers(
			@RequestBody @Valid InflammatoryMarkersDto inflammatoryMarkersDto) {
		InflammatoryMarkersDto createdInflammatoryMarkers = inflammatoryMarkersService
				.createInflammatoryMarkers(inflammatoryMarkersDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS,
						createdInflammatoryMarkers));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<InflammatoryMarkersDto>> updateInflammatoryMarkers(
			@PathVariable("id") Long id, @RequestBody @Valid InflammatoryMarkersDto inflammatoryMarkersDto) {
		InflammatoryMarkersDto updatedInflammatoryMarkers = inflammatoryMarkersService.updateInflammatoryMarkers(id,
				inflammatoryMarkersDto);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS,
						updatedInflammatoryMarkers));
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<List<InflammatoryMarkersDto>>> getInflammatoryMarkersByAdmitId(
			@PathVariable("admitId") Long admitId) {
		List<InflammatoryMarkersDto> inflammatoryMarkersList = inflammatoryMarkersService
				.getInflammatoryMarkersByAdmitId(admitId);
		return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(),
				ValidationMessages.FOUND, inflammatoryMarkersList));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<InflammatoryMarkersDto>> getInflammatoryMarkersById(
			@PathVariable("id") Long id) {
		InflammatoryMarkersDto inflammatoryMarkers = inflammatoryMarkersService.getInflammatoryMarkersById(id);
		return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(),
				ValidationMessages.FOUND, inflammatoryMarkers));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<InflammatoryMarkersDto>> deletePlaceById(@PathVariable("id") Long id) {
		if (!inflammatoryMarkersService.inflammatoryFound(id)) {
			return handleErrorResponse(HttpStatus.BAD_REQUEST, ValidationMessages.INVALID_INFLAMMATORY_ID);
		}

		inflammatoryMarkersService.deleteInflammatoryMarkersById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseWrapper<>(
				RestApiResponseStatus.DELETED.getCode(), ValidationMessages.DELETE_SUCCESS, null));
	}

	private ResponseEntity<ResponseWrapper<InflammatoryMarkersDto>> handleErrorResponse(HttpStatus status,
			String message) {
		ResponseWrapper<InflammatoryMarkersDto> response = new ResponseWrapper<>(
				RestApiResponseStatus.FAILURE.getCode(), message, null);
		return ResponseEntity.status(status).body(response);
	}

}
