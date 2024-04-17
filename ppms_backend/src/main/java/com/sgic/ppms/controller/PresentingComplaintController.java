package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.sgic.ppms.dto.PresentingComplaintDTO;
import com.sgic.ppms.entities.PresentingComplaint;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.PresentingComplaintService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.PRESENTING_COMPLAINT)
public class PresentingComplaintController {

	@Autowired
	private PresentingComplaintService presentingComplaintService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<PresentingComplaintDTO>> createPresentingComplaint(
			@RequestBody @Valid PresentingComplaintDTO presentingComplaintDTO) {
		boolean admitfound = presentingComplaintService.admitExists(presentingComplaintDTO.getAdmitId());
		if (!admitfound) {

			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}

		PresentingComplaint createdPresentingComplaint = presentingComplaintService
				.createPresentingComplaint(presentingComplaintDTO);

		PresentingComplaintDTO createdPresentingComplaintDTO = new PresentingComplaintDTO();
		BeanUtils.copyProperties(createdPresentingComplaint, createdPresentingComplaintDTO);
		createdPresentingComplaintDTO.setAdmitId(createdPresentingComplaint.getAdmit().getId());

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.CREATE_SUCCESS, createdPresentingComplaintDTO));

	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<PresentingComplaint>> getPresentingComplaintById(
			@PathVariable("id") Long id) {
		PresentingComplaint presentingComplaint = presentingComplaintService.getPresentingComplaintById(id);

		if (presentingComplaint == null) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.CREATE_FAILED, null));
		}

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.RETRIEVED, presentingComplaint));
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<PresentingComplaint>>> getAllPresentingComplaints() {
		List<PresentingComplaint> presentingComplaints = presentingComplaintService.getAllPresentingComplaints();
		if (!presentingComplaints.isEmpty()) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.RETRIEVED, presentingComplaints));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
				ValidationMessages.NO_RECORDS, null));

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<List<PresentingComplaint>>> getAllPresentingComplaintsByAdmitId(
			@PathVariable("admitId") Long admitId) {
		boolean admitfound = presentingComplaintService.admitExists(admitId);
		if (!admitfound) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}

		List<PresentingComplaint> presentingComplaints = presentingComplaintService
				.getAllPresentingComplaintsByAdmitId(admitId);
		if (!presentingComplaints.isEmpty()) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.RETRIEVED, presentingComplaints));
		}
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.NOT_FOUND, null));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<PresentingComplaint>> updatePresentingComplaint(@PathVariable("id") Long id,
			@RequestBody PresentingComplaintDTO presentingComplaintDTO) {
		boolean admitfound = presentingComplaintService.admitExists(presentingComplaintDTO.getAdmitId());
		if (!admitfound) {

			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}

		PresentingComplaint presentingComplaintUpdated = presentingComplaintService.updatePresentingComplaint(id,
				presentingComplaintDTO);
		if (presentingComplaintUpdated != null) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.UPDATE_SUCCESS, presentingComplaintUpdated));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
				ValidationMessages.NO_RECORDS, null));

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deletePresentingComplaintById(@PathVariable("id") Long id) {
		RestApiResponseStatus presentingComplaint = presentingComplaintService.deletePresentingComplaintById(id);
		if (presentingComplaint == null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.NO_RECORDS, null));
		}
		presentingComplaintService.deletePresentingComplaintById(id);

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.DELETE_SUCCESS, null));

	}
}
