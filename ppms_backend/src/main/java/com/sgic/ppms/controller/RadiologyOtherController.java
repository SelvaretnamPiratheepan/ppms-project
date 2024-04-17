package com.sgic.ppms.controller;

import java.util.List;
import java.util.Optional;

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

import com.sgic.ppms.dto.RadiologyOtherDto;
import com.sgic.ppms.entities.RadiologyOther;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.RadiologyOtherService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.RADIOLOGY_OTHER)
public class RadiologyOtherController {

	@Autowired
	RadiologyOtherService radiologyOtherService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> create(@RequestBody RadiologyOtherDto radiologyOtherDto) {

		if (radiologyOtherDto.getAdmitId() == null || radiologyOtherDto.getOtherTestMId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.EMPTY_FIELDS, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Long admitId = radiologyOtherDto.getAdmitId();
		boolean admitFound = radiologyOtherService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		Long otherTestMId = radiologyOtherDto.getOtherTestMId();
		boolean otherTestFound = radiologyOtherService.testExists(otherTestMId);
		ResponseWrapper<?> noTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_TESTID);

		if (!otherTestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noTest);
		}

		RadiologyOther radiologyOther = radiologyOtherService.create(radiologyOtherDto);
		ResponseWrapper<?> createResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				RestApiResponseStatus.CREATED.getStatus(), radiologyOther);
		return ResponseEntity.status(HttpStatus.CREATED).body(createResponseWrapper);

	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> update(@PathVariable("id") Long id,
			@RequestBody RadiologyOtherDto radiologyOtherDto) {

		if (radiologyOtherDto.getAdmitId() == null || radiologyOtherDto.getOtherTestMId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.EMPTY_FIELDS, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		ResponseWrapper<?> notMatchId = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH);

		if (!radiologyOtherDto.getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notMatchId);
		}

		Long admitId = radiologyOtherDto.getAdmitId();
		boolean admitFound = radiologyOtherService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		Long otherTestMId = radiologyOtherDto.getOtherTestMId();
		boolean otherTestFound = radiologyOtherService.testExists(otherTestMId);
		ResponseWrapper<?> noTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_TESTID);

		if (!otherTestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noTest);
		}

		boolean radiologyOtherFound = radiologyOtherService.radiologyOtherExist(id);
		ResponseWrapper<?> noRadiologyOther = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!radiologyOtherFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noRadiologyOther);
		}

		RadiologyOther updatedRadiologyOther = radiologyOtherService.update(radiologyOtherDto);
		ResponseWrapper<?> updateResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedRadiologyOther);
		return ResponseEntity.status(HttpStatus.OK).body(updateResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<?>> getByAdmitId(@PathVariable("admitId") long admitId) {

		boolean admitFound = radiologyOtherService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		List<RadiologyOther> radiologyOtherList = radiologyOtherService.getByAdmitId(admitId);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), radiologyOtherList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(value = EndpointBundle.GET_BY_TEST_ID)
	public ResponseEntity<ResponseWrapper<?>> getByTestId(@PathVariable("testId") Long testId) {

		boolean testFound = radiologyOtherService.testExists(testId);
		ResponseWrapper<?> noTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_TESTID);

		if (!testFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noTest);
		}

		List<RadiologyOther> radiologyOtherList = radiologyOtherService.getByTestId(testId);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), radiologyOtherList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(value = EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable("id") long id) {

		boolean radiologyOtherFound = radiologyOtherService.radiologyOtherExist(id);
		ResponseWrapper<?> noRadiologyOther = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!radiologyOtherFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noRadiologyOther);
		}

		Optional<RadiologyOther> radiologyOther = radiologyOtherService.getById(id);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), radiologyOther);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@DeleteMapping(value = EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> deleteById(@PathVariable("id") long id) {

		boolean radiologyOtherFound = radiologyOtherService.radiologyOtherExist(id);
		ResponseWrapper<?> noRadiologyOther = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!radiologyOtherFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noRadiologyOther);
		}

		radiologyOtherService.delete(id);
		ResponseWrapper<?> deleteResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponseWrapper);

	}

}
