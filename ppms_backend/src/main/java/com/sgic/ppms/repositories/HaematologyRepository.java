package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.Haematology;

public interface HaematologyRepository extends JpaRepository<Haematology, Long> {

	boolean existsByAdmitId(Long admitId);

	List<Haematology> findByAdmitId(Long admitId);
}