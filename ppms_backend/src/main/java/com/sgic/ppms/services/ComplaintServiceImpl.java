package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.ComplaintDTO;
import com.sgic.ppms.entities.Complaint;
import com.sgic.ppms.repositories.ComplaintRepository;

@Service
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

	@Autowired
	private ComplaintRepository complaintRepository;

	@Override
	public Complaint createComplaint(ComplaintDTO complaintDto) {
		Complaint complaint = new Complaint();
		BeanUtils.copyProperties(complaintDto, complaint);
		return complaintRepository.save(complaint);
	}

	@Override
	public Complaint getComplaintById(Long id) {
		return complaintRepository.findById(id).orElse(null);
	}

	@Override
	public List<Complaint> getAllComplaints() {
		return complaintRepository.findAll();
	}

	@Override
	public Complaint updateComplaint(Long id, ComplaintDTO updatedComplaintDto) {
		Optional<Complaint> optionalComplaint = complaintRepository.findById(id);
		return optionalComplaint.map(complaint -> {
			BeanUtils.copyProperties(updatedComplaintDto, complaint);
			complaint.setId(id);
			return complaintRepository.save(complaint);
		}).orElse(null);
	}

	@Override
	public boolean deleteComplaint(Long id) {
		if (complaintRepository.existsById(id)) {
			complaintRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
