// PastHistoryController.java
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

import com.sgic.ppms.dto.PastHistoryDto;
import com.sgic.ppms.entities.PastHistory;
import com.sgic.ppms.services.PastHistoryService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.PAST_HISTORY)
public class PastHistoryController {

	@Autowired
	private PastHistoryService pastHistoryService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<PastHistory>> createPastHistory(
			@Valid @RequestBody PastHistoryDto pastHistoryDto) {
		PastHistory pastHistory = pastHistoryService.createPastHistory(pastHistoryDto);

		if (pastHistory != null) {
			ResponseWrapper<PastHistory> responseWrapper = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					ValidationMessages.CREATE_SUCCESS, pastHistory);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);

		} else {
			ResponseWrapper<PastHistory> responseWrapper = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					ValidationMessages.CREATE_FAILED, pastHistory);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		}
	}

	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<List<PastHistory>>> getPastHistoryByChildId(
			@PathVariable("childId") String childId) {
		List<PastHistory> pastHistories = pastHistoryService.getPastHistoryByChildId(childId);

		if (pastHistories != null) {
			ResponseWrapper<List<PastHistory>> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
					ValidationMessages.RETRIEVED, pastHistories);
			return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
		} else {
			ResponseWrapper<List<PastHistory>> responseWrapper = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(),
					ValidationMessages.RETRIEVED_FAILED, pastHistories);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<PastHistory>> updatePastHistory(@PathVariable("id") int Id,
			@Valid @RequestBody PastHistoryDto pastHistoryDto) {
		PastHistory updatedPastHistory = pastHistoryService.updatePastHistory(Id, pastHistoryDto);
		if (updatedPastHistory != null) {
			ResponseWrapper<PastHistory> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
					ValidationMessages.UPDATE_SUCCESS, updatedPastHistory);
			return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
		} else {
			ResponseWrapper<PastHistory> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
					ValidationMessages.UPDATE_FAILED, updatedPastHistory);
			return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<String>> deletePastHistory(@PathVariable("childId") String childId) {
		boolean response = pastHistoryService.deletePastHistory(childId);
		if (response) {
			ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
					ValidationMessages.DELETE_SUCCESS, null);
			return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
		} else {
			ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
					ValidationMessages.GIVEN_ID_NOT_FOUND, null);
			return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
		}
	}
}
