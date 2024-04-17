package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.CSFFullReportDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.CSFFullReport;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.CSFFullReportRepository;

import jakarta.transaction.Transactional;

@Service
public class CSFFullReportServicesImpl implements CSFFullReportServices {

	@Autowired
	private CSFFullReportRepository csfFullReportRepository;
	@Autowired
	private AdmitRepository admitRepository;

	public Optional<CSFFullReport> getById(long id) {
		return csfFullReportRepository.findById(id);

	}

	@Transactional
	public CSFFullReport create(CSFFullReportDto csfFullReportDto) {

		CSFFullReport csfFullReport = new CSFFullReport();
		Admit admit = admitRepository.findById(csfFullReportDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id:" + csfFullReportDto.getAdmitId()));

		csfFullReport.setAdmit(admit);
		csfFullReportDto.setId(0L);
		BeanUtils.copyProperties(csfFullReportDto, csfFullReport);
		return csfFullReportRepository.save(csfFullReport);

	}

	@Transactional
	public CSFFullReport update(CSFFullReportDto csfFullReportDto) {

		CSFFullReport csfFullReport = new CSFFullReport();
		Admit admit = admitRepository.findById(csfFullReportDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id:" + csfFullReportDto.getAdmitId()));

		csfFullReport.setAdmit(admit);
		BeanUtils.copyProperties(csfFullReportDto, csfFullReport);
		return csfFullReportRepository.save(csfFullReport);

	}

	public void delete(long id) {
		csfFullReportRepository.findById(id);

	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);
	}

	public List<CSFFullReport> getByAdmitId(long admitId) {
		return csfFullReportRepository.findByAdmitId(admitId);
	}

	public boolean csfFullReportExist(long id) {
		return csfFullReportRepository.existsById(id);

	}

}
