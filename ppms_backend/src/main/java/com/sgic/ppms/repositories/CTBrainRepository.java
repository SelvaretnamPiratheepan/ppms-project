package com.sgic.ppms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgic.ppms.entities.CTBrain;

public interface CTBrainRepository extends JpaRepository<CTBrain, Long> {
	Optional<CTBrain> findByAdmitId(Long admitId);
}
