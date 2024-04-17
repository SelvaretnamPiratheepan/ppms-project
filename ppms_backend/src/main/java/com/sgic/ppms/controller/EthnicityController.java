
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

import com.sgic.ppms.dto.EthnicityDto;
import com.sgic.ppms.entities.Ethnicity;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.EthnicityServices;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.ETHNICITY)
public class EthnicityController {

	@Autowired
	private EthnicityServices ethnicityServices;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> createEthnicity(@RequestBody @Valid EthnicityDto ethnicityDto) {
		Ethnicity response = ethnicityServices.createEthnicity(ethnicityDto);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, response));
		}
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<Object> getAllEthnicities() {
		List<Ethnicity> ethnicities = ethnicityServices.getAllEthnicities();
		if (ethnicities != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, ethnicities));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.RETRIEVED_FAILED, ethnicities));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getEthnicityById(@PathVariable("id") long id) {
		Ethnicity ethnicity = ethnicityServices.getEthnicityById(id);
		if (ethnicity != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, ethnicity));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							ValidationMessages.RETRIEVED_FAILED, ValidationMessages.NO_RECORDS));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateEthnicity(@PathVariable("id") long id,
			@Valid @RequestBody EthnicityDto updatedEthnicityDto) {
		Ethnicity response = ethnicityServices.updateEthnicity(id, updatedEthnicityDto);

		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, response));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_UPDATED.getCode(),
							ValidationMessages.UPDATE_FAILED, ValidationMessages.INVALID_ID));
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<?> deleteEthnicity(@PathVariable("id") long id) {
		boolean status = ethnicityServices.deleteEthnicity(id);
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
