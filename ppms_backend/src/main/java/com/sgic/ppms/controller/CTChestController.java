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

import com.sgic.ppms.dto.CTChestDto;
import com.sgic.ppms.entities.CTChest;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.CTChestService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.CT_CHEST)
public class CTChestController {

	@Autowired
	private CTChestService ctChestService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> create(@RequestBody CTChestDto ctChestDto) {

		if (ctChestDto.getAdmitId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.ADMIT_NOTNULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Long admitId = ctChestDto.getAdmitId();
		boolean admitFound = ctChestService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		CTChest ctChest = ctChestService.create(ctChestDto);
		ResponseWrapper<?> createResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				RestApiResponseStatus.CREATED.getStatus(), ctChest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createResponseWrapper);

	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> update(@PathVariable("id") Long id, @RequestBody CTChestDto ctChestDto) {

		if (ctChestDto.getAdmitId() == null) {
			ResponseWrapper<?> response = new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.ADMIT_NOTNULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		ResponseWrapper<?> notMatchId = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH);

		if (ctChestDto.getId() != id) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notMatchId);
		}

		Long admitId = ctChestDto.getAdmitId();
		boolean admitFound = ctChestService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		boolean ctChestFound = ctChestService.ctChestExist(id);
		ResponseWrapper<?> noCTChest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!ctChestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noCTChest);
		}

		CTChest updatedCTChest = ctChestService.update(ctChestDto);
		ResponseWrapper<?> updateResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedCTChest);
		return ResponseEntity.status(HttpStatus.OK).body(updateResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<?>> getByAdmitId(@PathVariable("admitId") long admitId) {

		boolean admitFound = ctChestService.admitExists(admitId);
		ResponseWrapper<?> noAdmit = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ADMITID);

		if (!admitFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noAdmit);
		}

		List<CTChest> ctChestList = ctChestService.getByAdmitId(admitId);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), ctChestList);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable("id") Long id) {

		boolean ctChestFound = ctChestService.ctChestExist(id);
		ResponseWrapper<?> noCTChest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!ctChestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noCTChest);
		}

		Optional<CTChest> ctChest = ctChestService.getById(id);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), ctChest);
		return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> delete(@PathVariable("id") Long id) {

		boolean ctChestFound = ctChestService.ctChestExist(id);
		ResponseWrapper<?> noCTChest = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
				RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID);

		if (!ctChestFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noCTChest);
		}

		ctChestService.delete(id);
		ResponseWrapper<?> deleteResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponseWrapper);

	}

}