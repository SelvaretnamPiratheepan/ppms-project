package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.OtherTestMDto;
import com.sgic.ppms.entities.OtherTestM;

public interface OtherTestMService {

	List<OtherTestM> getAllDetails();

	OtherTestM createOtherTestM(OtherTestMDto otherTestMDto);

	OtherTestM getById(Long id);

	boolean deleteById(Long id);

	boolean existsByTestname(String testname);
}
