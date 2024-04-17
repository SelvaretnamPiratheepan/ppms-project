package com.sgic.ppms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.CXR;

public interface CXRRepository extends JpaRepository<CXR, Integer> {

	Optional<CXR> findByadmitId(Long admitId);

	List<CXR> findByAdmitId(Long admitId);

	Boolean existsByAdmitId(Long admitId);
}
