package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.EthnicityDto;
import com.sgic.ppms.entities.Ethnicity;

public interface EthnicityServices {
	List<Ethnicity> getAllEthnicities();

	Ethnicity getEthnicityById(long id);

	Ethnicity createEthnicity(EthnicityDto ethnicityDto);

	Ethnicity updateEthnicity(long id, EthnicityDto updatedEthnicityDto);

	boolean deleteEthnicity(long id);
}
