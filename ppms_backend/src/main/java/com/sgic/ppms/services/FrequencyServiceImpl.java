package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.FrequencyDto;
import com.sgic.ppms.entities.Frequency;
import com.sgic.ppms.repositories.FrequencyRepository;

@Service
public class FrequencyServiceImpl implements FrequencyService {

	@Autowired
	private FrequencyRepository frequencyRepository;

	public List<Frequency> getAllFrequencies() {
		return frequencyRepository.findAll();
	}

	public Frequency CreateFrequency(FrequencyDto frequencyDto) {
		Frequency existingFrequency = frequencyRepository.findByName(frequencyDto.getName());
		if (existingFrequency != null) {
			return existingFrequency;
		} else {
			Frequency frequency = new Frequency();
			BeanUtils.copyProperties(frequencyDto, frequency);
			return frequencyRepository.save(frequency);
		}
	}

	public boolean deleteFrequencyById(int id) {
		Optional<Frequency> optionalFrequency = frequencyRepository.findById(id);
		if (optionalFrequency.isPresent()) {
			frequencyRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Optional<Frequency> getFrequencyById(long id) {
		return frequencyRepository.findById(id);
	}

	public int getFrequencyIdByName(String frequency_name) {
		Frequency frequency = frequencyRepository.findByName(frequency_name);
		return (frequency != null) ? frequency.getId() : -1;
	}

}
