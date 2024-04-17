package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.ExaminationDto;
import com.sgic.ppms.entities.Examination;

public interface ExaminationService {
	Examination createExamination(ExaminationDto examinationDTO);

	ExaminationDto getExaminationById(Long id);

	List<ExaminationDto> getExaminationByChildId(String childId);

	ExaminationDto getExaminationByAdmitId(Long admitId);

	Examination updateExamination(Long id, ExaminationDto examinationDTO);

	boolean deleteExamination(Long id);
}