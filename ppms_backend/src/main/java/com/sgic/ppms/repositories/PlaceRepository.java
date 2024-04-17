package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
	Place findByName(String name);
}
