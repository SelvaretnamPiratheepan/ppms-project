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

import com.sgic.ppms.dto.BirthHistoryDto;
import com.sgic.ppms.entities.BirthHistory;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.BirthHistoryService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.BIRTH_HISTORY)
public class BirthHistoryController {

	@Autowired
	private BirthHistoryService service;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<BirthHistory>> createBirthHistory(
			@Valid @RequestBody BirthHistoryDto birthHistoryDto) {
		BirthHistory createdBirthHistory = service.createBirthHistory(birthHistoryDto);
		if (createdBirthHistory != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdBirthHistory));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, null));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<?> getBirthHistoryById(@PathVariable("id") Long id) {
		BirthHistory birthHistory = service.getBirthHistoryById(id);
		if (birthHistory != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
					ValidationMessages.RETRIEVED, birthHistory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.RETRIEVED_FAILED
									+ ValidationMessages.NO_RECORDS + ValidationMessages.INVALID_ID));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<?> getBirthHistoryByChildId(@PathVariable("childId") String childId) {
		BirthHistory birthHistory = service.getBirthHistoryByChildId(childId);
		if (birthHistory != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, birthHistory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.RETRIEVED_FAILED, birthHistory));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateBirthHistory(@PathVariable("id") Long id,
			@Valid @RequestBody BirthHistoryDto updatedBirthHistoryDto) {
		BirthHistory updatedBirthHistory = service.updateBirthHistory(id, updatedBirthHistoryDto);
		if (updatedBirthHistory != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
					RestApiResponseStatus.OK.getStatus(), updatedBirthHistory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_UPDATED.getCode(), RestApiResponseStatus.NOT_UPDATED.getStatus(),
					ValidationMessages.UPDATE_FAILED + ValidationMessages.NO_RECORDS + ValidationMessages.INVALID_ID));
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteBirthHistoryById(@PathVariable("id") Long id) {
		boolean deleted = service.deleteBirthHistoryById(id);
		if (deleted) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_DELETED.getCode(), RestApiResponseStatus.NOT_DELETED.getStatus(),
					ValidationMessages.DELETE_FAILED + ValidationMessages.NO_RECORDS + ValidationMessages.INVALID_ID));
		}
	}

}
