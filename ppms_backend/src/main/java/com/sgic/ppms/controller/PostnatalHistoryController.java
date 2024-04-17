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

import com.sgic.ppms.dto.PostnatalHistoryDto;
import com.sgic.ppms.entities.PostnatalHistory;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.PostnatalHistoryService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.POSTNATAL_HISTORY)
public class PostnatalHistoryController {

	@Autowired
	private PostnatalHistoryService postnatalHistoryService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<PostnatalHistory>> createPostnatalHistory(
			@Valid @RequestBody PostnatalHistoryDto postnatalHistoryDto) {
		PostnatalHistory createdPostnatalHistory = postnatalHistoryService.createPostnatalHistory(postnatalHistoryDto);
		if (createdPostnatalHistory != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
							ValidationMessages.CREATE_SUCCESS, createdPostnatalHistory));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, null));
		}
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<?> getAllPostnatalHistory() {
		List<PostnatalHistory> postnatal = postnatalHistoryService.getAllPostnatalHistory();
		if (postnatal.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_RECORDS));
		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, postnatal));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getPostnatalHistoryById(@PathVariable("id") Long id) {
		PostnatalHistory postnatalHistory = postnatalHistoryService.getPostnatalHistoryById(id);
		if (postnatalHistory != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
					ValidationMessages.RETRIEVED, postnatalHistory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.RETRIEVED_FAILED
									+ ValidationMessages.NO_RECORDS + ValidationMessages.INVALID_ID));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> updatePostnatalHistory(@PathVariable("id") Long id,
			@Valid @RequestBody PostnatalHistoryDto updatedPostnatalHistoryDto) {
		PostnatalHistory updatedPostnatalHistory = postnatalHistoryService.updatePostnatalHistoryById(id,
				updatedPostnatalHistoryDto);
		if (updatedPostnatalHistory != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
					RestApiResponseStatus.OK.getStatus(), updatedPostnatalHistory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_UPDATED.getCode(), RestApiResponseStatus.NOT_UPDATED.getStatus(),
					ValidationMessages.UPDATE_FAILED + ValidationMessages.NO_RECORDS + ValidationMessages.INVALID_ID));
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deletePostnatalHistoryById(@PathVariable("id") Long id) {
		boolean deleted = postnatalHistoryService.deletePostnatalHistoryById(id);
		if (deleted) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
					RestApiResponseStatus.OK.getStatus(), ValidationMessages.DELETE_SUCCESS));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_DELETED.getCode(), RestApiResponseStatus.NOT_DELETED.getStatus(),
					ValidationMessages.DELETE_FAILED + ValidationMessages.NO_RECORDS + ValidationMessages.INVALID_ID));
		}
	}
}
