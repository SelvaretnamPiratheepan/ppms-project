package com.sgic.ppms.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.GeneralConditionDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.GeneralCondition;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.GeneralConditionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GeneralConditionServiceImpl implements GeneralConditionService {

	@Autowired
	private GeneralConditionRepository generalConditionRepository;
	@Autowired
	private AdmitRepository admitRepository;

	public List<GeneralCondition> getAllGeneralConditions() {
		return generalConditionRepository.findAll();
	}

	@Transactional
	public GeneralConditionDto createGeneralConditionFromDto(GeneralConditionDto generalConditionDto) {

		GeneralCondition generalCondition = new GeneralCondition();
		BeanUtils.copyProperties(generalConditionDto, generalCondition);
		GeneralCondition savedGeneralCondition = generalConditionRepository.save(generalCondition);
		GeneralConditionDto savedgeneralConditionDto = new GeneralConditionDto();
		BeanUtils.copyProperties(savedGeneralCondition, savedgeneralConditionDto);

		Admit admit = admitRepository.findById(generalConditionDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + generalConditionDto.getAdmitId()));
		generalCondition.setAdmit(admit);

		if (generalConditionDto.getDoctorInformed().booleanValue()) {
			generalCondition.setTime(generalConditionDto.getTime());
		} else {
			generalCondition.setTime(null);
		}
		generalConditionDto.setId(savedGeneralCondition.getId());
		return generalConditionDto;
	}

	@Transactional
	public GeneralConditionDto updateGeneralCondition(Integer id, GeneralConditionDto generalConditionDto) {
		GeneralCondition existingGeneralCondition = generalConditionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("GeneralCondition not found with id: " + id));

		BeanUtils.copyProperties(generalConditionDto, existingGeneralCondition);

		GeneralCondition updatedGeneralCondition = generalConditionRepository.save(existingGeneralCondition);

		GeneralConditionDto updategeneralConditionDto = new GeneralConditionDto();
		BeanUtils.copyProperties(updatedGeneralCondition, updategeneralConditionDto);

		Admit admit = admitRepository.findById(generalConditionDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + generalConditionDto.getAdmitId()));
		updatedGeneralCondition.setAdmit(admit);

		generalConditionDto.setId(updatedGeneralCondition.getId());
		return generalConditionDto;
	}

	public GeneralConditionDto getGeneralConditionByAdmitId(Long admitId) {
		GeneralCondition generalCondition = generalConditionRepository.findByAdmitId(admitId);

		if (generalCondition == null) {
			throw new EntityNotFoundException("GeneralCondition not found with admit ID:" + admitId);
		}

		GeneralConditionDto generalConditionDto = new GeneralConditionDto();
		BeanUtils.copyProperties(generalCondition, generalConditionDto);

		generalConditionDto.setAdmitId(admitId);

		return generalConditionDto;
	}

}
