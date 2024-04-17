package com.sgic.ppms.services;

import org.springframework.http.ResponseEntity;

import com.sgic.ppms.dto.USSBrainDTO;
import com.sgic.ppms.entities.USSBrain;
import com.sgic.ppms.util.ResponseWrapper;

public interface USSBrainService {

	ResponseEntity<ResponseWrapper<USSBrain>> saveUSSBrain(USSBrainDTO ussBrainDTO);

	ResponseEntity<ResponseWrapper<USSBrain>> getUSSBrainById(Long id);

	ResponseEntity<ResponseWrapper<USSBrain>> getUSSBrainByAdmitId(Long admitId);

	ResponseEntity<ResponseWrapper<USSBrain>> updateUSSBrain(Long id, USSBrainDTO ussBrainDTO);

	ResponseEntity<ResponseWrapper<String>> deleteUSSBrain(Long id);
}
