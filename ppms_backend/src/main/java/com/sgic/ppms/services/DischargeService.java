package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.DischargeDto;
import com.sgic.ppms.entities.Discharge;

public interface DischargeService {
	List<Discharge> getAllDischarges();

	DischargeDto createDischargeFromDto(DischargeDto dischargeDto);

	DischargeDto updateDischarge(Long id, DischargeDto dischargeDto);

	DischargeDto getDischargeByAdmitId(Long admitId);

	boolean existsById(Long id);

	boolean admitIdExists(Long admitId);

	boolean placeIdExists(Long placeId);

	boolean causeOfDeathIdExists(Long causeOfDeathId);

	boolean admitIdExistsInDiagnosisDetails(Long admitId);

	Long getAdmitIdByRecordId(Long id);
}
