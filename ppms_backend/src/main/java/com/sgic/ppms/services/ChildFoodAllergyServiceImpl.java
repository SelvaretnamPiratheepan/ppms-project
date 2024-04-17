package com.sgic.ppms.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.ChildFoodAllergyDto;
import com.sgic.ppms.entities.AllergyDetail;
import com.sgic.ppms.entities.ChildFoodAllergy;
import com.sgic.ppms.entities.Food_Allergy;
import com.sgic.ppms.repositories.AllergyDetailRepository;
import com.sgic.ppms.repositories.ChildFoodAllergyRepository;
import com.sgic.ppms.repositories.Food_AllergyRepository;

@Service
public class ChildFoodAllergyServiceImpl implements ChildFoodAllergyService {
	private final Logger logger = LoggerFactory.getLogger(ChildFoodAllergyServiceImpl.class);

	@Autowired
	private ChildFoodAllergyRepository childFoodAllergyRepository;

	@Autowired
	private AllergyDetailRepository allergyDetailRepository;

	@Autowired
	private Food_AllergyRepository foodAllergyRepository;

	@Transactional
	public ChildFoodAllergyDto createChildFoodAllergy(ChildFoodAllergyDto childFoodAllergyDto) {
		logger.info("Creating new Child Food Allergy: {}", childFoodAllergyDto);
		ChildFoodAllergy childFoodAllergy = new ChildFoodAllergy();
		Food_Allergy foodAllergy = foodAllergyRepository.findById(childFoodAllergyDto.getFoodAllergy().getId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Food Allergy is not found with id: " + childFoodAllergyDto.getFoodAllergy().getId()));
		childFoodAllergy.setFoodAllergy(foodAllergy);
		AllergyDetail allergyDetail = allergyDetailRepository.findById(childFoodAllergyDto.getAllergyDetailId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Allergy Detail is not found with id: " + childFoodAllergyDto.getAllergyDetailId()));
		childFoodAllergy.setAllergyDetail(allergyDetail);
		ChildFoodAllergy savedChildFoodAllergy = childFoodAllergyRepository.save(childFoodAllergy);
		childFoodAllergyDto.setId(savedChildFoodAllergy.getId());
		childFoodAllergyDto.setFoodAllergy(foodAllergy);
		return childFoodAllergyDto;
	}

	@Transactional
	public ChildFoodAllergyDto updateChildFoodAllergy(Long id, ChildFoodAllergyDto childFoodAllergyDto) {
		ChildFoodAllergy existingChildFoodAllergy = childFoodAllergyRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Child Food Allergy is not found with id: " + id));

		Food_Allergy FoodAllergy = foodAllergyRepository.findById(childFoodAllergyDto.getFoodAllergy().getId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Food Allergy not found with id: " + childFoodAllergyDto.getFoodAllergy()));
		existingChildFoodAllergy.setFoodAllergy(FoodAllergy);

		AllergyDetail allergyDetail = allergyDetailRepository.findById(childFoodAllergyDto.getAllergyDetailId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Allergy Detail is not found with id: " + childFoodAllergyDto.getAllergyDetailId()));
		existingChildFoodAllergy.setAllergyDetail(allergyDetail);
		ChildFoodAllergy updatedChildFoodAllergy = childFoodAllergyRepository.save(existingChildFoodAllergy);
		childFoodAllergyDto.setId(updatedChildFoodAllergy.getId());
		childFoodAllergyDto.setFoodAllergy(FoodAllergy);
		return childFoodAllergyDto;
	}

	@Transactional(readOnly = true)
	public List<ChildFoodAllergyDto> getChildFoodAllergyByAllergyid(Long allergyDetailId) {
		List<ChildFoodAllergy> childFoodAllergies = childFoodAllergyRepository.findByAllergyDetailId(allergyDetailId);
		if (childFoodAllergies.isEmpty()) {
			throw new IllegalArgumentException(
					"Child Food Allergy results not found with Allergy Detail ID:" + allergyDetailId);
		}
		List<ChildFoodAllergyDto> childFoodAllergyDtos = new ArrayList<>();
		for (ChildFoodAllergy childFoodAllergy : childFoodAllergies) {
			ChildFoodAllergyDto childFoodAllergyDto = new ChildFoodAllergyDto();
			childFoodAllergyDto.setFoodAllergy(childFoodAllergy.getFoodAllergy());
			childFoodAllergyDto.setAllergyDetailId(allergyDetailId);
			childFoodAllergyDto.setId(childFoodAllergy.getId());
			childFoodAllergyDtos.add(childFoodAllergyDto);
		}
		return childFoodAllergyDtos;
	}

	@Transactional(readOnly = true)
	public ChildFoodAllergyDto getChildFoodAllergyByid(Long Id) {

		ChildFoodAllergy childFoodAllergy = childFoodAllergyRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Child Food Allergy is not found with id: " + Id));
		ChildFoodAllergyDto childFoodAllergyDto = new ChildFoodAllergyDto();
		childFoodAllergyDto.setFoodAllergy(childFoodAllergy.getFoodAllergy());
		childFoodAllergyDto.setAllergyDetailId(childFoodAllergy.getAllergyDetail().getId());
		childFoodAllergyDto.setId(Id);
		return childFoodAllergyDto;
	}

	@Transactional
	public void deleteChildFoodAllergyById(Long id) {
		childFoodAllergyRepository.deleteById(id);
	}

	public boolean childFoodAllergyFound(Long id) {
		return childFoodAllergyRepository.existsById(id);
	}

	public boolean relationshipExists(Long allergyDetailId, int foodAllergyId) {
		return childFoodAllergyRepository.existsByAllergyDetailIdAndFoodAllergyId(allergyDetailId, foodAllergyId);
	}
}
