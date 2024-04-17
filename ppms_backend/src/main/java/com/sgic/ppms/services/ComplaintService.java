package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.ComplaintDTO;
import com.sgic.ppms.entities.Complaint;

public interface ComplaintService {
	Complaint createComplaint(ComplaintDTO complaint);

	Complaint getComplaintById(Long id);

	List<Complaint> getAllComplaints();

	Complaint updateComplaint(Long id, ComplaintDTO updatedComplaintDto);

	boolean deleteComplaint(Long id);
}
