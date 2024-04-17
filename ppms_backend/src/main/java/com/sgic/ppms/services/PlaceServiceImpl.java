package com.sgic.ppms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.PlaceDTO;
import com.sgic.ppms.entities.Place;
import com.sgic.ppms.repositories.PlaceRepository;

import jakarta.transaction.Transactional;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	private PlaceRepository placeRepository;

	public Place createPlace(PlaceDTO placeDTO) {
		Place place = new Place();
		place.setName(placeDTO.getName());
		return placeRepository.save(place);
	}

	public List<Place> getAllPlaces() {
		return placeRepository.findAll();
	}

	public Place getPlaceById(Long id) {
		return placeRepository.findById(id).orElse(null);
	}

	public Place getPlaceIDByName(String name) {
		return placeRepository.findByName(name);
	}

	@Transactional
	public void deletePlaceById(Long id) {
		placeRepository.deleteById(id);
	}
}
