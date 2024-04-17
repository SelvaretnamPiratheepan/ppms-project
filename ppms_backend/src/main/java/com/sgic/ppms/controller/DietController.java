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

import com.sgic.ppms.dto.DietDto;
import com.sgic.ppms.entities.DietDetails;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.DietServices;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.DIET)
public class DietController {
	@Autowired
	private DietServices dietServices;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> create(@RequestBody DietDto dietDto) {
		if (dietDto.getChildDetailId() == null || dietDto.getFoodListId() == null) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					RestApiResponseStatus.FAILURE.getStatus(), ValidationMessages.EMPTY_FIELDS));
		}

		if (!dietServices.childExists(dietDto.getChildDetailId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_CHILDID));
		}

		if (!dietServices.foodExists(dietDto.getFoodListId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_FOODID));
		}

		DietDetails dietDetails = dietServices.create(dietDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), RestApiResponseStatus.CREATED.getStatus(), dietDetails));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> update(@PathVariable("id") Long id, @RequestBody DietDto dietDto) {
		if (!dietDto.getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NOT_MATCH));
		}

		if (!dietServices.childExists(dietDto.getChildDetailId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_CHILDID));
		}

		if (!dietServices.foodExists(dietDto.getFoodListId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_FOODID));
		}

		if (!dietServices.dietExist(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID));
		}

		DietDetails updatedDietDetails = dietServices.update(dietDto);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedDietDetails));
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<?>> getAllDiets() {
		List<DietDetails> dietDetailsList = dietServices.getAllDiets();
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), dietDetailsList));
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getDietDetailsById(@PathVariable("id") long id) {
		if (!dietServices.dietExist(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID));
		}

		Optional<DietDetails> dietDetails = dietServices.getById(id);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), dietDetails));
	}

	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<?>> getByChildId(@PathVariable("childId") String childId) {
		if (!dietServices.childExists(childId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_CHILDID));
		}

		List<DietDetails> dietDetailsList = dietServices.getByChildDetailId(childId);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), dietDetailsList));
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> deleteDietDetails(@PathVariable("id") long id) {
		if (!dietServices.dietExist(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID));
		}

		dietServices.delete(id);
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS));
	}
}
