package com.sgic.ppms.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.AnthropometryDto;
import com.sgic.ppms.entities.Anthropometry;
import com.sgic.ppms.repositories.AnthropometryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnthropometryServiceImpl implements AnthropometryService {

	@Autowired
	private AnthropometryRepository anthropometryRepository;

	private Anthropometry BMIcalculator(AnthropometryDto anthropometryDto) {
		Anthropometry anthropometry = new Anthropometry();
		BeanUtils.copyProperties(anthropometryDto, anthropometry);
		double height = anthropometry.getHeight();
		double weight = anthropometry.getWeight();
		double bmi = weight / ((height / 100.0) * (height / 100.0));
		anthropometry.setBMI(bmi);
		return anthropometry;
	}

	@Override
	public Anthropometry createAnthropometry(AnthropometryDto anthropometryDTO) {
		Anthropometry updatedAnthropometry = BMIcalculator(anthropometryDTO);
		return anthropometryRepository.save(updatedAnthropometry);
	}

	@Override
	public Optional<Anthropometry> getAnthropometryById(Long id) {
		Optional<Anthropometry> optionalAnthropometry = anthropometryRepository.findById(id);
		if (optionalAnthropometry != null) {
			return optionalAnthropometry;
		} else {
			return null;
		}

	}

	@Override
	public Anthropometry updateAnthropometry(Long id, AnthropometryDto anthropometryDTO) {
		Anthropometry anthropometry = anthropometryRepository.findById(id).get();
		if (anthropometry != null) {
			Anthropometry updatedAnthropometry = BMIcalculator(anthropometryDTO);
			BeanUtils.copyProperties(updatedAnthropometry, anthropometry);
			anthropometry.setId(id);
			return anthropometryRepository.save(anthropometry);
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteAnthropometry(Long id) {
		Optional<Anthropometry> optionalAnthropometry = anthropometryRepository.findById(id);
		if (optionalAnthropometry.isPresent()) {
			anthropometryRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
