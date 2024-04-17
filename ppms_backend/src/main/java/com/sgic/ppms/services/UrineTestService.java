package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.UrineTestDto;
import com.sgic.ppms.entities.UrineTest;
import com.sgic.ppms.enums.RestApiResponseStatus;

public interface UrineTestService {
	UrineTest createUrineTest(UrineTestDto urineTestDto);

	UrineTest getUrineTestById(Long id);

	List<UrineTest> getAllUrineTest();

	List<UrineTest> getAllUrineTestByAdmitId(Long admitId);

	UrineTest updateUrineTest(Long id, UrineTestDto updatedUrineTestDto);

	RestApiResponseStatus deleteUrineTest(long id);

	boolean admitExists(Long admitId);
}
