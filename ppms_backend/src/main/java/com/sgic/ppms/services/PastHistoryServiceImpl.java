package com.sgic.ppms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.PastHistoryDto;
import com.sgic.ppms.entities.PastHistory;
import com.sgic.ppms.repositories.PastHistoryRepository;

@Service
public class PastHistoryServiceImpl implements PastHistoryService {

	@Autowired
	private PastHistoryRepository pastHistoryRepository;

	@Override
	public PastHistory createPastHistory(PastHistoryDto pastHistoryDto) {
		PastHistory pastHistory = new PastHistory();
		pastHistory.setDate(pastHistoryDto.getDate());
		pastHistory.setChildId(pastHistoryDto.getChildId());
		return pastHistoryRepository.save(pastHistory);
	}

	@Override
	public List<PastHistory> getPastHistoryByChildId(String childId) {
		return pastHistoryRepository.findByChildId(childId);
	}

	@Override
	public PastHistory updatePastHistory(int Id, PastHistoryDto pastHistoryDto) {
		PastHistory existingPastHistory = pastHistoryRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Past history not found with id: " + Id));
		existingPastHistory.setDate(pastHistoryDto.getDate());
		existingPastHistory.setChildId(pastHistoryDto.getChildId());
		return pastHistoryRepository.save(existingPastHistory);
	}

	@Override
	public boolean deletePastHistory(String childId) {
		List<PastHistory> pastHistory = pastHistoryRepository.findByChildId(childId);
		if (pastHistory.isEmpty()) {
			return false;
		}
		for (PastHistory pastHistory1 : pastHistory) {
			pastHistoryRepository.delete(pastHistory1);
		}
		return true;
	}
}
