package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.PresentingComplaintDTO;
import com.sgic.ppms.entities.PresentingComplaint;
import com.sgic.ppms.enums.RestApiResponseStatus;

public interface PresentingComplaintService {

	PresentingComplaint createPresentingComplaint(PresentingComplaintDTO presentingComplaintDTO);

	PresentingComplaint getPresentingComplaintById(Long id);

	List<PresentingComplaint> getAllPresentingComplaints();

	PresentingComplaint updatePresentingComplaint(Long id, PresentingComplaintDTO updatedPresentingComplaintDTO);

	List<PresentingComplaint> getAllPresentingComplaintsByAdmitId(Long admitId);

	boolean admitExists(Long admitId);

	RestApiResponseStatus deletePresentingComplaintById(long id);

}
