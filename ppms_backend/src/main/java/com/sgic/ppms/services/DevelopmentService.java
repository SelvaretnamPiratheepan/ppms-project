package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.DevelopmentDetailsDto;
import com.sgic.ppms.entities.DevelopmentDetails;

public interface DevelopmentService {
	DevelopmentDetails addDevelopmentDetails(DevelopmentDetailsDto developmentDetailsDto);

	List<DevelopmentDetails> getAllDevelopmentDetails();

	Optional<DevelopmentDetails> getDevelopmentDetailsById(long id);

	DevelopmentDetails updateDevelopmentDetails(DevelopmentDetailsDto updatedDevelopmentDetailsDto);

	void deleteDevelopmentDetails(long id);

	boolean developmentExist(long id);
}
