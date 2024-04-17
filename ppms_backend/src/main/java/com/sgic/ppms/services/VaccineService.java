package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.VaccineDetailsDto;
import com.sgic.ppms.entities.VaccineDetails;

public interface VaccineService {
	VaccineDetails addVaccine(VaccineDetailsDto vaccineDetailsDto);

	List<VaccineDetails> getAllVaccineDetails();

	Optional<VaccineDetails> getVaccineDetailById(long id);

	Optional<VaccineDetails> getVaccineDetailByName(String vaccineName);

	void deleteVaccineDetails(long id);

	VaccineDetails updateVaccineDetails(VaccineDetailsDto vaccineDetailsDto);

	boolean vaccineExistById(long id);

	boolean vaccineExistByName(String vaccineName);

}
