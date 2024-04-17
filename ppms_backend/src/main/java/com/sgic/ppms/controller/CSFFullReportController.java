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

import com.sgic.ppms.dto.CSFFullReportDto;
import com.sgic.ppms.entities.CSFFullReport;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.CSFFullReportServices;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.CSF_FULL_REPORT)
public class CSFFullReportController {

	@Autowired
	private CSFFullReportServices csfFullReportServices;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> create(@RequestBody CSFFullReportDto csfFullReportDto) {
		if (csfFullReportDto.getAdmitId() == null) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.ADMIT_NOTNULL));
		}

		if (!csfFullReportServices.admitExists(csfFullReportDto.getAdmitId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID));
		}

		CSFFullReport csfFullReport = csfFullReportServices.create(csfFullReportDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), RestApiResponseStatus.CREATED.getStatus(), csfFullReport));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> update(@PathVariable("id") long id,
			@RequestBody CSFFullReportDto csfFullReportDto) {
		if (csfFullReportDto.getId() != id) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH));
		}

		if (!csfFullReportServices.admitExists(csfFullReportDto.getAdmitId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID));
		}

		if (!csfFullReportServices.csfFullReportExist(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID));
		}

		CSFFullReport updatedCSFFullReport = csfFullReportServices.update(csfFullReportDto);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedCSFFullReport));
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<?>> getByAdmitId(@PathVariable("admitId") long admitId) {
		if (!csfFullReportServices.admitExists(admitId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID));
		}

		List<CSFFullReport> csfFullReportList = csfFullReportServices.getByAdmitId(admitId);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), csfFullReportList));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable("id") long id) {
		if (!csfFullReportServices.csfFullReportExist(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID));
		}

		Optional<CSFFullReport> csfFullReport = csfFullReportServices.getById(id);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), csfFullReport));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> deleteCSFFullReport(@PathVariable("id") long id) {
		if (!csfFullReportServices.csfFullReportExist(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID));
		}

		csfFullReportServices.delete(id);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS));
	}
}
