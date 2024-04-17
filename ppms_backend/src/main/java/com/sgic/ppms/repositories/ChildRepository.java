package com.sgic.ppms.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.ChildDetail;

@EnableJpaRepositories
@Repository
public interface ChildRepository extends JpaRepository<ChildDetail, String> {

	Optional<ChildDetail> findByChildId(String childId);

	@Query("SELECT c FROM ChildDetail c WHERE c.firstName LIKE %:anything% OR c.lastName LIKE %:anything% OR c.childId LIKE %:anything%")
	List<ChildDetail> getPatientsByAnything(String anything);

	long countByDateOfBirth(LocalDate dateOfBirth);

	boolean existsByChildId(String childId);

}
