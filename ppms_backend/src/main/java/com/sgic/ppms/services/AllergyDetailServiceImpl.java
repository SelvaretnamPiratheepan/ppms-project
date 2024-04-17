package com.sgic.ppms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.AllergyDetailDto;
import com.sgic.ppms.entities.AllergyDetail;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.repositories.AllergyDetailRepository;
import com.sgic.ppms.repositories.ChildRepository;

@Service
public class AllergyDetailServiceImpl implements AllergyDetailService {

	@Autowired
	private AllergyDetailRepository allergyDetailRepository;

	@Autowired
	private ChildRepository childRepository;

	@Transactional
	public AllergyDetailDto createAllergyDetail(AllergyDetailDto allergyDetailDto) {
		AllergyDetail allergyDetail = new AllergyDetail();
		allergyDetail.setHasAllergy(allergyDetailDto.isHasAllergy());
		ChildDetail childDetail = childRepository.findById(allergyDetailDto.getChildId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Child Detail is not found with id: " + allergyDetailDto.getChildId()));
		allergyDetail.setChildDetail(childDetail);
		AllergyDetail savedAllergyDetail = allergyDetailRepository.save(allergyDetail);
		allergyDetailDto.setId(savedAllergyDetail.getId());
		return allergyDetailDto;
	}

	@Transactional
	public AllergyDetailDto updateAllergyDetail(Long id, AllergyDetailDto allergyDetailDto) {
		AllergyDetail existingAllergyDetail = allergyDetailRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Allergy Detail is not found with id: " + id));
		existingAllergyDetail.setHasAllergy(allergyDetailDto.isHasAllergy());
		existingAllergyDetail.setId(id);
		ChildDetail childDetail = childRepository.findById(allergyDetailDto.getChildId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Child Detail is not found with id: " + allergyDetailDto.getChildId()));
		existingAllergyDetail.setChildDetail(childDetail);
		allergyDetailRepository.save(existingAllergyDetail);
		allergyDetailDto.setId(id);
		return allergyDetailDto;
	}

	@Transactional(readOnly = true)
	public AllergyDetailDto getAllergyDetailByChildid(String childId) {
		AllergyDetail allergyDetail = allergyDetailRepository.findByChildDetailChildId(childId);
		if (allergyDetail == null) {
			throw new IllegalArgumentException("Allergy Detail is not found with Child ID: " + childId);
		}
		AllergyDetailDto allergyDetailDto = new AllergyDetailDto();
		allergyDetailDto.setId(allergyDetail.getId());
		allergyDetailDto.setHasAllergy(allergyDetail.isHasAllergy());
		allergyDetailDto.setChildId(childId);
		return allergyDetailDto;
	}

	@Transactional(readOnly = true)
	public AllergyDetailDto getAllergyDetailById(Long Id) {
		AllergyDetail allergyDetail = allergyDetailRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Allergy Detail is not found with id: " + Id));
		AllergyDetailDto allergyDetailDto = new AllergyDetailDto();
		allergyDetailDto.setId(Id);
		allergyDetailDto.setHasAllergy(allergyDetail.isHasAllergy());
		allergyDetailDto.setChildId(allergyDetail.getChildDetail().getChildId());
		return allergyDetailDto;
	}

	public boolean childExistsAllergy(String childId) {
		return allergyDetailRepository.existsByChildDetailChildId(childId);
	}

	public boolean childFound(String childId) {
		return childRepository.existsById(childId);
	}

	@Transactional
	public void deleteAllergyDetailsById(Long id) {
		allergyDetailRepository.deleteById(id);
	}

	public boolean allergyDetailExistsById(Long Id) {
		return allergyDetailRepository.existsById(Id);
	}

	public String getChildIdById(Long id) {
		AllergyDetail allergyDetail = allergyDetailRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Allergy Detail is not found with the given Id" + id));
		return allergyDetail.getChildDetail().getChildId();
	}

}
