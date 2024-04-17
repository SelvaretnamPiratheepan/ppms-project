package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.OtherTestMDto;
import com.sgic.ppms.entities.OtherTestM;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.OtherTestMService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.OTHER_TEST_M)
public class OtherTestMController {

	@Autowired
	private OtherTestMService otherTestMService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<?>> getAllDetails() {
		List<OtherTestM> otherTestDetails = otherTestMService.getAllDetails();
		ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), otherTestDetails);
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> createOtherTestM(@RequestBody OtherTestMDto otherTestMDto) {
		if (otherTestMService.existsByTestname(otherTestMDto.getTestname())) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.NAME_EXISTS);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
		}
		if (otherTestMDto.getId() == null
				&& (otherTestMDto.getTestname() == null || otherTestMDto.getTestname().isEmpty())) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.NOT_NULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
		}

		OtherTestM otherTestM = otherTestMService.createOtherTestM(otherTestMDto);

		if (otherTestM != null) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					RestApiResponseStatus.CREATED.getStatus(), otherTestM);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		} else {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					RestApiResponseStatus.NOT_CREATED.getStatus(), ValidationMessages.CREATE_FAILED);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable("id") Long id) {
		OtherTestM otherTestM = otherTestMService.getById(id);
		if (otherTestM != null) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
					RestApiResponseStatus.RETRIEVED.getStatus(), otherTestM);
			return ResponseEntity.ok().body(responseWrapper);
		} else {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.GIVEN_ID_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> deleteById(@PathVariable("id") Long id) {
		boolean deleted = otherTestMService.deleteById(id);
		if (deleted) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
			return ResponseEntity.ok().body(responseWrapper);
		} else {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.DELETE_FAILED);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}
}
