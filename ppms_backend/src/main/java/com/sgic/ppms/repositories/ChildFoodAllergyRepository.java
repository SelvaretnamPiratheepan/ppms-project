package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sgic.ppms.entities.ChildFoodAllergy;

public interface ChildFoodAllergyRepository extends JpaRepository<ChildFoodAllergy, Long> {
	@Query("SELECT h FROM ChildFoodAllergy h WHERE h.allergyDetail.Id = :allergyId")
	List<ChildFoodAllergy> findByAllergyDetailId(@Param("allergyId") Long allergyId);

	boolean existsByAllergyDetailIdAndFoodAllergyId(Long allergyDetailId, int foodAllergyId);
}
