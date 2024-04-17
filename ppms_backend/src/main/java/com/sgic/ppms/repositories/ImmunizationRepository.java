package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Immunization;

@Repository
public interface ImmunizationRepository extends JpaRepository<Immunization, Long> {

	List<Immunization> findByChildId(String childId);
}
