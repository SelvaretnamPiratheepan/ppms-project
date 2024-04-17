package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.ChildDetailDto;
import com.sgic.ppms.dto.ChildDetailSaveDto;
import com.sgic.ppms.dto.ChildDetailUpdateDto;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.PatientService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.CHILD)
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> addPatient(@RequestBody ChildDetailSaveDto childDetailSaveDto) {
		Long ethnicityId = childDetailSaveDto.getEthnicityId();
		boolean ethnicityFound = patientService.ethnicityExists(ethnicityId);
		ResponseWrapper<?> noEthnicity = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ETHNICITYID);

		if (!ethnicityFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noEthnicity);
		}

		ResponseWrapper<?> invalidDateOfBirth = new ResponseWrapper<>(
				RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
				RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.INVALID_DATE);
		ResponseWrapper<?> invalidName = new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
				RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.INVALID_NAME);

		if (childDetailSaveDto.getDateOfBirth() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidDateOfBirth);
		}
		if (!childDetailSaveDto.getFirstName().matches("^[A-Za-z]+$")
				|| !childDetailSaveDto.getLastName().matches("^[A-Za-z]+$")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidName);
		}

		ChildDetail childDetail = patientService.addPatient(childDetailSaveDto);
		ResponseWrapper<?> createResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				RestApiResponseStatus.CREATED.getStatus(), childDetail);

		return ResponseEntity.status(HttpStatus.CREATED).body(createResponseWrapper);

	}

	@PutMapping(EndpointBundle.UPDATE_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<?>> updateChildDetails(@PathVariable("childId") String childId,
			@RequestBody ChildDetailUpdateDto childDetailUpdateDto) {

		ResponseWrapper<?> notMatchChildId = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH);

		if (!childDetailUpdateDto.getChildId().matches(childId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notMatchChildId);
		}

		boolean childFound = patientService.childExists(childId);
		ResponseWrapper<?> noChild = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_CHILDID);

		if (!childFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noChild);
		}

		Long ethnicityId = childDetailUpdateDto.getEthnicityId();
		boolean ethnicityFound = patientService.ethnicityExists(ethnicityId);
		ResponseWrapper<?> noEthnicity = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ETHNICITYID);

		if (!ethnicityFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noEthnicity);
		}

		ResponseWrapper<?> invalidName = new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
				RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.INVALID_NAME);

		if (!childDetailUpdateDto.getFirstName().matches("^[A-Za-z]+$")
				|| !childDetailUpdateDto.getLastName().matches("^[A-Za-z]+$")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidName);
		}

		ChildDetail updatedChildDetail = patientService.updateChildDetails(childDetailUpdateDto);
		ResponseWrapper<?> updateResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedChildDetail);
		return ResponseEntity.status(HttpStatus.OK).body(updateResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<?>> getAllChild() {
		List<ChildDetailDto> childDetailDtoList = patientService.getAllChild();
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
				RestApiResponseStatus.OK.getStatus(), childDetailDtoList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(EndpointBundle.SEARCH)
	public ResponseEntity<ResponseWrapper<?>> getListOfChildDetailByAnything(@PathVariable String anything) {
		List<ChildDetailDto> childDetailsByAnything = patientService.getPatientsByAnything(anything);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
				RestApiResponseStatus.OK.getStatus(), childDetailsByAnything);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

}
