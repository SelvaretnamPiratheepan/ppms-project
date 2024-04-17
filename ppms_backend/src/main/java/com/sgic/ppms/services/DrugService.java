package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.DrugDto;
import com.sgic.ppms.entities.Drug;

public interface DrugService {
	Drug CreateDrug(DrugDto drugDto);

	List<Drug> getAllDrugs();

	boolean deleteDrugById(int id);

	Optional<Drug> getDrugById(long id);;

	int getDrugIdByName(String drug_name);

}