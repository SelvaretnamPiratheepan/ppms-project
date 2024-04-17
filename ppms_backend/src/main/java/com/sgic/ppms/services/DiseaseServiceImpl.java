package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DiseaseDTO;
import com.sgic.ppms.entities.Disease;
import com.sgic.ppms.entities.ICDCategory;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.DiseaseRepository;
import com.sgic.ppms.repositories.ICDCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class DiseaseServiceImpl implements DiseaseService {
	@Autowired
	DiseaseRepository diseaseRepository;
	@Autowired
	ICDCategoryRepository icdCategoryRepository;

	public List<Disease> getAllDiseases() {
		return diseaseRepository.findAll();
	}

	public RestApiResponseStatus deleteDisease(Long diseaseId) {
		Optional<Disease> optionalDisease = diseaseRepository.findById(diseaseId);

		if (optionalDisease.isPresent()) {
			diseaseRepository.deleteById(diseaseId);
			return RestApiResponseStatus.DELETED;
		} else {
			return RestApiResponseStatus.NOT_FOUND;
		}
	}

	public Optional<Disease> getDiseaseById(Long diseaseId) {
		return diseaseRepository.findById(diseaseId);
	}

	@Transactional
	public Disease updateDisease(Long diseaseid, DiseaseDTO diseaseDTO) {
		Disease updateddisease = diseaseRepository.findById(diseaseid)
				.orElseThrow(() -> new IllegalArgumentException("Disease not found with id:" + diseaseid));
		updateddisease.setIcdname(diseaseDTO.getIcdname());
		updateddisease.setIcdcode(diseaseDTO.getIcdcode());
		updateddisease.setIcd10(diseaseDTO.getIcd10());
		return diseaseRepository.save(updateddisease);
	}

	public boolean existsById(Long diseaseId) {
		return diseaseRepository.existsById(diseaseId);

	}

	@Transactional
	public Disease createDisease(DiseaseDTO diseaseDTO) {
		Disease disease = new Disease();
		disease.setDiseaseid(diseaseDTO.getDiseaseid());
		disease.setIcdname(diseaseDTO.getIcdname());
		disease.setIcdcode(diseaseDTO.getIcdcode());
		disease.setIcd10(diseaseDTO.getIcd10());

		ICDCategory icdCategory = icdCategoryRepository.findById(diseaseDTO.getId()).orElseThrow(
				() -> new IllegalArgumentException("ICD category not found with id: " + diseaseDTO.getId()));

		disease.setIcdCategory(icdCategory);
		Disease savedDisease = diseaseRepository.save(disease);

		diseaseDTO.setDiseaseid((savedDisease.getDiseaseid()));
		return disease;
	}

	public boolean Idexists(long id) {
		return icdCategoryRepository.existsById(id);
	}
}