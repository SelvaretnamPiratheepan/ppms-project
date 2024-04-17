package com.sgic.ppms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.sgic.ppms.dto.DevelopmentDetailsDto;
import com.sgic.ppms.entities.DevelopmentDetails;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.DevelopmentService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.DEVELOPMENT)
public class DevelopmentController {

	@Autowired
	private DevelopmentService developmentService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> saveDevelopmentDetails(
			@RequestBody DevelopmentDetailsDto developmentDetailsDto) {

		if (!developmentDetailsDto.getDevelopment().matches("^[A-Za-z\\s]+$")) {
			ResponseWrapper<?> notValid = new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.INVALID_NAME);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notValid);
		}

		try {
			DevelopmentDetails developmentDetails = developmentService.addDevelopmentDetails(developmentDetailsDto);
			ResponseWrapper<?> createResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					RestApiResponseStatus.CREATED.getStatus(), developmentDetails);
			return ResponseEntity.status(HttpStatus.CREATED).body(createResponseWrapper);
		} catch (DataIntegrityViolationException e) {
			ResponseWrapper<?> existResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.NAME_EXISTS);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existResponseWrapper);
		}

	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> updateDevelopmentDetails(@PathVariable("id") long id,
			@RequestBody DevelopmentDetailsDto developmentDetailsDto) {

		ResponseWrapper<?> notMatch = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH);

		if (developmentDetailsDto.getId() != id) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notMatch);
		}

		boolean developmentFound = developmentService.developmentExist(id);
		ResponseWrapper<?> noDevelopment = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!developmentFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noDevelopment);
		}

		if (!developmentDetailsDto.getDevelopment().matches("^[A-Za-z\\s]+$")) {
			ResponseWrapper<?> notValid = new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.INVALID_NAME);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notValid);
		}

		try {
			DevelopmentDetails developmentDetails = developmentService.updateDevelopmentDetails(developmentDetailsDto);
			ResponseWrapper<?> updateResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
					RestApiResponseStatus.UPDATED.getStatus(), developmentDetails);
			return ResponseEntity.status(HttpStatus.OK).body(updateResponseWrapper);
		} catch (DataIntegrityViolationException e) {
			ResponseWrapper<?> existResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.NAME_EXISTS);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existResponseWrapper);
		}

	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<?>> getAllDevelopmentDetails() {

		List<DevelopmentDetails> developmentDetailsList = developmentService.getAllDevelopmentDetails();
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), developmentDetailsList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getDevelopmentDetailsById(@PathVariable("id") long id) {

		boolean developmentFound = developmentService.developmentExist(id);
		ResponseWrapper<?> noDevelopment = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!developmentFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noDevelopment);
		}

		Optional<DevelopmentDetails> developmentDetails = developmentService.getDevelopmentDetailsById(id);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), developmentDetails);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> deleteDevelopmentDetails(@PathVariable("id") long id) {

		boolean developmentFound = developmentService.developmentExist(id);
		ResponseWrapper<?> noDevelopment = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!developmentFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noDevelopment);
		}

		developmentService.deleteDevelopmentDetails(id);
		ResponseWrapper<?> deleteResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponseWrapper);

	}

}
