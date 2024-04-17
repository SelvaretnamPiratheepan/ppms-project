package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.FrequencyDto;
import com.sgic.ppms.entities.Frequency;

public interface FrequencyService {
	Frequency CreateFrequency(FrequencyDto frequencyDto);

	List<Frequency> getAllFrequencies();

	boolean deleteFrequencyById(int id);

	Optional<Frequency> getFrequencyById(long id);

	int getFrequencyIdByName(String frequency_name);

}