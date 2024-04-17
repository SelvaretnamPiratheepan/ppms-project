package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.DoseDto;
import com.sgic.ppms.entities.Dose;

public interface DoseService {

	Dose CreateDose(DoseDto doseDto);

	List<Dose> getAllDoses();

	boolean deleteDoseById(int id);

	Optional<Dose> getDoseById(long id);

	int getDoseIdByName(String dose_name);

}
