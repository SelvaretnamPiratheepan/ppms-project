package com.sgic.ppms.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.ImmunizationDTO;
import com.sgic.ppms.dto.VaccineDetailAndDueDateDTO;
import com.sgic.ppms.entities.Immunization;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.ImmunizationService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointBundle.IMMUNIZATION)
public class ImmunizationController {

	@Autowired
	private final ImmunizationService immunizationService;

	@GetMapping(EndpointBundle.GET_BASICS)
	public ResponseEntity<?> getAllBasics(
			@Valid @RequestParam("birthDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
		List<VaccineDetailAndDueDateDTO> dueDates = immunizationService.GetBasicsImmunizationDetails(birthDate);
		return ResponseEntity.ok(dueDates);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> createImmunizations(@Valid @RequestBody List<ImmunizationDTO> immunizationDTOs) {

		List<Immunization> response = immunizationService.createImmunizations(immunizationDTOs);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, response));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<?> getImmunizationById(@PathVariable("id") Long id) {
		Immunization response = immunizationService.getImmunizationById(id);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							ValidationMessages.RETRIEVED_FAILED, ValidationMessages.NO_RECORDS + " ID: " + id));
		}

	}

	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<?> getImmunizationsByChildId(@PathVariable("childId") String childId) {
		List<Immunization> response = immunizationService.getAllImmunizationsByChildId(childId);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							ValidationMessages.RETRIEVED_FAILED,
							ValidationMessages.NO_RECORDS + " For Child ID: " + childId));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_AS_LIST)
	public ResponseEntity<?> updateImmunizations(@Valid @RequestBody List<ImmunizationDTO> immunizationDTOs) {

		List<Immunization> updatedImmunizations = new ArrayList<>();
		for (ImmunizationDTO immunizationDTO : immunizationDTOs) {
			Immunization updatedImmunization = immunizationService.updateImmunization(immunizationDTO.getId(),
					immunizationDTO);
			if (updatedImmunization != null) {
				updatedImmunizations.add(updatedImmunization);
			}
		}
		if (!updatedImmunizations.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, updatedImmunizations));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_UPDATED.getCode(), ValidationMessages.UPDATE_FAILED, null));
		}

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<?> deleteImmunization(@PathVariable("id") Long id) {
		boolean status = immunizationService.deleteImmunization(id);
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
