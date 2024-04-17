package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.HistoryOfPresentingComplaintDto;
import com.sgic.ppms.entities.HistoryOfPresentingComplaint;
import com.sgic.ppms.services.HistoryOfPresentingComplaintService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.HISTORY_OF_PRESENTING_COMPLAINT)
public class HistoryOfPresentingComplaintController {

	@Autowired
	private HistoryOfPresentingComplaintService historyService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<HistoryOfPresentingComplaint>> saveHistoryOfPresentingComplaint(
			@RequestBody HistoryOfPresentingComplaintDto dto) {
		return historyService.saveHistoryOfPresentingComplaint(dto);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<HistoryOfPresentingComplaint>> updateHistoryOfPresentingComplaint(
			@PathVariable("id") Long id, @RequestBody HistoryOfPresentingComplaintDto dto) {
		return historyService.updateHistoryOfPresentingComplaint(id, dto);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteHistoryOfPresentingComplaint(@PathVariable("id") Long id) {
		return historyService.deleteHistoryOfPresentingComplaint(id);
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<List<HistoryOfPresentingComplaint>>> getHistoryOfPresentingComplaintByAdmitId(
			@PathVariable Long admitId) {
		return historyService.getHistoryOfPresentingComplaintByAdmitId(admitId);
	}
}
