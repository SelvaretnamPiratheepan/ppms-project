package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Ethnicity;

@Repository
public interface EthnicityRepository extends JpaRepository<Ethnicity, Long> {
}
