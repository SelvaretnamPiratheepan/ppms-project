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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.DrugDto;
import com.sgic.ppms.entities.Drug;
import com.sgic.ppms.services.DrugService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.DRUG)
public class DrugController {

	@Autowired
	private DrugService drugService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<Drug>>> getAllDrugs() {
		List<Drug> drugs = drugService.getAllDrugs();
		ResponseWrapper<List<Drug>> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
				ValidationMessages.GET_SUCCESS, drugs);
		return ResponseEntity.ok(responseWrapper);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<Drug>> CreateDrug(@RequestBody DrugDto drugDto) {
		String drugName = drugDto.getName();
		int existingDrugId = drugService.getDrugIdByName(drugName);
		if (existingDrugId != -1) {
			Drug existingDrug = drugService.getDrugById(existingDrugId).orElse(null);
			ResponseWrapper<Drug> responseWrapper = new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(),
					ValidationMessages.NAME_EXISTS, existingDrug);
			return ResponseEntity.badRequest().body(responseWrapper);
		} else {
			Drug drug = drugService.CreateDrug(drugDto);
			ResponseWrapper<Drug> responseWrapper = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					ValidationMessages.CREATE_SUCCESS, drug);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteDrug(@PathVariable("id") int id) {
		boolean deleteStatus = drugService.deleteDrugById(id);
		if (deleteStatus) {
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.DELETE_SUCCESS, null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<Drug>> getDrugById(@PathVariable("id") long id) {
		Optional<Drug> drugOptional = drugService.getDrugById(id);
		if (drugOptional.isPresent()) {
			Drug drug = drugOptional.get();
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.GET_SUCCESS, drug));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

}