package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.HaematologyDto;

public interface HaematologyService {
	HaematologyDto createHaematology(HaematologyDto haematologyDto);

	HaematologyDto updateHaematology(Long id, HaematologyDto haematologyDto);

	List<HaematologyDto> getHaematologybyAdmitId(Long admitId);

	HaematologyDto getHaematologybyId(Long Id);

	void deleteById(long Id);

	boolean haematologyFound(Long id);
}
