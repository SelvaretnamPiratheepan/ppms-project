package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.ComplaintDTO;
import com.sgic.ppms.entities.Complaint;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.ComplaintService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.COMPLAINT)
public class ComplaintController {

	@Autowired
	private ComplaintService complaintService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<?> createComplaint(@Valid @RequestBody ComplaintDTO complaint) {
		Complaint createdComplaint = complaintService.createComplaint(complaint);
		if (createdComplaint != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, createdComplaint));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
							RestApiResponseStatus.NOT_CREATED.getStatus(), ValidationMessages.CREATE_FAILED));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<?> getComplaintById(@PathVariable("id") Long id) {
		Complaint complaint = complaintService.getComplaintById(id);
		if (complaint != null) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, complaint));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.INVALID_ID));
		}
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<?> getAllComplaints() {
		List<Complaint> complaints = complaintService.getAllComplaints();
		if (complaints.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
							RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_RECORDS));
		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatus.FOUND.getCode(), ValidationMessages.RETRIEVED, complaints));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<?> updateComplaint(@PathVariable("id") Long id,
			@Valid @RequestBody ComplaintDTO updatedComplaintDto) {
		Complaint complaint = complaintService.updateComplaint(id, updatedComplaintDto);
		if (complaint != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, complaint));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_UPDATED.getCode(),
							RestApiResponseStatus.NOT_UPDATED.getStatus(), ValidationMessages.INVALID_ID));
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<?> deleteComplaint(@PathVariable("id") Long id) {
		Boolean status = complaintService.deleteComplaint(id);
		if (status) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
							RestApiResponseStatus.DELETED.getStatus(), ValidationMessages.DELETE_SUCCESS));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(RestApiResponseStatus.NOT_DELETED.getCode(),
							RestApiResponseStatus.NOT_DELETED.getStatus(), ValidationMessages.INVALID_ID));
		}
	}
}