package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.PresentingComplaintDTO;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.PresentingComplaint;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.PresentingComplaintRepository;

@Service
public class PresentingComplaintServiceImpl implements PresentingComplaintService {
	@Autowired
	private PresentingComplaintRepository presentingComplaintRepository;
	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public PresentingComplaint createPresentingComplaint(PresentingComplaintDTO presentingComplaintDTO) {
		PresentingComplaint presentingComplaint = new PresentingComplaint();
		BeanUtils.copyProperties(presentingComplaintDTO, presentingComplaint);
		Admit admit = admitRepository.findById(presentingComplaintDTO.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException());
		presentingComplaint.setAdmit(admit);
		return presentingComplaintRepository.save(presentingComplaint);
	}

	public PresentingComplaint getPresentingComplaintById(Long id) {
		Optional<PresentingComplaint> optionalPresentingComplaint = presentingComplaintRepository.findById(id);
		return optionalPresentingComplaint.orElse(null);
	}

	public List<PresentingComplaint> getAllPresentingComplaintsByAdmitId(Long admitId) {
		List<PresentingComplaint> entities = presentingComplaintRepository.findByAdmitId(admitId);
		return entities;
	}

	public List<PresentingComplaint> getAllPresentingComplaints() {
		return presentingComplaintRepository.findAll();
	}

	@Transactional
	public PresentingComplaint updatePresentingComplaint(Long id,
			PresentingComplaintDTO updatedPresentingComplaintDTO) {
		Optional<PresentingComplaint> optionalPresentingComplaint = presentingComplaintRepository.findById(id);

		if (optionalPresentingComplaint.isPresent()) {
			PresentingComplaint presentingComplaint = optionalPresentingComplaint.get();
			updatePresentingComplaintDetails(presentingComplaint, updatedPresentingComplaintDTO);
			return presentingComplaintRepository.save(presentingComplaint);
		} else {
			return null;
		}
	}

	private void updatePresentingComplaintDetails(PresentingComplaint presentingComplaint,
			PresentingComplaintDTO updatedPresentingComplaintDTO) {

		if (updatedPresentingComplaintDTO.getAdmitId() != null) {
			Admit admit = admitRepository.findById(updatedPresentingComplaintDTO.getAdmitId())
					.orElseThrow(() -> new IllegalArgumentException());
			presentingComplaint.setAdmit(admit);
		}
		if (updatedPresentingComplaintDTO.getComplaintName() != null) {
			presentingComplaint.setComplaintName(updatedPresentingComplaintDTO.getComplaintName());
		}
		if (updatedPresentingComplaintDTO.getDuration() != null) {
			presentingComplaint.setDuration(updatedPresentingComplaintDTO.getDuration());
		}

	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);
	}

	public RestApiResponseStatus deletePresentingComplaintById(long id) {
		Optional<PresentingComplaint> optionalPresentingComplaint = presentingComplaintRepository.findById(id);

		if (optionalPresentingComplaint.isPresent()) {
			presentingComplaintRepository.deleteById(id);
			return RestApiResponseStatus.DELETED;
		} else {
			return RestApiResponseStatus.NOT_FOUND;
		}
	}
}
