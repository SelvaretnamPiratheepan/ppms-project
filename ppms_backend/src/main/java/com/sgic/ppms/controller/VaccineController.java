package com.sgic.ppms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.sgic.ppms.dto.VaccineDetailsDto;
import com.sgic.ppms.entities.VaccineDetails;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.VaccineService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.VACCINE)
public class VaccineController {
	@Autowired
	private VaccineService vaccineService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> create(@RequestBody VaccineDetailsDto vaccineDetailsDto) {

		try {
			VaccineDetails vaccineDetails = vaccineService.addVaccine(vaccineDetailsDto);
			ResponseWrapper<?> createResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					RestApiResponseStatus.CREATED.getStatus(), vaccineDetails);
			return ResponseEntity.status(HttpStatus.CREATED).body(createResponseWrapper);
		} catch (DataIntegrityViolationException e) {
			ResponseWrapper<?> existResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.NAME_EXISTS);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existResponseWrapper);
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateVaccineDetails(@PathVariable("id") Long id,
			@RequestBody VaccineDetailsDto vaccineDetailsDto) {

		ResponseWrapper<?> notMatch = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH);

		if (!vaccineDetailsDto.getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notMatch);
		}

		boolean vaccineFound = vaccineService.vaccineExistById(id);
		ResponseWrapper<?> noVaccine = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!vaccineFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noVaccine);
		}

		try {
			VaccineDetails vaccineDetails = vaccineService.updateVaccineDetails(vaccineDetailsDto);
			ResponseWrapper<?> updateResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
					RestApiResponseStatus.UPDATED.getStatus(), vaccineDetails);
			return ResponseEntity.status(HttpStatus.OK).body(updateResponseWrapper);
		} catch (DataIntegrityViolationException e) {
			ResponseWrapper<?> existResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.NAME_EXISTS);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existResponseWrapper);
		}

	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<?>> getAllVaccineDetails() {

		List<VaccineDetails> vaccineDetailsList = vaccineService.getAllVaccineDetails();
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), vaccineDetailsList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<?> getVaccineDetailById(@PathVariable("id") long id) {

		boolean vaccineFound = vaccineService.vaccineExistById(id);
		ResponseWrapper<?> noVaccine = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!vaccineFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noVaccine);
		}

		Optional<VaccineDetails> vaccineDetails = vaccineService.getVaccineDetailById(id);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), vaccineDetails);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_NAME)
	public ResponseEntity<?> getByVaccineName(@PathVariable("name") String vaccineName) {

		boolean vaccineFound = vaccineService.vaccineExistByName(vaccineName);
		ResponseWrapper<?> noVaccine = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_RECORDS);

		if (!vaccineFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noVaccine);
		}

		Optional<VaccineDetails> vaccineDetails = vaccineService.getVaccineDetailByName(vaccineName);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), vaccineDetails);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<?> deleteVaccineDetails(@PathVariable("id") long id) {

		boolean vaccineFound = vaccineService.vaccineExistById(id);
		ResponseWrapper<?> noVaccine = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!vaccineFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noVaccine);
		}

		vaccineService.deleteVaccineDetails(id);
		ResponseWrapper<?> deleteResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponseWrapper);

	}

}
