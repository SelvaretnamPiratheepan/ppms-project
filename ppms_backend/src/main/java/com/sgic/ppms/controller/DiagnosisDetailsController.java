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

import com.sgic.ppms.dto.DiagnosisDetailsDto;
import com.sgic.ppms.entities.DiagnosisDetails;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.DiagnosisDetailsService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.DIAGNOSIS_DETAILS)
public class DiagnosisDetailsController {

	@Autowired
	private DiagnosisDetailsService diagnosisDetailsService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<DiagnosisDetails>>> getAllDiagnosisDetails() {
		List<DiagnosisDetails> diagnosisDetailsList = diagnosisDetailsService.getAllDiagnosisDetails();
		if (diagnosisDetailsList.isEmpty()) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.NO_RECORDS, null));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				ValidationMessages.RETRIEVED, diagnosisDetailsList));
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<List<DiagnosisDetailsDto>>> getDiagnosisDetailsByAdmitId(
			@PathVariable("admitId") Long admitId) {
		if (!diagnosisDetailsService.admitIdExists(admitId)) {
			ResponseWrapper<List<DiagnosisDetailsDto>> response = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_ADMITID, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		if (!diagnosisDetailsService.admitIdExistsInDiagnosisDetails(admitId)) {
			ResponseWrapper<List<DiagnosisDetailsDto>> response = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_ADMITID, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		List<DiagnosisDetailsDto> diagnosisDetailsList = diagnosisDetailsService.getDiagnosisDetailsByAdmitId(admitId);
		if (diagnosisDetailsList.isEmpty()) {
			ResponseWrapper<List<DiagnosisDetailsDto>> response = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_ADMITID, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			ResponseWrapper<List<DiagnosisDetailsDto>> response = new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.GET_SUCCESS, diagnosisDetailsList);
			return ResponseEntity.status(HttpStatus.FOUND).body(response);
		}
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> createDiagnosisDetails(@RequestBody @Valid DiagnosisDetailsDto diagnosisDetailsDto) {

		if (diagnosisDetailsDto.getAdmitId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.ADMIT_NOTNULL, diagnosisDetailsDto));
		}
		if (diagnosisDetailsDto.getDiseaseId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.DISEASE_NOTNULL, diagnosisDetailsDto));
		}
		if (!diagnosisDetailsService.admitIdExists(diagnosisDetailsDto.getAdmitId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.INVALID_ADMITID, diagnosisDetailsDto));
		}
		if (!diagnosisDetailsService.diseaseIdExists(diagnosisDetailsDto.getDiseaseId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
							ValidationMessages.INVALID_DISEASEID, diagnosisDetailsDto));
		}

		boolean relationshipExists = diagnosisDetailsService.relationshipExists(diagnosisDetailsDto.getDiseaseId(),
				diagnosisDetailsDto.getAdmitId());
		if (relationshipExists) {
			ResponseWrapper<DiagnosisDetailsDto> response = new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.RELATIONSHIP_DIAGNOSIS_EXISTS, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		DiagnosisDetails savedDiagnosisDetails = diagnosisDetailsService.createDiagnosisDetails(diagnosisDetailsDto);
		if (savedDiagnosisDetails != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, savedDiagnosisDetails));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
							ValidationMessages.CREATE_FAILED, diagnosisDetailsDto));
		}

	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateDiagnosisDetails(@PathVariable("id") Long id,
			@RequestBody DiagnosisDetailsDto diagnosisDetailsDto) {

		if (diagnosisDetailsDto.getAdmitId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.ADMIT_NOTNULL, diagnosisDetailsDto));
		}
		if (diagnosisDetailsDto.getDiseaseId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.DISEASE_NOTNULL, diagnosisDetailsDto));
		}
		if (!diagnosisDetailsService.admitIdExists(diagnosisDetailsDto.getAdmitId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.INVALID_ADMITID, diagnosisDetailsDto));
		}
		if (!diagnosisDetailsService.diseaseIdExists(diagnosisDetailsDto.getDiseaseId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
							ValidationMessages.INVALID_DISEASEID, diagnosisDetailsDto));
		}

		boolean relationshipExists = diagnosisDetailsService.relationshipExists(diagnosisDetailsDto.getDiseaseId(),
				diagnosisDetailsDto.getAdmitId());
		if (relationshipExists) {
			ResponseWrapper<DiagnosisDetailsDto> response = new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.RELATIONSHIP_DIAGNOSIS_EXISTS, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		DiagnosisDetails updatedDiagnosisDetails = diagnosisDetailsService.updateDiagnosisDetails(id,
				diagnosisDetailsDto);
		if (updatedDiagnosisDetails != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
					ValidationMessages.UPDATE_SUCCESS, updatedDiagnosisDetails));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
							ValidationMessages.CREATE_FAILED, diagnosisDetailsDto));
		}

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<?> deleteDiagnosisDetails(@PathVariable("id") Long id) {
		if (diagnosisDetailsService.existsById(id)) {
			diagnosisDetailsService.deleteDiagnosisDetails(id);
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					ValidationMessages.DELETE_SUCCESS, id));
		} else {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), id));
		}
	}
}
