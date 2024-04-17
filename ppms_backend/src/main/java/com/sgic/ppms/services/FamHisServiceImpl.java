package com.sgic.ppms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.entities.Family_And_Social_History;
import com.sgic.ppms.repositories.FamHisRepository;

@Service
public class FamHisServiceImpl implements FamHisService {
	@Autowired
	private FamHisRepository famHisRepository;

	@Override
	public Family_And_Social_History createFamilyHistory(Family_And_Social_History familyAndSocialHistory) {
		return famHisRepository.save(familyAndSocialHistory);
	}

	@Override
	public Family_And_Social_History getFamilyHistoryById(Integer id) {
		return famHisRepository.findById(id).orElse(null);
	}

	@Override
	public List<Family_And_Social_History> getAllFamilyHistories() {
		return famHisRepository.findAll();
	}

	@Override
	public void deleteFamilyHistory(Integer id) {
		famHisRepository.deleteById(id);
	}

	@Override
	public Family_And_Social_History updateFamilyHistory(Integer id, Family_And_Social_History familyHistory) {
		if (famHisRepository.existsById(id)) {
			familyHistory.setId(id);
			return famHisRepository.save(familyHistory);
		}
		return null;
	}
}
