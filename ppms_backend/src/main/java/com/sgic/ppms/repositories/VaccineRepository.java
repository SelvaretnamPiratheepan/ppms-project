package com.sgic.ppms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.VaccineDetails;

@EnableJpaRepositories
@Repository
public interface VaccineRepository extends JpaRepository<VaccineDetails, Long> {
	Optional<VaccineDetails> getByVaccineName(String vaccineName);

	boolean existsByVaccineName(String vaccineName);

}
