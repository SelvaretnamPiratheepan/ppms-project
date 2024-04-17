package com.sgic.ppms.services;

import com.sgic.ppms.dto.OtherChildDTO;
import com.sgic.ppms.entities.Family_And_Social_History;
import com.sgic.ppms.entities.OtherChild;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.FamHisRepository;
import com.sgic.ppms.repositories.OtherChildRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OtherChildServiceImpl implements OtherChildService {

    @Autowired
    private OtherChildRepository otherChildRepository;

    @Autowired
    private FamHisRepository famHisRepository;


    @Transactional
    public ResponseEntity<ResponseWrapper<OtherChild>> createOtherChild(OtherChildDTO otherChildDTO) {
        Optional<Family_And_Social_History> familyAndSocialHistoryOptional =
                famHisRepository.findById(otherChildDTO.getFamilyAndSocialHistoryId());
        if (familyAndSocialHistoryOptional.isPresent()) {
            OtherChild otherChild = new OtherChild();
            otherChild.setFamilyAndSocialHistory(familyAndSocialHistoryOptional.get());
            otherChild.setChildAge(otherChildDTO.getChildAge());
            otherChild.setOtherChildAge(otherChildDTO.getOtherChildAge());
            otherChild.setSchool(otherChildDTO.getSchool());
            otherChild.setIllness(otherChildDTO.getIllness());
            otherChild.setIf_yes(otherChildDTO.getIf_yes());

            OtherChild savedOtherChild = otherChildRepository.save(otherChild);
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
                    ValidationMessages.CREATE_SUCCESS, savedOtherChild), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
                    ValidationMessages.INVALID_FAMILY_SOCIAL_HISTORY_ID + otherChildDTO.getFamilyAndSocialHistoryId(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseWrapper<OtherChild>> updateOtherChild(Long id, OtherChildDTO otherChildDTO) {
        Optional<OtherChild> otherChildOptional = otherChildRepository.findById(id);
        if (otherChildOptional.isPresent()) {
            OtherChild otherChild = otherChildOptional.get();

            // Update the fields with the new values
            otherChild.setChildAge(otherChildDTO.getChildAge());
            otherChild.setOtherChildAge(otherChildDTO.getOtherChildAge());
            otherChild.setSchool(otherChildDTO.getSchool());
            otherChild.setIllness(otherChildDTO.getIllness());
            otherChild.setIf_yes(otherChildDTO.getIf_yes());

            // Retrieve the associated Family_And_Social_History
            Optional<Family_And_Social_History> familyAndSocialHistoryOptional = famHisRepository.findById(otherChildDTO.getFamilyAndSocialHistoryId());
            if (familyAndSocialHistoryOptional.isPresent()) {
                // Update the Family_And_Social_History entity
                Family_And_Social_History familyAndSocialHistory = familyAndSocialHistoryOptional.get();
                // Update Family_And_Social_History fields with the new values
                // You can update relevant fields here

                // Set the updated Family_And_Social_History to the OtherChild entity
                otherChild.setFamilyAndSocialHistory(familyAndSocialHistory);
            } else {
                return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
                        ValidationMessages.INVALID_FAMILY_SOCIAL_HISTORY_ID + otherChildDTO.getFamilyAndSocialHistoryId(), null), HttpStatus.NOT_FOUND);
            }

            // Save the updated OtherChild entity
            OtherChild updatedOtherChild = otherChildRepository.save(otherChild);
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
                    ValidationMessages.UPDATE_SUCCESS, updatedOtherChild), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
                    ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND);
        }
    }



    public ResponseEntity<ResponseWrapper<OtherChild>> getOtherChildById(Long id) {
        Optional<OtherChild> otherChildOptional = otherChildRepository.findById(id);
        return otherChildOptional.map(otherChild ->
                        new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
                                RestApiResponseStatus.RETRIEVED.getStatus() + id, otherChild), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
                        ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ResponseWrapper<String>> deleteOtherChildById(Long id) {
        Optional<OtherChild> otherChildOptional = otherChildRepository.findById(id);
        if (otherChildOptional.isPresent()) {
            otherChildRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
                    ValidationMessages.DELETE_SUCCESS, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
                    ValidationMessages.INVALID_ID + id, null), HttpStatus.NOT_FOUND);
        }
    }

}
