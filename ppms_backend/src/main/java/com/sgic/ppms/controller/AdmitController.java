package com.sgic.ppms.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.sgic.ppms.dto.AdmitDTO;
import com.sgic.ppms.enums.AdmitType;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.AdmitService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.ADMIT)
public class AdmitController {

	@Autowired
	private AdmitService admitService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<AdmitDTO>> createAdmit(@RequestBody @Valid AdmitDTO admitDTO) {
		if (isInvalidAdmitDTO(admitDTO)) {
			return ResponseEntity.badRequest().body(failureResponse(ValidationMessages.EMPTY_FIELDS));
		}

		if (admitDTO.getAdmitType() == AdmitType.NEW_ADMIT && admitDTO.getTransferDate() != null) {
			return ResponseEntity.badRequest().body(failureResponse(ValidationMessages.TRANSFER_DATE_NOT_NULL));
		}

		if (admitDTO.getAdmitType() == AdmitType.TRANSFER_IN && admitDTO.getDate() != null) {
			return ResponseEntity.badRequest().body(failureResponse(ValidationMessages.DATE_NOT_NULL));
		}

		AdmitDTO createdAdmit = admitService.createAdmit(admitDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdAdmit));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<AdmitDTO>> getAdmitById(@PathVariable("id") Long id) {
		AdmitDTO admitDTOFound = admitService.getAdmitById(id);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(), ValidationMessages.FOUND, admitDTOFound));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<AdmitDTO>> updateAdmit(@PathVariable("id") Long id,
			@RequestBody @Valid AdmitDTO admitDTO) {
		if (isInvalidAdmitDTO(admitDTO)) {
			return ResponseEntity.badRequest().body(failureResponse(ValidationMessages.EMPTY_FIELDS));
		}

		if (admitDTO.getAdmitType() == AdmitType.NEW_ADMIT && admitDTO.getTransferDate() != null) {
			return ResponseEntity.badRequest().body(failureResponse(ValidationMessages.TRANSFER_DATE_NOT_NULL));
		}

		if (admitDTO.getAdmitType() == AdmitType.TRANSFER_IN && admitDTO.getDate() != null) {
			return ResponseEntity.badRequest().body(failureResponse(ValidationMessages.DATE_NOT_NULL));
		}

		AdmitDTO updatedAdmit = admitService.updateAdmit(id, admitDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				ValidationMessages.UPDATE_SUCCESS, updatedAdmit));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<AdmitDTO>> deleteAdmit(@PathVariable("id") Long id) {
		if (!admitService.admitExists(id)) {
			return ResponseEntity.badRequest().body(failureResponse(ValidationMessages.INVALID_ADMITID));
		}

		admitService.deleteAdmit(id);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));
	}

	@GetMapping(EndpointBundle.GET_BY_BETWEEN_DATES)
	public ResponseEntity<ResponseWrapper<List<AdmitDTO>>> getAdmitsBetweenDates(
			@PathVariable("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<AdmitDTO> admitsBetweenDates = admitService.getAdmitsBetweenDates(fromDate, endDate);
		if (admitsBetweenDates.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.NO_ADMITS_BETWEEN_DATES, null));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(), ValidationMessages.FOUND,
				admitsBetweenDates));
	}

	private boolean isInvalidAdmitDTO(AdmitDTO admitDTO) {
		return (admitDTO.getDate() == null && admitDTO.getTransferDate() == null);
	}

	private ResponseWrapper<AdmitDTO> failureResponse(String message) {
		return new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), message, null);
	}
}
