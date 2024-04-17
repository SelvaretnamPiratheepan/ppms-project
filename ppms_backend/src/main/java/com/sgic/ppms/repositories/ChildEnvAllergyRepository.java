package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sgic.ppms.entities.ChildEnvironmentalAllergy;

public interface ChildEnvAllergyRepository extends JpaRepository<ChildEnvironmentalAllergy, Long> {
	@Query("SELECT h FROM ChildEnvironmentalAllergy h WHERE h.allergyDetail.Id = :allergyId")
	List<ChildEnvironmentalAllergy> findByAllergyDetailId(@Param("allergyId") Long allergyId);

	boolean existsByAllergyDetailIdAndEnvironmentalAllergyId(Long allergyDetailId, Long environmentalAllergyId);

}
