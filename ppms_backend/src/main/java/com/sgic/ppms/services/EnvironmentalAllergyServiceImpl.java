package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.EnvironmentalAllergyDTO;
import com.sgic.ppms.entities.EnvironmentalAllergy;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.EnvironmentalAllergyRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@Service
public class EnvironmentalAllergyServiceImpl implements EnvironmentalAllergyService {

	private final EnvironmentalAllergyRepository environmentalAllergyRepository;

	public EnvironmentalAllergyServiceImpl(EnvironmentalAllergyRepository environmentalAllergyRepository) {
		this.environmentalAllergyRepository = environmentalAllergyRepository;
	}

	public boolean existsByName(String name) {
		return environmentalAllergyRepository.existsByAllergyName(name);
	}

	@Transactional
	public ResponseWrapper<EnvironmentalAllergyDTO> insertEnvironmentalAllergyDetail(
			EnvironmentalAllergyDTO environmentalAllergyDTO) {
		String allergyName = environmentalAllergyDTO.getAllergyName();
		if (!environmentalAllergyRepository.existsByAllergyName(allergyName)) {
			EnvironmentalAllergy allergy = convertToEntity(environmentalAllergyDTO);
			allergy = environmentalAllergyRepository.save(allergy);
			EnvironmentalAllergyDTO createdAllergyDTO = convertToDTO(allergy);
			return new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS,
					createdAllergyDTO);
		} else {
			EnvironmentalAllergy existingAllergy = environmentalAllergyRepository.findByAllergyName(allergyName);
			EnvironmentalAllergyDTO existingAllergyDTO = convertToDTO(existingAllergy);
			return new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					ValidationMessages.NAME_EXISTS, existingAllergyDTO);
		}
	}

	@Transactional
	public ResponseWrapper<EnvironmentalAllergyDTO> updateEnvironmentalAllergyDetail(Long id,
			EnvironmentalAllergyDTO environmentalAllergyDTO) {
		Optional<EnvironmentalAllergy> existingAllergyOptional = environmentalAllergyRepository.findById(id);
		if (existingAllergyOptional.isPresent()) {
			EnvironmentalAllergy existingAllergy = existingAllergyOptional.get();
			existingAllergy.setAllergyName(environmentalAllergyDTO.getAllergyName());
			existingAllergy = environmentalAllergyRepository.save(existingAllergy); // Save the updated entity
			EnvironmentalAllergyDTO updatedAllergyDTO = convertToDTO(existingAllergy); // Convert to DTO
			return new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS,
					updatedAllergyDTO);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		}
	}

	public ResponseWrapper<EnvironmentalAllergyDTO> deleteEnvironmentalAllergyDetail(Long id) {
		Optional<EnvironmentalAllergy> allergyOptional = environmentalAllergyRepository.findById(id);
		if (allergyOptional.isPresent()) {
			EnvironmentalAllergy deletedAllergy = allergyOptional.get();
			environmentalAllergyRepository.deleteById(id);
			EnvironmentalAllergyDTO deletedAllergyDTO = convertToDTO(deletedAllergy);
			return new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(), ValidationMessages.DELETE_SUCCESS,
					deletedAllergyDTO);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		}
	}

	public ResponseWrapper<List<EnvironmentalAllergyDTO>> getAllEnvironmentalAllergies() {
		List<EnvironmentalAllergy> allergies = environmentalAllergyRepository.findAll();
		List<EnvironmentalAllergyDTO> allergyDTOs = allergies.stream().map(this::convertToDTO)
				.collect(Collectors.toList());
		return new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), ValidationMessages.RETRIEVED,
				allergyDTOs);
	}

	public ResponseWrapper<String> getEnvironmentalAllergyName(Long id) {
		Optional<EnvironmentalAllergy> allergyOptional = environmentalAllergyRepository.findById(id);
		return allergyOptional
				.map(allergy -> new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						ValidationMessages.RETRIEVED, allergy.getAllergyName()))
				.orElseGet(() -> new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						RestApiResponseStatus.NOT_FOUND.getStatus(), null));
	}

	public ResponseWrapper<EnvironmentalAllergyDTO> getEnvironmentalAllergy(Long id) {
		Optional<EnvironmentalAllergy> allergyOptional = environmentalAllergyRepository.findById(id);
		return allergyOptional
				.map(allergy -> new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
						ValidationMessages.RETRIEVED, convertToDTO(allergy)))
				.orElseGet(() -> new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						RestApiResponseStatus.NOT_FOUND.getStatus(), null));
	}

	public ResponseWrapper<EnvironmentalAllergyDTO> getEnvironmentalAllergyByName(String name) {
		EnvironmentalAllergy allergy = environmentalAllergyRepository.findByAllergyName(name);
		return (allergy != null)
				? new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), ValidationMessages.RETRIEVED,
						convertToDTO(allergy))
				: new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						RestApiResponseStatus.NOT_FOUND.getStatus(), null);
	}

	public ResponseWrapper<Long> getEnvironmentalAllergyIdByName(String name) {
		EnvironmentalAllergy allergy = environmentalAllergyRepository.findByAllergyName(name);
		return (allergy != null)
				? new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), ValidationMessages.RETRIEVED,
						allergy.getId())
				: new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						RestApiResponseStatus.NOT_FOUND.getStatus(), null);
	}

	private EnvironmentalAllergyDTO convertToDTO(EnvironmentalAllergy environmentalAllergy) {
		EnvironmentalAllergyDTO allergyDTO = new EnvironmentalAllergyDTO();
		allergyDTO.setId(environmentalAllergy.getId());
		allergyDTO.setAllergyName(environmentalAllergy.getAllergyName());
		return allergyDTO;
	}

	private EnvironmentalAllergy convertToEntity(EnvironmentalAllergyDTO environmentalAllergyDTO) {
		EnvironmentalAllergy allergy = new EnvironmentalAllergy();
		allergy.setAllergyName(environmentalAllergyDTO.getAllergyName());
		return allergy;
	}
}
