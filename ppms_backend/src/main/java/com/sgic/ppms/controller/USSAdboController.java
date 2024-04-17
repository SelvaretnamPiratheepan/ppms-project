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

import com.sgic.ppms.dto.USSAdboDto;
import com.sgic.ppms.entities.USSAdbo;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.USSAdboService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.USS_ADBO)
public class USSAdboController {

	@Autowired
	private USSAdboService ussAdboService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> create(@RequestBody USSAdboDto ussAdboDto) {

		if (ussAdboDto.getAdmitId() == null || ussAdboDto.getDate() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.EMPTY_FIELDS, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Long admitId = ussAdboDto.getAdmitId();
		boolean admitFound = ussAdboService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		USSAdbo ussAdbo = ussAdboService.create(ussAdboDto);
		ResponseWrapper<?> createResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				RestApiResponseStatus.CREATED.getStatus(), ussAdbo);
		return ResponseEntity.status(HttpStatus.CREATED).body(createResponseWrapper);

	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> update(@PathVariable("id") Long id, @RequestBody USSAdboDto ussAdboDto) {

		if (ussAdboDto.getAdmitId() == null || ussAdboDto.getDate() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.EMPTY_FIELDS, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		ResponseWrapper<?> notMatchId = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH);

		if (!ussAdboDto.getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notMatchId);
		}

		Long admitId = ussAdboDto.getAdmitId();
		boolean admitFound = ussAdboService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		boolean ussAdboFound = ussAdboService.ussAdboExist(id);
		ResponseWrapper<?> noUSSAdbo = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!ussAdboFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noUSSAdbo);
		}

		USSAdbo updatedUSSAdbo = ussAdboService.update(ussAdboDto);
		ResponseWrapper<?> updateResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedUSSAdbo);
		return ResponseEntity.status(HttpStatus.OK).body(updateResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<?>> getByAdmitId(@PathVariable("admitId") long admitId) {

		boolean admitFound = ussAdboService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		List<USSAdbo> ussAdboList = ussAdboService.getByAdmitId(admitId);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), ussAdboList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable("id") Long id) {

		boolean ussAdboFound = ussAdboService.ussAdboExist(id);
		ResponseWrapper<?> noUSSAdbo = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!ussAdboFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noUSSAdbo);
		}

		Optional<USSAdbo> ussAdbo = ussAdboService.getById(id);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), ussAdbo);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> delete(@PathVariable("id") Long id) {

		boolean ussAdboFound = ussAdboService.ussAdboExist(id);
		ResponseWrapper<?> noUSSAdbo = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!ussAdboFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noUSSAdbo);
		}

		ussAdboService.delete(id);
		ResponseWrapper<?> deleteResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponseWrapper);

	}

}
