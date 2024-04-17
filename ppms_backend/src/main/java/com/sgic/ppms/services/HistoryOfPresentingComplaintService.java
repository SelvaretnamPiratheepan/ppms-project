package com.sgic.ppms.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sgic.ppms.dto.HistoryOfPresentingComplaintDto;
import com.sgic.ppms.entities.HistoryOfPresentingComplaint;
import com.sgic.ppms.util.ResponseWrapper;

import jakarta.transaction.Transactional;

public interface HistoryOfPresentingComplaintService {
	// @Transactional
	ResponseEntity<ResponseWrapper<HistoryOfPresentingComplaint>> saveHistoryOfPresentingComplaint(
			HistoryOfPresentingComplaintDto dto);

	@Transactional
	ResponseEntity<ResponseWrapper<HistoryOfPresentingComplaint>> updateHistoryOfPresentingComplaint(Long id,
			HistoryOfPresentingComplaintDto dto);

	ResponseEntity<ResponseWrapper<String>> deleteHistoryOfPresentingComplaint(Long id);

	ResponseEntity<ResponseWrapper<List<HistoryOfPresentingComplaint>>> getHistoryOfPresentingComplaintByAdmitId(
			Long admitId);
}
