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

import com.sgic.ppms.dto.CauseOfDeathDTO;
import com.sgic.ppms.entities.CauseOfDeath;
import com.sgic.ppms.services.CauseOfDeathService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.CAUSE_OF_DEATH)
public class CauseOfDeathController {

	@Autowired
	private CauseOfDeathService causeOfDeathService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<CauseOfDeath>>> getAllCauseOfDeaths() {
		List<CauseOfDeath> allCauseOfDeaths = causeOfDeathService.getAllCaseOfDeaths();
		if (allCauseOfDeaths.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NO_RECORDS, null),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.RETRIEVED, allCauseOfDeaths),
				HttpStatus.OK);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<CauseOfDeath>> getCauseOfDeathById(@PathVariable("id") Long id) {
		Optional<CauseOfDeath> causeOfDeathOptional = causeOfDeathService.getCauseOfDeathById(id);
		if (causeOfDeathOptional.isPresent()) {
			return new ResponseEntity<>(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.RETRIEVED,
					causeOfDeathOptional.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<CauseOfDeath>> createCauseOfDeath(
			@Valid @RequestBody CauseOfDeathDTO causeOfDeathDTO) {
		CauseOfDeath newCauseOfDeath = new CauseOfDeath();
		newCauseOfDeath.setName(causeOfDeathDTO.getName());

		CauseOfDeath savedCauseOfDeath = causeOfDeathService.saveCauseOfDeath(newCauseOfDeath);

		return new ResponseEntity<>(
				new ResponseWrapper<>(HttpStatus.CREATED.value(), ValidationMessages.CREATE_SUCCESS, savedCauseOfDeath),
				HttpStatus.CREATED);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<CauseOfDeath>> updateCauseOfDeath(@PathVariable("id") Long id,
			@Valid @RequestBody CauseOfDeathDTO causeOfDeathDTO) {
		Optional<CauseOfDeath> existingCauseOfDeathOptional = causeOfDeathService.getCauseOfDeathById(id);

		if (existingCauseOfDeathOptional.isPresent()) {
			CauseOfDeath existingCauseOfDeath = existingCauseOfDeathOptional.get();
			existingCauseOfDeath.setName(causeOfDeathDTO.getName());

			CauseOfDeath updatedCauseOfDeath = causeOfDeathService.saveCauseOfDeath(existingCauseOfDeath);

			return new ResponseEntity<>(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.UPDATE_SUCCESS,
					updatedCauseOfDeath), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null),
					HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<Void>> deleteCauseOfDeath(@PathVariable("id") Long id) {
		Optional<CauseOfDeath> existingCauseOfDeathOptional = causeOfDeathService.getCauseOfDeathById(id);

		if (existingCauseOfDeathOptional.isPresent()) {
			causeOfDeathService.deleteCauseOfDeathById(id);
			return new ResponseEntity<>(
					new ResponseWrapper<>(HttpStatus.NO_CONTENT.value(), ValidationMessages.DELETE_SUCCESS, null),
					HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(
					new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null),
					HttpStatus.NOT_FOUND);
		}
	}
}
