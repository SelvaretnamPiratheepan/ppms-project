package com.sgic.ppms.controller;

import java.util.List;
import java.util.Objects;

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

import com.sgic.ppms.dto.DiagnosisDetailsDto;
import com.sgic.ppms.dto.DischargeDto;
import com.sgic.ppms.entities.Discharge;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.DischargeService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.DISCHARGE)
public class DischargeController {

	@Autowired
	private DischargeService dischargeService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<Discharge>>> getAllDischarges() {
		List<Discharge> discharges = dischargeService.getAllDischarges();
		if (discharges.isEmpty()) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.NO_RECORDS, null));
		}
		return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				ValidationMessages.GET_SUCCESS, discharges), HttpStatus.OK);

	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> createDischarge(@RequestBody DischargeDto dischargeDto) {

		if (dischargeDto.getAdmitId() == null || dischargeDto.getPlaceId() == null
				|| dischargeDto.getCauseOfDeathId() == null) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.WRONG_JSON, dischargeDto));
		}

		if (dischargeService.admitIdExists(dischargeDto.getAdmitId())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ADMITID, dischargeDto));
		}
		if (dischargeService.admitIdExistsInDiagnosisDetails(dischargeDto.getAdmitId())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.ADMITID_EXISTS, dischargeDto));
		}
		if (dischargeService.placeIdExists(dischargeDto.getPlaceId())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.PLACEID_NOT_FOUND, dischargeDto));
		}
		if (dischargeService.causeOfDeathIdExists(dischargeDto.getCauseOfDeathId())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.CAUSEOFDEATHID_NOT_FOUND, dischargeDto));
		}

		DischargeDto response = dischargeService.createDischargeFromDto(dischargeDto);
		if (response != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.CREATE_SUCCESS, response));
		} else {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.CREATE_FAILED, dischargeDto));
		}

	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateDischarge(@PathVariable("id") Long id, @RequestBody DischargeDto dischargeDto) {
		if (dischargeDto.getAdmitId() == null) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.ADMIT_NOTNULL, dischargeDto));
		}

		if (dischargeService.admitIdExists(dischargeDto.getAdmitId())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ADMITID, dischargeDto));
		}
		Long existingAdmitId = dischargeService.getAdmitIdByRecordId(dischargeDto.getId());
		boolean admitIdExistsInDiagnosisDetails = dischargeService
				.admitIdExistsInDiagnosisDetails(dischargeDto.getAdmitId());
		if (!Objects.equals(existingAdmitId, dischargeDto.getAdmitId()) && admitIdExistsInDiagnosisDetails) {
			ResponseWrapper<DischargeDto> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.ADMITID_EXISTS, dischargeDto);
			return ResponseEntity.badRequest().body(response);
		}

		if (dischargeService.placeIdExists(dischargeDto.getPlaceId())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.PLACEID_NOT_FOUND, dischargeDto));
		}

		if (dischargeService.causeOfDeathIdExists(dischargeDto.getCauseOfDeathId())) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.CAUSEOFDEATHID_NOT_FOUND, dischargeDto));
		}

		DischargeDto updatedDischargeDto = dischargeService.updateDischarge(id, dischargeDto);
		if (updatedDischargeDto != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
					ValidationMessages.UPDATE_SUCCESS, updatedDischargeDto));
		} else {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.UPDATE_FAILED, dischargeDto));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<?> getDischargeByAdmitId(@PathVariable("admitId") Long admitId) {

		if (dischargeService.admitIdExists(admitId)) {
			ResponseWrapper<DischargeDto> response = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.NOT_FOUND + ": Admit ID not found in Admit Table", null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		if (!dischargeService.admitIdExistsInDiagnosisDetails(admitId)) {
			ResponseWrapper<List<DiagnosisDetailsDto>> response = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.NOT_FOUND + ": Admit ID not found in discharge Table", null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		DischargeDto dischargeDto = dischargeService.getDischargeByAdmitId(admitId);

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FOUND.getCode(),
				ValidationMessages.GET_SUCCESS, dischargeDto));
	}
}
