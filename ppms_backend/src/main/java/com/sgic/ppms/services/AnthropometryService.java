package com.sgic.ppms.services;

import java.util.Optional;

import com.sgic.ppms.dto.AnthropometryDto;
import com.sgic.ppms.entities.Anthropometry;

public interface AnthropometryService {
	Anthropometry createAnthropometry(AnthropometryDto anthropometryDTO);

	Optional<Anthropometry> getAnthropometryById(Long id);

	Anthropometry updateAnthropometry(Long id, AnthropometryDto anthropometryDTO);

	boolean deleteAnthropometry(Long id);
}