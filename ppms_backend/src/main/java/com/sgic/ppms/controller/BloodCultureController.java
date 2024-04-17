package com.sgic.ppms.controller;

import java.util.List;

import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

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

import com.sgic.ppms.dto.BloodCultureDto;
import com.sgic.ppms.entities.BloodCultureDetailEntity;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.BloodCultureService;
import com.sgic.ppms.util.EndpointBundle;

@RestController
@RequestMapping(EndpointBundle.BLOOD_CULTURE)
public class BloodCultureController {


	public BloodCultureController() {
	}


	@Autowired
	public BloodCultureService bloodCultureService;




	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity <?> createBloodCultureDetail(@RequestBody BloodCultureDto bloodCultureDto) {
		String childId = bloodCultureDto.getChildDetailId();
		boolean childFound = bloodCultureService.childExists(childId);
		ResponseWrapper<?> noChild = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_CHILD);

		if (!childFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noChild);
		}
		if (bloodCultureDto.getBloodCulture()!= null && bloodCultureDto.getDate() != null) {
			BloodCultureDetailEntity createdBloodCultureDetail = bloodCultureService.createBloodCultureDetail(bloodCultureDto);
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.CREATE_SUCCESS, createdBloodCultureDetail));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, ValidationMessages.EMPTY_FIELDS));
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<BloodCultureDetailEntity>>> getAllBloodCultureDetail() {

		List<BloodCultureDetailEntity> getAllBloodCultureDetail =bloodCultureService.getAllBloodCultureDetail();
		return new ResponseEntity<>(new ResponseWrapper<>(
				RestApiResponseStatus.RETRIEVED.getCode(),
				ValidationMessages.GET_SUCCESS, getAllBloodCultureDetail), HttpStatus.OK);
	}

	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<?>> getByChildId(@PathVariable("childId") String childId) {

		boolean childFound = bloodCultureService.isBloodCultureExists(childId);
		ResponseWrapper<?> noChild = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_CHILD);

		if (!childFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noChild);
		}

		List<BloodCultureDetailEntity> bloodCultureList = bloodCultureService.getbloodCultureByChildId(childId);
		ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), RestApiResponseStatus.RETRIEVED.getStatus(), bloodCultureList);

			return	ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);

	}


	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> updateBloodDetail(@PathVariable("id") Long id,@RequestBody BloodCultureDto updatedBloodCulturelDto)
	{
		updatedBloodCulturelDto.setId(id);
		if (bloodCultureService.isBloodCultureExists(id))
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.INVALID_ID, null));
		String childId = updatedBloodCulturelDto.getChildDetailId();
		boolean childFound = bloodCultureService.isBloodCultureExists(childId) ;
		ResponseWrapper<?> noChild = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_CHILD);
		if (!childFound)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noChild);
		else {
		BloodCultureDetailEntity updatedBloodDetail = bloodCultureService.updateBloodDetail(id, updatedBloodCulturelDto);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, updatedBloodDetail));
		}
	}


	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteBloodCultureDetail(@PathVariable("id") Long id) {
		if (bloodCultureService.isBloodCultureExists(id)) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ID, null));
		}

		RestApiResponseStatus status = bloodCultureService.deleteBloodCultureDetail(id);
		if (status == RestApiResponseStatus.OK) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					ValidationMessages.DELETE_SUCCESS, null));
		} else {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.DELETE_FAILED, null));
		}
	}


}
