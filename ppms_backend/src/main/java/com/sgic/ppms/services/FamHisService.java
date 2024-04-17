package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.entities.Family_And_Social_History;

public interface FamHisService {
	Family_And_Social_History getFamilyHistoryById(Integer id);

	List<Family_And_Social_History> getAllFamilyHistories();

	void deleteFamilyHistory(Integer id);

	Family_And_Social_History updateFamilyHistory(Integer id, Family_And_Social_History familyHistory);

	Family_And_Social_History createFamilyHistory(Family_And_Social_History familyAndSocialHistory);

}
