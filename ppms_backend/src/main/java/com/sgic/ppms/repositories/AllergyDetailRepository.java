package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.AllergyDetail;

public interface AllergyDetailRepository extends JpaRepository<AllergyDetail, Long> {
	AllergyDetail findByChildDetailChildId(String childId);

	boolean existsByChildDetailChildId(String childId);
}