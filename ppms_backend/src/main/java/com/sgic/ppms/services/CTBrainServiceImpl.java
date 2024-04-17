package com.sgic.ppms.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.CTBrainDTO;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.CTBrain;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.CTBrainRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@Service
public class CTBrainServiceImpl implements CTBrainService {

	@Autowired
	private CTBrainRepository ctBrainRepository;

	@Autowired
	private AdmitRepository admitRepository;

	@Override
	@Transactional
	public ResponseEntity<ResponseWrapper<CTBrain>> saveCTBrain(CTBrainDTO ctBrainDTO) {

		Optional<Admit> admitOptional = admitRepository.findById(ctBrainDTO.getAdmitId());
		if (admitOptional.isPresent()) {
			CTBrain ctBrain = new CTBrain();
			ctBrain.setAdmit(admitOptional.get());
			ctBrain.setDate(ctBrainDTO.getDate());
			ctBrain.setResult(ctBrainDTO.getResult());
			ctBrain.setDetails(ctBrainDTO.getDetails());
			ctBrainRepository.save(ctBrain);
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.CREATE_SUCCESS, ctBrain), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(
					new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
							ValidationMessages.INVALID_ADMITID + ctBrainDTO.getAdmitId(), null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<ResponseWrapper<CTBrain>> getCTBrainById(Long id) {
		Optional<CTBrain> ctBrainOptional = ctBrainRepository.findById(id);
		return ctBrainOptional
				.map(ctBrain -> new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
						RestApiResponseStatus.RETRIEVED.getStatus() + id, ctBrain), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND));
	}

	@Override
	public ResponseEntity<ResponseWrapper<CTBrain>> getCTBrainByAdmitId(Long admitId) {
		Optional<CTBrain> ctBrainOptional = ctBrainRepository.findByAdmitId(admitId);
		return ctBrainOptional
				.map(ctBrain -> new ResponseEntity<>(
						new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
								RestApiResponseStatus.RETRIEVED.getStatus() + ctBrain.getAdmit().getId(), ctBrain),
						HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
						ValidationMessages.INVALID_ADMITID + admitId, null), HttpStatus.NOT_FOUND));
	}

	@Override
	@Transactional
	public ResponseEntity<ResponseWrapper<CTBrain>> updateCTBrain(Long id, CTBrainDTO ctBrainDTO) {
		Optional<CTBrain> ctBrainOptional = ctBrainRepository.findById(id);
		if (ctBrainOptional.isPresent()) {
			CTBrain ctBrain = ctBrainOptional.get();

			Optional<Admit> admitOptional = admitRepository.findById(ctBrainDTO.getAdmitId());
			if (!admitOptional.isPresent()) {
				return new ResponseEntity<>(
						new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
								ValidationMessages.INVALID_ADMITID + ctBrainDTO.getAdmitId(), null),
						HttpStatus.BAD_REQUEST);
			}

			ctBrain.setDate(ctBrainDTO.getDate());
			ctBrain.setResult(ctBrainDTO.getResult());
			ctBrain.setDetails(ctBrainDTO.getDetails());
			ctBrain.getAdmit().setId(ctBrainDTO.getAdmitId());

			ctBrainRepository.save(ctBrain);

			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
					ValidationMessages.UPDATE_SUCCESS, ctBrain), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus() + id, null), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<ResponseWrapper<String>> deleteCTBrain(Long id) {
		Optional<CTBrain> ctBrainOptional = ctBrainRepository.findById(id);
		if (ctBrainOptional.isPresent()) {
			ctBrainRepository.delete(ctBrainOptional.get());
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					ValidationMessages.DELETE_SUCCESS, null), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND);
		}
	}
}
