package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.HistoryOfPresentingComplaintDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.HistoryOfPresentingComplaint;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.HistoryOfPresentingComplaintRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@Service
public class HistoryOfPresentingComplaintServiceImpl implements HistoryOfPresentingComplaintService {

	@Autowired
	private HistoryOfPresentingComplaintRepository historyOfPresentingComplaintRepository;

	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public ResponseEntity<ResponseWrapper<HistoryOfPresentingComplaint>> saveHistoryOfPresentingComplaint(
			HistoryOfPresentingComplaintDto dto) {
		Optional<Admit> admitOptional = admitRepository.findById(dto.getAdmitId());
		if (admitOptional.isPresent()) {
			HistoryOfPresentingComplaint history = new HistoryOfPresentingComplaint();
			history.setAdmit(admitOptional.get());
			history.setHistoryOfComplaint(dto.getHistoryOfComplaint());
			history.setDurationValue(dto.getDurationValue());
			history.setTimeUnit(dto.getTimeUnit());

			historyOfPresentingComplaintRepository.save(history);
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.CREATE_SUCCESS, history), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.INVALID_ADMITID + dto.getAdmitId(), null), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<ResponseWrapper<HistoryOfPresentingComplaint>> updateHistoryOfPresentingComplaint(Long id,
			HistoryOfPresentingComplaintDto dto) {
		Optional<HistoryOfPresentingComplaint> historyOptional = historyOfPresentingComplaintRepository.findById(id);
		if (historyOptional.isPresent()) {
			HistoryOfPresentingComplaint history = historyOptional.get();
			history.setHistoryOfComplaint(dto.getHistoryOfComplaint());
			history.setDurationValue(dto.getDurationValue());
			history.setTimeUnit(dto.getTimeUnit());

			Optional<Admit> admitOptional = admitRepository.findById(dto.getAdmitId());
			if (!admitOptional.isPresent()) {
				return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
						ValidationMessages.INVALID_ADMITID + dto.getAdmitId(), null), HttpStatus.BAD_REQUEST);
			}

			history.setAdmit(admitOptional.get());

			historyOfPresentingComplaintRepository.save(history);

			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
					ValidationMessages.UPDATE_SUCCESS, history), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseWrapper<String>> deleteHistoryOfPresentingComplaint(Long id) {
		Optional<HistoryOfPresentingComplaint> historyOptional = historyOfPresentingComplaintRepository.findById(id);
		if (historyOptional.isPresent()) {
			historyOfPresentingComplaintRepository.delete(historyOptional.get());
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					ValidationMessages.DELETE_SUCCESS, null), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseWrapper<List<HistoryOfPresentingComplaint>>> getHistoryOfPresentingComplaintByAdmitId(
			Long admitId) {
		Optional<Admit> admitOptional = admitRepository.findById(admitId);
		if (admitOptional.isPresent()) {
			List<HistoryOfPresentingComplaint> historyList = historyOfPresentingComplaintRepository
					.findByAdmitId(admitId);
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
					ValidationMessages.GET_SUCCESS, historyList), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ADMITID + admitId, null), HttpStatus.NOT_FOUND);
		}
	}

}