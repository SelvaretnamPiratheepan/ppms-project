package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.DiseaseDTO;
import com.sgic.ppms.entities.Disease;
import com.sgic.ppms.enums.RestApiResponseStatus;

public interface DiseaseService {
	List<Disease> getAllDiseases();

	RestApiResponseStatus deleteDisease(Long diseaseid);

	Optional<Disease> getDiseaseById(Long id);

	Disease createDisease(DiseaseDTO diseaseDTO);

	boolean Idexists(long id);

	Disease updateDisease(Long diseaseId, DiseaseDTO diseaseDTO);

	boolean existsById(Long diseaseId);

}
