package com.sgic.ppms.services;

import java.util.Optional;

import com.sgic.ppms.dto.DrugDetailsDto;
import com.sgic.ppms.entities.DrugDetails;

public interface DrugDetailsService {

	DrugDetails CreateDrugDetails(DrugDetailsDto drugDetailsDto);

	Optional<DrugDetails> getDrugDetailsById(int admit_id);

	DrugDetails updateDrugDetails(int admit_id, DrugDetailsDto drugDetailsDto);
}
