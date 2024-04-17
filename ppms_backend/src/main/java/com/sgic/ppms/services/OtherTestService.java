package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.OtherTestDto;
import com.sgic.ppms.entities.OtherTest;

public interface OtherTestService {

	boolean admitExists(Long admitId);

	OtherTest create(OtherTestDto otherTestDto);

	List<OtherTest> getByAdmitId(long admitId);

	List<OtherTest> getByTestId(Long testmId);

	Optional<OtherTest> getById(long id);

	boolean deleteById(long id);

	boolean testmExists(Long otherTestMId);

	boolean existsById(long id);

	OtherTest update(OtherTestDto otherTestDto);

}
