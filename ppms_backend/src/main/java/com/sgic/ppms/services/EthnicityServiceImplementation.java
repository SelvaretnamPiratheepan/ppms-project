package com.sgic.ppms.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.EthnicityDto;
import com.sgic.ppms.entities.Ethnicity;
import com.sgic.ppms.repositories.EthnicityRepository;

@Service
@Transactional
public class EthnicityServiceImplementation implements EthnicityServices {

	@Autowired
	private EthnicityRepository ethnicityRepository;

	public Ethnicity createEthnicity(EthnicityDto ethnicityDto) {
		Ethnicity ethnicity = new Ethnicity();
		BeanUtils.copyProperties(ethnicityDto, ethnicity);
		return ethnicityRepository.save(ethnicity);
	}

	public List<Ethnicity> getAllEthnicities() {
		return ethnicityRepository.findAll();
	}

	public Ethnicity getEthnicityById(long id) {
		return ethnicityRepository.findById(id).orElse(null);
	}

	public Ethnicity updateEthnicity(long id, EthnicityDto updatedEthnicityDto) {
		Ethnicity ethnicity = ethnicityRepository.findById(id).get();
		if (ethnicity != null) {
			BeanUtils.copyProperties(updatedEthnicityDto, ethnicity);
			ethnicity.setId(id);
			return ethnicityRepository.save(ethnicity);
		}
		return null;
	}

	public boolean deleteEthnicity(long id) {
		if (ethnicityRepository.existsById(id)) {
			ethnicityRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
