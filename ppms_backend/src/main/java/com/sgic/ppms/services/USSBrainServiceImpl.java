package com.sgic.ppms.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.USSBrainDTO;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.USSBrain;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.USSBrainRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@Service
public class USSBrainServiceImpl implements USSBrainService {
    @Autowired
    private USSBrainRepository ussBrainRepository;

    @Autowired
    private AdmitRepository admitRepository;

    @Transactional
    public ResponseEntity<ResponseWrapper<USSBrain>> saveUSSBrain(USSBrainDTO ussBrainDTO) {

        Optional<Admit> admitOptional = admitRepository.findById(ussBrainDTO.getAdmitId());
        if (admitOptional.isPresent()) {
            USSBrain ussBrain = new USSBrain();
            ussBrain.setAdmit(admitOptional.get());
            ussBrain.setDate(ussBrainDTO.getDate());
            ussBrain.setResult(ussBrainDTO.getResult());
            ussBrain.setDetails(ussBrainDTO.getDetails());
            ussBrainRepository.save(ussBrain);
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
                    ValidationMessages.CREATE_SUCCESS, ussBrain), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
                    ValidationMessages.INVALID_ADMITID + ussBrainDTO.getAdmitId(), null), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<ResponseWrapper<USSBrain>> getUSSBrainById(Long id) {
        Optional<USSBrain> ussBrainOptional = ussBrainRepository.findById(id);
        return ussBrainOptional.map(ussBrain ->
                new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
                		RestApiResponseStatus.RETRIEVED.getStatus() + id, ussBrain), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
                        ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<ResponseWrapper<USSBrain>> getUSSBrainByAdmitId(Long admitId) {
        Optional<USSBrain> ussBrainOptional = ussBrainRepository.findByAdmitId(admitId);
        return ussBrainOptional.map(ussBrain ->
                new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
                		RestApiResponseStatus.RETRIEVED.getStatus() + ussBrain.getAdmit().getId(), ussBrain), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
                        ValidationMessages.INVALID_ADMITID + admitId, null), HttpStatus.NOT_FOUND));
    }


    @Transactional
    public ResponseEntity<ResponseWrapper<USSBrain>> updateUSSBrain(Long id, USSBrainDTO ussBrainDTO) {
        Optional<USSBrain> ussBrainOptional = ussBrainRepository.findById(id);
        if (ussBrainOptional.isPresent()) {
            USSBrain ussBrain = ussBrainOptional.get();


            Optional<Admit> admitOptional = admitRepository.findById(ussBrainDTO.getAdmitId());
            if (!admitOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
                        ValidationMessages.INVALID_ADMITID + ussBrainDTO.getAdmitId(), null), HttpStatus.BAD_REQUEST);
            }

            ussBrain.setDate(ussBrainDTO.getDate());
            ussBrain.setResult(ussBrainDTO.getResult());
            ussBrain.setDetails(ussBrainDTO.getDetails());

            
            Admit admit = admitOptional.get();
            ussBrain.setAdmit(admit);

            ussBrainRepository.save(ussBrain);

            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
                    ValidationMessages.UPDATE_SUCCESS, ussBrain), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
            		RestApiResponseStatus.NOT_FOUND.getStatus()+ id, null), HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<ResponseWrapper<String>> deleteUSSBrain(Long id) {
        Optional<USSBrain> ussBrainOptional = ussBrainRepository.findById(id);
        if (ussBrainOptional.isPresent()) {
            ussBrainRepository.delete(ussBrainOptional.get());
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
                    ValidationMessages.DELETE_SUCCESS, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
                    ValidationMessages.INVALID_ID+ id, null), HttpStatus.NOT_FOUND);
        }
    }
}
