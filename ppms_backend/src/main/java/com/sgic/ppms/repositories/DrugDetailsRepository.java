package com.sgic.ppms.repositories;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.DrugDetails;

@Repository
public interface DrugDetailsRepository extends JpaRepository<DrugDetails, Integer> {
	Optional<DrugDetails> findById(int admit_id);

	List<DrugDetails> findByAdmitId(int admit_id);

	List<DrugDetails> findByAdmitIdAndStartDate(Long id, Date startDate);

}
