package com.sgic.ppms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.Frequency;

public interface FrequencyRepository extends JpaRepository<Frequency, Integer> {

	Frequency findByName(String name);

	Optional<Frequency> findById(long id);

}
