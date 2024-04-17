package com.sgic.ppms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
	Route findByName(String name);

	Optional<Route> findById(long id);

}