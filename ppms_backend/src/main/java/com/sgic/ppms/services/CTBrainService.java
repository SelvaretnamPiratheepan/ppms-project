package com.sgic.ppms.services;

import org.springframework.http.ResponseEntity;

import com.sgic.ppms.dto.CTBrainDTO;
import com.sgic.ppms.entities.CTBrain;
import com.sgic.ppms.util.ResponseWrapper;

public interface CTBrainService {

	ResponseEntity<ResponseWrapper<CTBrain>> saveCTBrain(CTBrainDTO ctBrainDTO);

	ResponseEntity<ResponseWrapper<CTBrain>> getCTBrainById(Long id);

	ResponseEntity<ResponseWrapper<CTBrain>> getCTBrainByAdmitId(Long admitId);

	ResponseEntity<ResponseWrapper<CTBrain>> updateCTBrain(Long id, CTBrainDTO ctBrainDTO);

	ResponseEntity<ResponseWrapper<String>> deleteCTBrain(Long id);
}
