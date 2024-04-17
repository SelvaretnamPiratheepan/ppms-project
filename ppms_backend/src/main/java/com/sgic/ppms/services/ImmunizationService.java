package com.sgic.ppms.services;

import java.time.LocalDate;
import java.util.List;

import com.sgic.ppms.dto.ImmunizationDTO;
import com.sgic.ppms.dto.VaccineDetailAndDueDateDTO;
import com.sgic.ppms.entities.Immunization;

public interface ImmunizationService {
	Immunization getImmunizationById(Long id);

	Immunization updateImmunization(Long id, ImmunizationDTO immunizationDTO);

	boolean deleteImmunization(Long id);

	List<Immunization> getAllImmunizationsByChildId(String childId);

	List<Immunization> createImmunizations(List<ImmunizationDTO> immunizationDTOs);

	List<VaccineDetailAndDueDateDTO> GetBasicsImmunizationDetails(LocalDate birthDate);
}
