package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DrugDto;
import com.sgic.ppms.entities.Drug;
import com.sgic.ppms.repositories.DrugRepository;

@Service
public class DrugServiceImpl implements DrugService {

	@Autowired
	private DrugRepository drugRepository;

	public List<Drug> getAllDrugs() {
		return drugRepository.findAll();
	}

	public Drug CreateDrug(DrugDto drugDto) {
		Drug existingDrug = drugRepository.findByName(drugDto.getName());
		if (existingDrug != null) {
			return existingDrug;
		} else {
			Drug drug = new Drug();
			BeanUtils.copyProperties(drugDto, drug);
			return drugRepository.save(drug);
		}
	}

	public boolean deleteDrugById(int id) {
		Optional<Drug> optionalDrug = drugRepository.findById(id);
		if (optionalDrug.isPresent()) {
			drugRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Optional<Drug> getDrugById(long id) {
		return drugRepository.findById(id);
	}

	public int getDrugIdByName(String drug_name) {
		Drug drug = drugRepository.findByName(drug_name);
		return (drug != null) ? drug.getId() : -1;
	}

}
