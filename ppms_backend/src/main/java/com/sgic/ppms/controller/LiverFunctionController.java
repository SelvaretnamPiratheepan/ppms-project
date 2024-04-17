package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.LiverFunctionDto;
import com.sgic.ppms.entities.LiverFunction;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.LiverFunctionService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.LIVER_FUNCTION)
public class LiverFunctionController {
	@Autowired
	private LiverFunctionService liverFunctionService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<LiverFunctionDto>> createLiverFunction(
			@RequestBody @Valid LiverFunctionDto liverFunctionDto) {
		boolean admitfound = liverFunctionService.admitExists(liverFunctionDto.getAdmitId());
		if (!admitfound) {

			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}

		LiverFunction createdLiverFunction = liverFunctionService.createLiverFunction(liverFunctionDto);

		LiverFunctionDto createdLiverFunctionDTO = new LiverFunctionDto();
		BeanUtils.copyProperties(createdLiverFunction, createdLiverFunctionDTO);
		createdLiverFunctionDTO.setAdmitId(createdLiverFunction.getAdmit().getId());

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.CREATE_SUCCESS, createdLiverFunctionDTO));

	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<LiverFunction>> getLiverFunctionById(@PathVariable("id") Long id) {
		LiverFunction liverFunction = liverFunctionService.getLiverFunctionById(id);

		if (liverFunction == null) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.CREATE_FAILED, null));
		}

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.RETRIEVED, liverFunction));
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<LiverFunction>>> getAllLiverFunction() {
		List<LiverFunction> liverFunctions = liverFunctionService.getAllLiverFunction();
		if (!liverFunctions.isEmpty()) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.RETRIEVED, liverFunctions));
		}
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.NOT_FOUND, null));

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<List<LiverFunction>>> getAllLiverFunctionByAdmitId(
			@PathVariable("admitId") Long admitId) {
		boolean admitfound = liverFunctionService.admitExists(admitId);
		if (!admitfound) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}

		List<LiverFunction> liverFunctions = liverFunctionService.getAllLiverFunctionByAdmitId(admitId);
		if (!liverFunctions.isEmpty()) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.RETRIEVED, liverFunctions));
		}
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.NOT_FOUND, null));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<LiverFunction>> updateLiverFunction(@PathVariable("id") Long id,
			@RequestBody LiverFunctionDto liverFunctionDto) {
		boolean admitfound = liverFunctionService.admitExists(liverFunctionDto.getAdmitId());
		if (!admitfound) {

			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}
		LiverFunction liverFunctionUpdated = liverFunctionService.updateLiverFunction(id, liverFunctionDto);
		if (liverFunctionUpdated != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.UPDATE_SUCCESS, liverFunctionUpdated));
		}
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.NOT_FOUND, null));

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteLiverFunction(@PathVariable("id") Long id) {
		LiverFunction liverFunction = liverFunctionService.getLiverFunctionById(id);
		if (liverFunction == null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.NOT_FOUND, null));
		}
		liverFunctionService.deleteLiverFunction(id);

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));

	}
}
