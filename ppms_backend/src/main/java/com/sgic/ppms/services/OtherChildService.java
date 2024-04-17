package com.sgic.ppms.services;

import org.springframework.http.ResponseEntity;

import com.sgic.ppms.dto.OtherChildDTO;
import com.sgic.ppms.entities.OtherChild;
import com.sgic.ppms.util.ResponseWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OtherChildService {
    @Transactional
    ResponseEntity<ResponseWrapper<OtherChild>> createOtherChild(OtherChildDTO otherChildDTO);

    @Transactional
    ResponseEntity<ResponseWrapper<OtherChild>> updateOtherChild(Long id, OtherChildDTO otherChildDTO);

    ResponseEntity<ResponseWrapper<OtherChild>> getOtherChildById(Long id);


    ResponseEntity<ResponseWrapper<String>> deleteOtherChildById(Long id);

}
