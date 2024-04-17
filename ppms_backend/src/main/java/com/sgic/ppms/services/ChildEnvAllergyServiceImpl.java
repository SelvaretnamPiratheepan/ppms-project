package com.sgic.ppms.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.ChildEnvAllergyDto;
import com.sgic.ppms.entities.AllergyDetail;
import com.sgic.ppms.entities.ChildEnvironmentalAllergy;
import com.sgic.ppms.entities.EnvironmentalAllergy;
import com.sgic.ppms.repositories.AllergyDetailRepository;
import com.sgic.ppms.repositories.ChildEnvAllergyRepository;
import com.sgic.ppms.repositories.EnvironmentalAllergyRepository;

@Service
public class ChildEnvAllergyServiceImpl implements ChildEnvAllergyService {
	private final Logger logger = LoggerFactory.getLogger(ChildEnvAllergyServiceImpl.class);

	@Autowired
	private ChildEnvAllergyRepository childEnvAllergyRepository;

	@Autowired
	private AllergyDetailRepository allergyDetailRepository;

	@Autowired
	private EnvironmentalAllergyRepository environmentalAllergyRepository;

	@Transactional
	public ChildEnvAllergyDto createChildEnvironmentalAllergy(ChildEnvAllergyDto childEnvAllergyDto) {
		logger.info("Creating new Child Environmental Allergy: {}", childEnvAllergyDto);
		ChildEnvironmentalAllergy childEnvironmentalAllergy = new ChildEnvironmentalAllergy();
		EnvironmentalAllergy envAllergy = environmentalAllergyRepository
				.findById(childEnvAllergyDto.getEnvAllergy().getId()).orElseThrow(() -> new IllegalArgumentException(
						"Environmental Allergy not found with id: " + childEnvAllergyDto.getEnvAllergy().getId()));
		childEnvironmentalAllergy.setEnvironmentalAllergy(envAllergy);
		AllergyDetail allergyDetail = allergyDetailRepository.findById(childEnvAllergyDto.getAllergyDetailId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Allergy Detail is not found with id: " + childEnvAllergyDto.getAllergyDetailId()));
		childEnvironmentalAllergy.setAllergyDetail(allergyDetail);
		ChildEnvironmentalAllergy savedChildEnvironmentalAllergy = childEnvAllergyRepository
				.save(childEnvironmentalAllergy);
		childEnvAllergyDto.setId(savedChildEnvironmentalAllergy.getId());
		childEnvAllergyDto.setEnvAllergy(envAllergy);
		return childEnvAllergyDto;
	}

	@Transactional
	public ChildEnvAllergyDto updateChildEnvironmentalAllergy(Long id, ChildEnvAllergyDto childEnvAllergyDto) {
		ChildEnvironmentalAllergy existingChildEnvAllergy = childEnvAllergyRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("Child Environmental Allergy is not found with id: " + id));
		EnvironmentalAllergy EnvAllergy = environmentalAllergyRepository
				.findById(childEnvAllergyDto.getEnvAllergy().getId()).orElseThrow(() -> new IllegalArgumentException(
						"Environmental Allergy not found with id: " + childEnvAllergyDto.getEnvAllergy()));
		existingChildEnvAllergy.setEnvironmentalAllergy(EnvAllergy);
		AllergyDetail allergyDetail = allergyDetailRepository.findById(childEnvAllergyDto.getAllergyDetailId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Allergy Detail is not found with id: " + childEnvAllergyDto.getAllergyDetailId()));
		existingChildEnvAllergy.setAllergyDetail(allergyDetail);
		ChildEnvironmentalAllergy updatedChildEnvironmentalAllergy = childEnvAllergyRepository
				.save(existingChildEnvAllergy);
		childEnvAllergyDto.setId(updatedChildEnvironmentalAllergy.getId());
		childEnvAllergyDto.setEnvAllergy(EnvAllergy);
		return childEnvAllergyDto;
	}

	@Transactional(readOnly = true)
	public List<ChildEnvAllergyDto> getChildEnvironmentalAllergyByAllergyid(Long allergyDetailId) {
		List<ChildEnvironmentalAllergy> childEnvAllergies = childEnvAllergyRepository
				.findByAllergyDetailId(allergyDetailId);
		if (childEnvAllergies.isEmpty()) {
			throw new IllegalArgumentException(
					"Child Environmental Allergy results not found with Allergy Detail ID:" + allergyDetailId);
		}
		List<ChildEnvAllergyDto> childEnvironmentalAllergyDtos = new ArrayList<>();
		for (ChildEnvironmentalAllergy childEnvironmentalAllergy : childEnvAllergies) {
			ChildEnvAllergyDto childEnvAllergyDto = new ChildEnvAllergyDto();
			childEnvAllergyDto.setAllergyDetailId(allergyDetailId);
			childEnvAllergyDto.setEnvAllergy(childEnvironmentalAllergy.getEnvironmentalAllergy());
			childEnvAllergyDto.setId(childEnvironmentalAllergy.getId());
			childEnvironmentalAllergyDtos.add(childEnvAllergyDto);
		}
		return childEnvironmentalAllergyDtos;
	}

	@Transactional(readOnly = true)
	public ChildEnvAllergyDto getChildEnvironmentalAllergyByid(Long Id) {
		ChildEnvironmentalAllergy childEnvironmentalAllergy = childEnvAllergyRepository.findById(Id).orElseThrow(
				() -> new IllegalArgumentException("Child Environmental Allergy is not found with id: " + Id));
		ChildEnvAllergyDto childEnvAllergyDto = new ChildEnvAllergyDto();
		childEnvAllergyDto.setAllergyDetailId(childEnvironmentalAllergy.getAllergyDetail().getId());
		childEnvAllergyDto.setEnvAllergy(childEnvironmentalAllergy.getEnvironmentalAllergy());
		childEnvAllergyDto.setId(Id);
		return childEnvAllergyDto;
	}

	@Transactional
	public void deleteChildEnvironmentalAllergyById(Long id) {
		childEnvAllergyRepository.deleteById(id);
	}

	public boolean childEnvAllergyFound(Long id) {
		return childEnvAllergyRepository.existsById(id);
	}

	public boolean relationshipExists(Long allergyDetailId, Long envAllergyId) {
		return childEnvAllergyRepository.existsByAllergyDetailIdAndEnvironmentalAllergyId(allergyDetailId,
				envAllergyId);
	}
}
