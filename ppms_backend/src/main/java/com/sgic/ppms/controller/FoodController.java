package com.sgic.ppms.controller;

import java.util.List;

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

import com.sgic.ppms.dto.FoodDto;
import com.sgic.ppms.entities.Food;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.FoodService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping(EndpointBundle.FOOD)
public class FoodController {

	@Autowired
	private FoodService foodService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<?>> getAllFoods() {
		List<Food> foodsDetails = foodService.getAllFoods();
		ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				RestApiResponseStatus.RETRIEVED.getStatus(), foodsDetails);
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> getById(@PathVariable("id") long id) {
		Food food = foodService.getById(id);
		if (food != null) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
					RestApiResponseStatus.RETRIEVED.getStatus(), food);
			return ResponseEntity.ok().body(responseWrapper);
		} else {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.GIVEN_ID_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}

	@Transactional
	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> createFood(@RequestBody FoodDto foodDto) {
		if (foodService.existsByname(foodDto.getName())) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.NAME_EXISTS);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
		}
		if (foodDto.getId() == null && (foodDto.getName() == null || foodDto.getName().isEmpty())) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					RestApiResponseStatus.VALIDATION_FAILURE.getStatus(), ValidationMessages.NOT_NULL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
		}

		Food food = foodService.createFood(foodDto);

		if (food != null) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					RestApiResponseStatus.CREATED.getStatus(), food);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		} else {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					RestApiResponseStatus.NOT_CREATED.getStatus(), ValidationMessages.CREATE_FAILED);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> deleteById(@PathVariable("id") Long id) {
		boolean deleted = foodService.deleteById(id);
		if (deleted) {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS);
			return ResponseEntity.ok().body(responseWrapper);
		} else {
			ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.DELETE_FAILED);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}
}
