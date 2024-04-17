package com.sgic.ppms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DiagnosisDetailsDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.DiagnosisDetails;
import com.sgic.ppms.entities.Disease;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.DiagnosisDetailsRepository;
import com.sgic.ppms.repositories.DiseaseRepository;

import jakarta.transaction.Transactional;

@Service
public class DiagnosisDetailsServiceImpl implements DiagnosisDetailsService {
	@Autowired
	private DiagnosisDetailsRepository diagnosisDetailsRepository;
	@Autowired
	private AdmitRepository admitRepository;
	@Autowired
	private DiseaseRepository diseaseRepository;

	public boolean existsById(Long id) {
		return diagnosisDetailsRepository.existsById(id);
	}

	public boolean admitIdExists(Long admitId) {
		return admitRepository.existsById(admitId);
	}

	public boolean diseaseIdExists(Long diseaseId) {
		return diseaseRepository.existsById(diseaseId);
	}

	public boolean IdExists(Long id) {
		return diagnosisDetailsRepository.existsById(id);
	}

	public boolean admitIdExistsInDiagnosisDetails(Long admitId) {
		return !diagnosisDetailsRepository.findByAdmitId(admitId).isEmpty();
	}

	public boolean relationshipExists(Long diseaseId, Long admitId) {
		return diagnosisDetailsRepository.existsByDiseaseDiseaseidAndAdmitId(diseaseId, admitId);
	}

	public List<DiagnosisDetails> getAllDiagnosisDetails() {
		return diagnosisDetailsRepository.findAll();
	}

	public List<DiagnosisDetailsDto> getDiagnosisDetailsByAdmitId(Long admitId) {
		List<DiagnosisDetails> diagnosisDetails = diagnosisDetailsRepository.findByAdmitId(admitId);
		if (diagnosisDetails.isEmpty()) {
			throw new IllegalArgumentException("Diagnosis details not found with Admit ID:" + admitId);
		}

		List<DiagnosisDetailsDto> diagnosisDetailDto = new ArrayList<>();
		for (DiagnosisDetails diagnosisDetail : diagnosisDetails) {
			DiagnosisDetailsDto diagnosisDetailsDto = new DiagnosisDetailsDto();
			diagnosisDetailsDto.setId(diagnosisDetail.getId());
			diagnosisDetailsDto.setAdmitId(admitId);
			diagnosisDetailsDto.setDiseaseId(diagnosisDetail.getDisease().getDiseaseid());

			diagnosisDetailDto.add(diagnosisDetailsDto);
		}

		return diagnosisDetailDto;
	}

	public DiagnosisDetails getDiagnosisDetailsById(Long id) {
		return null;
	}

	@Transactional
	public DiagnosisDetails createDiagnosisDetails(DiagnosisDetailsDto diagnosisDetailsDto) {
		Admit admit = admitRepository.findById(diagnosisDetailsDto.getAdmitId())
				.orElseThrow(() -> new NoSuchElementException("Admit not found"));
		Disease disease = diseaseRepository.findById(diagnosisDetailsDto.getDiseaseId())
				.orElseThrow(() -> new NoSuchElementException("Disease not found"));
		DiagnosisDetails diagnosisDetails = new DiagnosisDetails();
		diagnosisDetails.setAdmit(admit);
		diagnosisDetails.setDisease(disease);
		return diagnosisDetailsRepository.save(diagnosisDetails);
	}

	@Transactional
	public DiagnosisDetails updateDiagnosisDetails(Long id, DiagnosisDetailsDto diagnosisDetailsDto) {
		DiagnosisDetails diagnosisDetails = diagnosisDetailsRepository.getDiagnosisDetailsById(id);
		Admit admit = admitRepository.findById(diagnosisDetailsDto.getAdmitId())
				.orElseThrow(() -> new NoSuchElementException("Admit not found"));
		Disease disease = diseaseRepository.findById(diagnosisDetailsDto.getDiseaseId())
				.orElseThrow(() -> new NoSuchElementException("Disease not found"));
		diagnosisDetails.setAdmit(admit);
		diagnosisDetails.setDisease(disease);
		return diagnosisDetailsRepository.save(diagnosisDetails);
	}

	public void deleteDiagnosisDetails(Long id) {
		diagnosisDetailsRepository.deleteById(id);
	}
}
