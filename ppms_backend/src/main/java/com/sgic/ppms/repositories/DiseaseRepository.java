package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

}
