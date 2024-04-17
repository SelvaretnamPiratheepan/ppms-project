package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.VaccineDetailsDto;
import com.sgic.ppms.entities.VaccineDetails;
import com.sgic.ppms.repositories.VaccineRepository;

import jakarta.transaction.Transactional;

@Service
public class VaccineServiceImplementation implements VaccineService {

	@Autowired
	private VaccineRepository vaccineRepository;

	@Transactional
	public VaccineDetails addVaccine(VaccineDetailsDto vaccineDetailsDto) {
		VaccineDetails vaccineDetails = new VaccineDetails();
		vaccineDetailsDto.setId(0L);
		BeanUtils.copyProperties(vaccineDetailsDto, vaccineDetails);
		return vaccineRepository.save(vaccineDetails);
	}

	@Transactional
	public VaccineDetails updateVaccineDetails(VaccineDetailsDto vaccineDetailsDto) {
		VaccineDetails vaccineDetails = new VaccineDetails();
		BeanUtils.copyProperties(vaccineDetailsDto, vaccineDetails);
		return vaccineRepository.save(vaccineDetails);

	}

	public List<VaccineDetails> getAllVaccineDetails() {
		return vaccineRepository.findAll();
	}

	public Optional<VaccineDetails> getVaccineDetailById(long id) {
		return vaccineRepository.findById(id);

	}

	public Optional<VaccineDetails> getVaccineDetailByName(String vaccineName) {
		return vaccineRepository.getByVaccineName(vaccineName);
	}

	public void deleteVaccineDetails(long id) {
		vaccineRepository.deleteById(id);
	}

	public boolean vaccineExistById(long id) {
		return vaccineRepository.existsById(id);
	}

	public boolean vaccineExistByName(String vaccineName) {
		return vaccineRepository.existsByVaccineName(vaccineName);

	}

}
