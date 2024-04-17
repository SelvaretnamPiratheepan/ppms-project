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

import com.sgic.ppms.dto.OtherTestDto;
import com.sgic.ppms.entities.OtherTest;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.OtherTestService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.OTHER_TEST)
public class OtherTestController {

	@Autowired
	OtherTestService otherTestService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> create(@RequestBody OtherTestDto otherTestDto) {
		if (otherTestDto.getAdmitId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_NULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Long admitId = otherTestDto.getAdmitId();
		boolean admitFound = otherTestService.admitExists(admitId);
		if (!admitFound) {
			ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}
		if (otherTestDto.getOtherTestmId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_NULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Long otherTestMId = otherTestDto.getOtherTestmId();
		boolean otherTestFound = otherTestService.testmExists(otherTestMId);
		if (!otherTestFound) {
			ResponseWrapper<?> noTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_TESTID);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noTest);
		}

		OtherTest otherTest = otherTestService.create(otherTestDto);
		ResponseWrapper<?> createResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				RestApiResponseStatus.CREATED.getStatus(), otherTest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createResponseWrapper);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> update(@PathVariable("id") long id,
			@RequestBody OtherTestDto otherTestDto) {
		ResponseWrapper<?> notMatchId = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.GIVEN_ID_NOT_FOUND);

		if (otherTestDto.getId() != id) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notMatchId);
		}

		boolean otherTestMFound = otherTestService.existsById(id);
		ResponseWrapper<?> noOtherTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.GIVEN_ID_NOT_FOUND);

		if (!otherTestMFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noOtherTest);
		}
		if (otherTestDto.getAdmitId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_NULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Long admitId = otherTestDto.getAdmitId();
		boolean admitFound = otherTestService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}
		if (otherTestDto.getOtherTestmId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_NULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Long otherTestMId = otherTestDto.getOtherTestmId();
		boolean otherTestFound = otherTestService.testmExists(otherTestMId);
		ResponseWrapper<?> noTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_TESTID);

		if (!otherTestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noTest);
		}

		OtherTest updatedOtherTest = otherTestService.update(otherTestDto);
		ResponseWrapper<?> updateResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedOtherTest);
		return ResponseEntity.status(HttpStatus.OK).body(updateResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<?>> getByAdmitId(@PathVariable("admitId") long admitId) {

		boolean admitFound = otherTestService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		List<OtherTest> otherTestList = otherTestService.getByAdmitId(admitId);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), otherTestList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(value = EndpointBundle.GET_BY_TEST_ID)
	public ResponseEntity<ResponseWrapper<?>> getByTestId(@PathVariable("testId") Long testmId) {

		boolean testFound = otherTestService.testmExists(testmId);
		ResponseWrapper<?> noTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_TESTID);

		if (!testFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noTest);
		}

		List<OtherTest> otherTestList = otherTestService.getByTestId(testmId);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), otherTestList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(value = EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable("id") long id) {

		boolean otherTestFound = otherTestService.existsById(id);
		ResponseWrapper<?> noOtherTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.GIVEN_ID_NOT_FOUND);

		if (!otherTestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noOtherTest);
		}

		Optional<OtherTest> otherTest = otherTestService.getById(id);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), otherTest);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@DeleteMapping(value = EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> deleteById(@PathVariable("id") long id) {

		boolean otherTestFound = otherTestService.existsById(id);
		ResponseWrapper<?> noOtherTest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.DELETE_FAILED);

		if (!otherTestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noOtherTest);
		}

		otherTestService.deleteById(id);
		ResponseWrapper<?> deleteResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponseWrapper);

	}

}
