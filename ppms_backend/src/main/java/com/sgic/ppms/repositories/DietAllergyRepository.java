package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.DietAllergy;

@Repository
public interface DietAllergyRepository extends JpaRepository<DietAllergy, Long> {
	DietAllergy findByChildDetailChildId(String childId);

	boolean existsByChildDetailChildId(String childId);

	boolean existsByAllergyDetailId(Long allergyId);
}