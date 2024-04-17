package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.EnvironmentalAllergy;

@Repository
public interface EnvironmentalAllergyRepository extends JpaRepository<EnvironmentalAllergy, Long> {
	EnvironmentalAllergy findByAllergyName(String allergyName);

	boolean existsByAllergyName(String allergyName);

}
