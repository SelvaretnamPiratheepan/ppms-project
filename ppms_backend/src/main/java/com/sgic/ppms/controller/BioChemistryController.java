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

import com.sgic.ppms.dto.BioChemistryDto;
import com.sgic.ppms.entities.BioChemistry;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.BioChemistryService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.BIOCHEMISTRY)
public class BioChemistryController {

	@Autowired
	private BioChemistryService bioChemistryService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<BioChemistry>> createBioChemistry(
			@RequestBody @Valid BioChemistryDto bioChemistryDto) {
		BioChemistry response = bioChemistryService.createBioChemistry(bioChemistryDto);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, response));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, null));
		}
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<BioChemistry>>> getAllBioChemistries() {
		List<BioChemistry> bioChemistries = bioChemistryService.getAllBioChemistries();
		if (!bioChemistries.isEmpty()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, bioChemistries));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NO_RECORDS, bioChemistries));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<Object>> getBioChemistryById(@PathVariable("id") long id) {
		BioChemistry bioChemistry = bioChemistryService.getBioChemistryById(id);
		if (bioChemistry != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, bioChemistry));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							ValidationMessages.RETRIEVED_FAILED, ValidationMessages.NO_RECORDS));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<Object>> updateBioChemistry(@PathVariable("id") long id,
			@Valid @RequestBody BioChemistryDto updatedBioChemistryDto) {
		BioChemistry response = bioChemistryService.updateBioChemistry(id, updatedBioChemistryDto);
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
	public ResponseEntity<ResponseWrapper<?>> deleteBioChemistry(@PathVariable("id") long id) {
		boolean status = bioChemistryService.deleteBioChemistry(id);
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
