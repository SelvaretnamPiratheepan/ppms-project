package com.sgic.ppms.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

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

import com.sgic.ppms.dto.DiseaseDTO;
import com.sgic.ppms.entities.Disease;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.DiseaseService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.DISEASE)
public class DiseaseController {
	@Autowired
	DiseaseService diseaseService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<Disease>>> getAllDiseases() {
		List<Disease> diseases = diseaseService.getAllDiseases();
		if (diseases == null || diseases.isEmpty()) {
			return status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.NO_RECORDS, null));
		}
		return ok(new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(), ValidationMessages.RETRIEVED, diseases));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteDisease(@PathVariable("id") Long diseaseid) {
		RestApiResponseStatus responseStatus = diseaseService.deleteDisease(diseaseid);

		if (responseStatus == RestApiResponseStatus.DELETED) {
			return ok(
					new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(), ValidationMessages.DELETE_SUCCESS, null));
		} else if (responseStatus == RestApiResponseStatus.NOT_FOUND) {
			return status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.NO_RECORDS, null));
		} else {
			return status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseWrapper<>(RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(),
							RestApiResponseStatus.INTERNAL_SERVER_ERROR.getStatus(), null));
		}
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<Disease>> createDisease(@RequestBody DiseaseDTO diseaseDTO) {

		if (diseaseDTO == null) {
			return badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
					ValidationMessages.WRONG_JSON, null));
		}
		if (!diseaseService.Idexists(diseaseDTO.getId())) {
			return status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ID, null));
		}

		Disease savedDisease = diseaseService.createDisease(diseaseDTO);

		if (savedDisease != null) {
			return status(HttpStatus.CREATED).body(
					new ResponseWrapper<>(HttpStatus.CREATED.value(), ValidationMessages.CREATE_SUCCESS, savedDisease));
		} else {
			return status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), ValidationMessages.CREATE_FAILED, null));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> updateDisease(@PathVariable("id") Long diseaseId,
			@RequestBody DiseaseDTO diseaseDTO) {

		if (diseaseDTO == null) {
			return badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
					RestApiResponseStatus.UPDATED.getStatus(), null));
		}
		if (!diseaseService.existsById(diseaseId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NO_RECORDS, null));
		}

		Disease updatedDiseaseDTO = diseaseService.updateDisease(diseaseId, diseaseDTO);
		if (updatedDiseaseDTO == null) {
			return status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					RestApiResponseStatus.INTERNAL_SERVER_ERROR.getCode(), ValidationMessages.UPDATE_FAILED, null));
		}

		return ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS,
				updatedDiseaseDTO));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<Optional<Disease>>> getDiseaseById(@PathVariable("id") Long diseaseId) {
		if (diseaseId == null || diseaseId <= 0) {
			return badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.BAD_REQUEST.getCode(),
					ValidationMessages.INVALID_ID, null));
		}
		Optional<Disease> disease = diseaseService.getDiseaseById(diseaseId);

		if (disease.isPresent()) {
			return ok(new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(), ValidationMessages.RETRIEVED, disease));
		} else {
			return status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.NO_RECORDS, null));
		}
	}
}
