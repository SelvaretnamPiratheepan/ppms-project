package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.PlaceDTO;
import com.sgic.ppms.entities.Place;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.PlaceService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.PLACE)
public class PlaceController {

	@Autowired
	private PlaceService placeService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseWrapper<PlaceDTO> createPlace(@RequestBody PlaceDTO placeDTO) {
		Place existingPlace = placeService.getPlaceIDByName(placeDTO.getName());
		if (existingPlace != null) {
			PlaceDTO existingPlaceDTO = new PlaceDTO(existingPlace.getId(), existingPlace.getName());
			return new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					ValidationMessages.NAME_EXISTS, existingPlaceDTO);
		}

		Place place = placeService.createPlace(placeDTO);
		if (place != null) {
			PlaceDTO createdPlaceDTO = new PlaceDTO(place.getId(), place.getName());
			return new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS,
					createdPlaceDTO);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.CREATE_FAILED,
					null);
		}
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseWrapper<List<Place>> getAllPlaces() {
		List<Place> places = placeService.getAllPlaces();
		return new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), "Success", places);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseWrapper<Place> getPlaceById(@PathVariable("id") Long id) {
		Place place = placeService.getPlaceById(id);
		if (place == null) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NOT_FOUND, null);
		}
		return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), "Success", place);
	}

	@GetMapping(EndpointBundle.GET_BY_NAME)
	public ResponseWrapper<Place> getPlaceIDByName(@PathVariable("name") String name) {
		Place place = placeService.getPlaceIDByName(name);
		if (place == null) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NOT_FOUND, null);
		}
		return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), "Success", place);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseWrapper<String> deletePlaceById(@PathVariable("id") Long id) {
		Place place = placeService.getPlaceById(id);
		if (place == null) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NOT_FOUND, null);
		}
		placeService.deletePlaceById(id);
		return new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(), ValidationMessages.DELETE_SUCCESS, null);
	}
}
