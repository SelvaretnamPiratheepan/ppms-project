package com.sgic.ppms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.ImmunizationDTO;
import com.sgic.ppms.dto.VaccineDetailAndDueDateDTO;
import com.sgic.ppms.entities.Immunization;
import com.sgic.ppms.entities.VaccineDetails;
import com.sgic.ppms.enums.AgeType;
import com.sgic.ppms.repositories.ImmunizationRepository;
import com.sgic.ppms.repositories.VaccineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImmunizationServiceImpl implements ImmunizationService {

	@Autowired
	private ImmunizationRepository immunizationRepository;

	@Autowired
	private VaccineRepository vaccineRepo;

	private LocalDate calculateDueDate(LocalDate birthDate, int age, AgeType ageType) {
		if (ageType == AgeType.MONTHS) {
			return birthDate.plusMonths(age);
		} else if (ageType == AgeType.YEARS) {
			return birthDate.plusYears(age);
		} else {
			return null;
		}
	}

	private Immunization createImmunization(ImmunizationDTO immunizationDTO) {
		Immunization immunization = new Immunization();
		BeanUtils.copyProperties(immunizationDTO, immunization);
		return immunizationRepository.save(immunization);
	}

	@Override
	public List<VaccineDetailAndDueDateDTO> GetBasicsImmunizationDetails(LocalDate birthDate) {
		List<VaccineDetailAndDueDateDTO> dueDates = new ArrayList<>();
		List<VaccineDetails> vaccineDetailsList = vaccineRepo.findAll();
		for (VaccineDetails vaccineDetail : vaccineDetailsList) {
			int age = vaccineDetail.getAge();
			AgeType ageType = vaccineDetail.getAgeType();
			LocalDate dueDate = calculateDueDate(birthDate, age, ageType);
			VaccineDetailAndDueDateDTO dueDateDTO = new VaccineDetailAndDueDateDTO(vaccineDetail, dueDate);
			dueDates.add(dueDateDTO);
		}
		return dueDates;
	}

	@Override
	public List<Immunization> createImmunizations(List<ImmunizationDTO> immunizationDTOs) {
		List<Immunization> createdImmunizations = new ArrayList<>();
		for (ImmunizationDTO immunizationDTO : immunizationDTOs) {
			Immunization immunization = createImmunization(immunizationDTO);
			createdImmunizations.add(immunization);
		}
		return createdImmunizations;
	}

	@Override
	public Immunization getImmunizationById(Long id) {
		return immunizationRepository.findById(id).orElse(null);
	}

	@Override
	public List<Immunization> getAllImmunizationsByChildId(String childId) {
		List<Immunization> immunization = immunizationRepository.findByChildId(childId);
		if (immunization.isEmpty()) {
			return null;
		}
		return immunization;
	}

	@Override
	public Immunization updateImmunization(Long id, ImmunizationDTO immunizationDTO) {
		Immunization existingImmunization = immunizationRepository.findById(id).orElse(null);
		if (existingImmunization != null) {
			BeanUtils.copyProperties(immunizationDTO, existingImmunization);
			existingImmunization.setId(id);
			return immunizationRepository.save(existingImmunization);
		}
		return null;
	}

	@Override
	public boolean deleteImmunization(Long id) {
		Optional<Immunization> optionalEthnicity = immunizationRepository.findById(id);
		if (optionalEthnicity.isPresent()) {
			immunizationRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
