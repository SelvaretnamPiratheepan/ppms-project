package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.DiagnosisDetailsDto;
import com.sgic.ppms.entities.DiagnosisDetails;

public interface DiagnosisDetailsService {
	List<DiagnosisDetails> getAllDiagnosisDetails();

	List<DiagnosisDetailsDto> getDiagnosisDetailsByAdmitId(Long admitId);

	DiagnosisDetails getDiagnosisDetailsById(Long id);

	DiagnosisDetails createDiagnosisDetails(DiagnosisDetailsDto diagnosisDetailsDto);

	DiagnosisDetails updateDiagnosisDetails(Long id, DiagnosisDetailsDto diagnosisDetailsDto);

	void deleteDiagnosisDetails(Long id);

	boolean existsById(Long id);

	boolean admitIdExists(Long admitId);

	boolean diseaseIdExists(Long diseaseId);

	boolean IdExists(Long id);

	boolean admitIdExistsInDiagnosisDetails(Long admitId);

	boolean relationshipExists(Long diseaseId, Long admitId);
}