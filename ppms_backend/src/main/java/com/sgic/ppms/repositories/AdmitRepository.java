package com.sgic.ppms.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.Admit;

@Repository
public interface AdmitRepository extends JpaRepository<Admit, Long> {
	List<Admit> findByDateBetween(LocalDate fromDate, LocalDate endDate);

	List<Admit> findByTransferDateBetween(LocalDate fromDate, LocalDate endDate);
}
