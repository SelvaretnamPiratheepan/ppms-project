package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.DrugManagementPlan;

public interface DrugManagementPlanRepository extends JpaRepository<DrugManagementPlan, Long> {
	DrugManagementPlan findByAdmitId(Long admitId);

	boolean existsByAdmitId(Long admitId);
}
