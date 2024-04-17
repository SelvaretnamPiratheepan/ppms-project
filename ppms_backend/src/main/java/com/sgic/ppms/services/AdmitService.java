package com.sgic.ppms.services;

import java.time.LocalDate;
import java.util.List;

import com.sgic.ppms.dto.AdmitDTO;

public interface AdmitService {

	AdmitDTO createAdmit(AdmitDTO admitDTO);

	AdmitDTO getAdmitById(Long id);

	AdmitDTO updateAdmit(Long id, AdmitDTO admitDTO);

	void deleteAdmit(Long id);

	boolean admitExists(Long id);

	List<AdmitDTO> getAdmitsBetweenDates(LocalDate fromDate, LocalDate endDate);

}
