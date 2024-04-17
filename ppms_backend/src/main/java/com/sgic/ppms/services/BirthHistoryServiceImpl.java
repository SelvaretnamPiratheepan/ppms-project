package com.sgic.ppms.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.BirthHistoryDto;
import com.sgic.ppms.entities.BirthHistory;
import com.sgic.ppms.repositories.BirthHistoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BirthHistoryServiceImpl implements BirthHistoryService {

	@Autowired
	private BirthHistoryRepository repo;

	@Override
	public BirthHistory createBirthHistory(BirthHistoryDto birthHistoryDto) {
		BirthHistory birthHistory = new BirthHistory();
		BeanUtils.copyProperties(birthHistoryDto, birthHistory);
		return repo.save(birthHistory);
	}

	@Override
	public BirthHistory getBirthHistoryById(Long id) {
		return repo.findById(id).get();
	}

	@Override
	public BirthHistory getBirthHistoryByChildId(String childId) {
		return repo.findByChildId(childId);
	}

	@Override
	public BirthHistory updateBirthHistory(Long id, BirthHistoryDto updatedBirthHistoryDto) {
		BirthHistory existingBirthHistory = repo.findById(id).get();
		if (existingBirthHistory != null) {
			BeanUtils.copyProperties(updatedBirthHistoryDto, existingBirthHistory);
			existingBirthHistory.setId(id);
			return repo.save(existingBirthHistory);
		}
		return null;
	}

	@Override
	public boolean deleteBirthHistoryById(Long id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		}
		return false;
	}
}
