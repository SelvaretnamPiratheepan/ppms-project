package com.sgic.ppms.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.DietAllergyDto;
import com.sgic.ppms.entities.AllergyDetail;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.entities.DietAllergy;
import com.sgic.ppms.repositories.AllergyDetailRepository;
import com.sgic.ppms.repositories.ChildRepository;
import com.sgic.ppms.repositories.DietAllergyRepository;
import com.sgic.ppms.repositories.DietRepository;

@Service
public class DietAllergyServiceImpl implements DietAllergyService {

	@Autowired
	public DietAllergyRepository dietAllergyRepository;

	@Autowired
	public ChildRepository childRepository;

	@Autowired
	public AllergyDetailRepository allergyDetailRepository;

	@Autowired
	public DietRepository dietRepository;

	@Transactional
	public DietAllergyDto updateDietAllergy(Long id, DietAllergyDto dietAllergyDto) {
		DietAllergy dietAllergy = dietAllergyRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Diet Allergy is not present for the given Id: " + id));
		dietAllergyDto.setId(id);
		BeanUtils.copyProperties(dietAllergyDto, dietAllergy);
		AllergyDetail allergyDetail = allergyDetailRepository.findById(dietAllergyDto.getAllergyId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Allergy Detail is not present for the given Id: " + dietAllergyDto.getAllergyId()));
		dietAllergy.setAllergyDetail(allergyDetail);
		ChildDetail child = childRepository.findById(dietAllergyDto.getChildId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Child is not present for the given Id: " + dietAllergyDto.getChildId()));
		dietAllergy.setChildDetail(child);
		dietAllergyRepository.save(dietAllergy);
		return dietAllergyDto;

	}

	@Transactional
	public DietAllergyDto createDietAllergy(DietAllergyDto dietAllergyDto) {
		DietAllergy dietAllergy = new DietAllergy();
		BeanUtils.copyProperties(dietAllergyDto, dietAllergy);
		AllergyDetail allergyDetail = allergyDetailRepository.findById(dietAllergyDto.getAllergyId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Allergy Detail is not present for the given Id: " + dietAllergyDto.getAllergyId()));
		dietAllergy.setAllergyDetail(allergyDetail);
		ChildDetail child = childRepository.findById(dietAllergyDto.getChildId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Child is not present for the given Id: " + dietAllergyDto.getChildId()));
		dietAllergy.setChildDetail(child);
		DietAllergy createddietAllergy = dietAllergyRepository.save(dietAllergy);
		dietAllergyDto.setId(createddietAllergy.getId());
		return dietAllergyDto;
	}

	public void deleteDietAllergy(Long id) {
		dietAllergyRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public DietAllergyDto getDietAllergyById(Long id) {
		DietAllergy dietAllergy = dietAllergyRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Diet Allergy is not find for the given ID: " + id));
		DietAllergyDto dietAllergyDto = new DietAllergyDto();
		BeanUtils.copyProperties(dietAllergy, dietAllergyDto);
		dietAllergyDto.setAllergyId(dietAllergy.getAllergyDetail().getId());
		dietAllergyDto.setChildId(dietAllergy.getChildDetail().getChildId());
		return dietAllergyDto;
	}

	@Transactional(readOnly = true)
	public DietAllergyDto getDietAllergyByChildId(String childId) {
		DietAllergy dietAllergy = dietAllergyRepository.findByChildDetailChildId(childId);
		DietAllergyDto dietAllergyDto = new DietAllergyDto();
		BeanUtils.copyProperties(dietAllergy, dietAllergyDto);
		AllergyDetail allergyDetail = allergyDetailRepository.findById(dietAllergy.getAllergyDetail().getId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Allergy Detail is not present for the given Id: " + dietAllergyDto.getAllergyId()));
		dietAllergyDto.setAllergyId(allergyDetail.getId());
		dietAllergyDto.setChildId(dietAllergy.getChildDetail().getChildId());
		return dietAllergyDto;

	}

	public boolean dietAllergyExists(Long id) {
		return dietAllergyRepository.existsById(id);
	}

	public boolean childFoundDietAllergy(String childId) {
		return dietAllergyRepository.existsByChildDetailChildId(childId);
	}

	public boolean allergyExistsDietAllergy(Long allergyId) {
		return dietAllergyRepository.existsByAllergyDetailId(allergyId);
	}

	public Long getAllergyIdById(Long id) {
		DietAllergy dietAllergy = dietAllergyRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Diet Allergy is not find for the given ID: " + id));
		return dietAllergy.getAllergyDetail().getId();
	}

	public String getChildIdbyId(Long id) {
		DietAllergy dietAllergy = dietAllergyRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Diet Allergy is not find for the given ID: " + id));
		return dietAllergy.getChildDetail().getChildId();
	}
}