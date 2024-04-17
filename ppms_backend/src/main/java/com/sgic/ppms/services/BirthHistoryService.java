package com.sgic.ppms.services;

import com.sgic.ppms.dto.BirthHistoryDto;
import com.sgic.ppms.entities.BirthHistory;

import jakarta.persistence.EntityNotFoundException;

public interface BirthHistoryService {

	BirthHistory createBirthHistory(BirthHistoryDto birthHistoryDto);

	BirthHistory getBirthHistoryById(Long id) throws EntityNotFoundException;

	BirthHistory getBirthHistoryByChildId(String childId);

	boolean deleteBirthHistoryById(Long id);

	BirthHistory updateBirthHistory(Long id, BirthHistoryDto birthHistoryDto);
}
