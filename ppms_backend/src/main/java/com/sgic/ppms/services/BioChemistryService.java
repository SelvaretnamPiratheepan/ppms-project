package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.BioChemistryDto;
import com.sgic.ppms.entities.BioChemistry;

public interface BioChemistryService {
	BioChemistry createBioChemistry(BioChemistryDto bioChemistryDto);

	List<BioChemistry> getAllBioChemistries();

	BioChemistry getBioChemistryById(Long id);

	BioChemistry updateBioChemistry(Long id, BioChemistryDto bioChemistryDto);

	boolean deleteBioChemistry(Long id);
}
