package com.sgic.ppms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.ChildDetailDto;
import com.sgic.ppms.dto.ChildDetailSaveDto;
import com.sgic.ppms.dto.ChildDetailUpdateDto;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.entities.Ethnicity;
import com.sgic.ppms.repositories.ChildRepository;
import com.sgic.ppms.repositories.EthnicityRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private ChildRepository childRepository;
	@Autowired
	private EthnicityRepository ethnicityRepository;

	@Transactional
	public ChildDetail addPatient(ChildDetailSaveDto childDetailSaveDto) {
		ChildDetail childDetail = new ChildDetail();
		Ethnicity ethnicity = ethnicityRepository.findById(childDetailSaveDto.getEthnicityId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Ethnicity not found with id:" + childDetailSaveDto.getEthnicityId()));
		childDetail.setEthnicity(ethnicity);
		childDetail.setChildId(generateChildId(childDetailSaveDto.getDateOfBirth()));
		childDetail.setChildBmi(calculateBMI(childDetailSaveDto.getChildHeight(), childDetailSaveDto.getChildWeight()));
		BeanUtils.copyProperties(childDetailSaveDto, childDetail);
		return childRepository.save(childDetail);

	}

	@Transactional
	public ChildDetail updateChildDetails(ChildDetailUpdateDto childDetailUpdateDto) {
		ChildDetail childDetail = childRepository.findByChildId(childDetailUpdateDto.getChildId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Child details not found with this ChildId:" + childDetailUpdateDto.getChildId()));

		childDetail.setChildBmi(
				calculateBMI(childDetailUpdateDto.getChildHeight(), childDetailUpdateDto.getChildWeight()));

		Ethnicity ethnicity = ethnicityRepository.findById(childDetailUpdateDto.getEthnicityId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Ethnicity not found with this id:" + childDetailUpdateDto.getEthnicityId()));
		childDetail.setEthnicity(ethnicity);
		BeanUtils.copyProperties(childDetailUpdateDto, childDetail);
		return childRepository.save(childDetail);

	}

	public List<ChildDetailDto> getAllChild() {
		List<ChildDetail> getAllChild = childRepository.findAll();
		List<ChildDetailDto> childDetailDtoList = new ArrayList<>();
		for (ChildDetail childDetail : getAllChild) {
			ChildDetailDto childDetailDto = new ChildDetailDto();
			BeanUtils.copyProperties(childDetail, childDetailDto);
			childDetailDtoList.add(childDetailDto);
		}
		return childDetailDtoList;

	}

	private synchronized String generateChildId(LocalDate dateOfBirth) {
		long count = childRepository.countByDateOfBirth(dateOfBirth);
		return dateOfBirth.toString() + "-" + String.format("%04d", count + 1);

	}

	private double calculateBMI(double height, double weight) {
		validateHeightAndWeight(height, weight);
		return weight / (height * height);

	}

	private void validateHeightAndWeight(double height, double weight) {
		if (height <= 0 || weight <= 0) {
			throw new IllegalArgumentException("Height and weight must be greater than zero");
		}
	}

	public boolean ethnicityExists(Long ethnicityId) {
		return ethnicityRepository.existsById(ethnicityId);

	}

	public boolean childExists(String childId) {
		return childRepository.existsById(childId);

	}

	public List<ChildDetailDto> getPatientsByAnything(String anything) {
		List<ChildDetail> childDetails = childRepository.getPatientsByAnything(anything);
		List<ChildDetailDto> childDetailDtoList = new ArrayList<>();
		for (ChildDetail childDetail : childDetails) {
			ChildDetailDto childDetailDto = new ChildDetailDto();
			BeanUtils.copyProperties(childDetail, childDetailDto);
			childDetailDtoList.add(childDetailDto);

		}
		return childDetailDtoList;

	}

}
