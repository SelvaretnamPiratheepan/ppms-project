package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.DrugAllergy;

public interface DrugAllergyRepository extends JpaRepository<DrugAllergy, Long> {
	boolean existsByDrugIdAndAllergyDetailIdAndIdNot(long drugId, long allergyDetailId, Long id);

	boolean existsByDrugIdAndAllergyDetailId(long drugId, long allergyDetailId);
}
