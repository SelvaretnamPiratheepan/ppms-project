package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.USSAdbo;

public interface USSAdboRepository extends JpaRepository<USSAdbo, Long> {
	List<USSAdbo> findByAdmitId(long admitId);

}
