package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.Discharge;

public interface DischargeRepository extends JpaRepository<Discharge, Long> {

	Discharge findByAdmitId(Long admitId);

	boolean existsByAdmitId(Long admitId);

}