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

import com.sgic.ppms.dto.ExaminationDto;
import com.sgic.ppms.entities.Examination;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.ExaminationService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndpointBundle.EXAMINATION)
@RequiredArgsConstructor
public class ExaminationController {

	@Autowired
	private ExaminationService examinationService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> createExamination(@Valid @RequestBody ExaminationDto examinationDto) {
		Examination response = examinationService.createExamination(examinationDto);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, response));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<?> getExaminationById(@PathVariable("id") Long id) {
		ExaminationDto examination = examinationService.getExaminationById(id);
		if (examination != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, examination));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.RETRIEVED_FAILED, examination));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<?> getExaminationByAdmitId(@PathVariable("admitId") Long admitId) {
		ExaminationDto examination = examinationService.getExaminationByAdmitId(admitId);
		if (examination != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, examination));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.RETRIEVED_FAILED, examination));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<?> getExaminationByChildId(@PathVariable("childId") String childId) {
		List<ExaminationDto> examination = examinationService.getExaminationByChildId(childId);
		if (examination != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, examination));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.RETRIEVED_FAILED, examination));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateExamination(@Valid @PathVariable("id") Long id,
			@RequestBody ExaminationDto examinationDTO) {
		Examination response = examinationService.updateExamination(id, examinationDTO);

		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_UPDATED.getCode(), ValidationMessages.UPDATE_FAILED, response));
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<?> deleteExamination(@PathVariable("id") Long id) {
		Boolean status = examinationService.deleteExamination(id);
		if (status) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatus.DELETED.getCode(), RestApiResponseStatus.DELETED.getStatus(), null));
		} else {
			String msg = ValidationMessages.DELETE_FAILED + ". " + ValidationMessages.NO_RECORDS + " ID: " + id;
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_DELETED.getCode(), RestApiResponseStatus.NOT_DELETED.getStatus(), msg));
		}
	}

}
