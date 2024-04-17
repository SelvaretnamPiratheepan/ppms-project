package com.sgic.ppms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgic.ppms.entities.DiagnosisDetails;

@Repository
public interface DiagnosisDetailsRepository extends JpaRepository<DiagnosisDetails, Long> {
	List<DiagnosisDetails> findByAdmitId(Long admitId);

	boolean existsByDiseaseDiseaseidAndAdmitId(Long diseaseId, Long admitId);

	DiagnosisDetails getDiagnosisDetailsById(Long id);
}
