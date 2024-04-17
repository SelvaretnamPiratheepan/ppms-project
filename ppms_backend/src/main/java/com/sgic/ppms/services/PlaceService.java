package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.PlaceDTO;
import com.sgic.ppms.entities.Place;

public interface PlaceService {
	Place createPlace(PlaceDTO placeDTO);

	List<Place> getAllPlaces();

	Place getPlaceById(Long id);

	Place getPlaceIDByName(String name);

	void deletePlaceById(Long id);
}
