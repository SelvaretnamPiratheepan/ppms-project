package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.ChildDetailDto;
import com.sgic.ppms.dto.ChildDetailSaveDto;
import com.sgic.ppms.dto.ChildDetailUpdateDto;
import com.sgic.ppms.entities.ChildDetail;

public interface PatientService {

	ChildDetail addPatient(ChildDetailSaveDto childDetailSaveDto);

	List<ChildDetailDto> getAllChild();

	boolean ethnicityExists(Long ethnicityId);

	boolean childExists(String childId);

	List<ChildDetailDto> getPatientsByAnything(String anything);

	ChildDetail updateChildDetails(ChildDetailUpdateDto childDetailUpdateDto);

}
