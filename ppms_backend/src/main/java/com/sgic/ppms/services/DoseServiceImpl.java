package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DoseDto;
import com.sgic.ppms.entities.Dose;
import com.sgic.ppms.repositories.DoseRepository;

@Service
public class DoseServiceImpl implements DoseService {

	@Autowired
	private DoseRepository doseRepository;

	public List<Dose> getAllDoses() {
		return doseRepository.findAll();
	}

	public Dose CreateDose(DoseDto doseDto) {
		Dose existingDose = doseRepository.findByName(doseDto.getName());
		if (existingDose != null) {
			return existingDose;
		} else {
			Dose dose = new Dose();
			BeanUtils.copyProperties(doseDto, dose);
			return doseRepository.save(dose);
		}
	}

	public boolean deleteDoseById(int id) {
		Optional<Dose> optionalDose = doseRepository.findById(id);
		if (optionalDose.isPresent()) {
			doseRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Optional<Dose> getDoseById(long id) {
		return doseRepository.findById(id);
	}

	public int getDoseIdByName(String dose_name) {
		Dose dose = doseRepository.findByName(dose_name);
		return (dose != null) ? dose.getId() : -1;
	}

}
