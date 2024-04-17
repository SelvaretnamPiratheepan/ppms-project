package com.sgic.ppms.controller;

import java.util.Optional;

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

import com.sgic.ppms.dto.AnthropometryDto;
import com.sgic.ppms.entities.Anthropometry;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.AnthropometryService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.ANTHROPOMETRY)
public class AnthropometryController {

	private final AnthropometryService anthropometryService;

	public AnthropometryController(AnthropometryService anthropometryService) {
		this.anthropometryService = anthropometryService;
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> createAnthropometry(@Valid @RequestBody AnthropometryDto anthropometryDTO) {
		try {
			Anthropometry response = anthropometryService.createAnthropometry(anthropometryDTO);
			if (response != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
						RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, response));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseWrapper<>(
						RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, response));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ResponseWrapper<>(RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<?> getAnthropometryById(@PathVariable("id") Long id) {
		Optional<Anthropometry> response = anthropometryService.getAnthropometryById(id);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.RETRIEVED_FAILED, response));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateAnthropometry(@Valid @PathVariable("id") Long id,
			@RequestBody AnthropometryDto anthropometryDTO) {
		Anthropometry response = anthropometryService.updateAnthropometry(id, anthropometryDTO);

		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, response));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_UPDATED.getCode(),
							ValidationMessages.UPDATE_FAILED, ValidationMessages.INVALID_ID));
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<?> deleteAnthropometry(@PathVariable("id") Long id) {
		boolean status = anthropometryService.deleteAnthropometry(id);
		if (status) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
					RestApiResponseStatus.DELETED.getCode(), RestApiResponseStatus.DELETED.getStatus(), null));
		} else {
			String msg = ValidationMessages.DELETE_FAILED + ". " + ValidationMessages.NO_RECORDS + id;
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_DELETED.getCode(), RestApiResponseStatus.NOT_DELETED.getStatus(), msg));
		}
	}
}
