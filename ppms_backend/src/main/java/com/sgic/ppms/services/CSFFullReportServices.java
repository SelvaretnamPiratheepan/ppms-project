package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.dto.CSFFullReportDto;
import com.sgic.ppms.entities.CSFFullReport;

public interface CSFFullReportServices {

	CSFFullReport create(CSFFullReportDto csfFullReportDto);

	CSFFullReport update(CSFFullReportDto updatedCSFFullReportDto);

	void delete(long id);

	boolean admitExists(Long admitId);

	List<CSFFullReport> getByAdmitId(long admitId);

	boolean csfFullReportExist(long id);

	Optional<CSFFullReport> getById(long id);

}
