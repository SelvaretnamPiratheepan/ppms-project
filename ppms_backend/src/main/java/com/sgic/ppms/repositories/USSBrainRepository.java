package com.sgic.ppms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.USSBrain;

@Repository
public interface USSBrainRepository extends JpaRepository<USSBrain, Long> {

	Optional<USSBrain> findByAdmitId(Long admitId);
}
