package com.sgic.ppms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Anthropometry;

@Repository
public interface AnthropometryRepository extends JpaRepository<Anthropometry, Long> {
}
