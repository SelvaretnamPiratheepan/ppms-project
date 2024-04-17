package com.sgic.ppms.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sgic.ppms.dto.BloodCultureDto;
import com.sgic.ppms.entities.BloodCultureDetailEntity;
import com.sgic.ppms.enums.RestApiResponseStatus;

@Component
public interface BloodCultureService {

	BloodCultureDetailEntity createBloodCultureDetail(BloodCultureDto bloodCultureDto);

	List<BloodCultureDetailEntity> getAllBloodCultureDetail();

	BloodCultureDetailEntity updateBloodDetail(Long id, BloodCultureDto updatedBloodCulturelDto);

	RestApiResponseStatus deleteBloodCultureDetail(Long id);

	boolean isBloodCultureExists(Long id);

	boolean isBloodCultureExists(String byChildId);

	List<BloodCultureDetailEntity> getbloodCultureByChildId(String childId);

	boolean childExists(String childId);
}
