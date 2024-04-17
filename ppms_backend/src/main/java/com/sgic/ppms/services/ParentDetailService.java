package com.sgic.ppms.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.ParentDetailDto;
import com.sgic.ppms.entities.ParentDetailEntity;
import com.sgic.ppms.enums.RestApiResponseStatus;

@Service
public interface ParentDetailService {

	ParentDetailEntity createParentDetail(ParentDetailDto parentDetailDto);

	List<ParentDetailEntity> getAllParentDetails();

	ParentDetailEntity getParentDetailById(Long id);

	ParentDetailEntity updateParentDetail(Long id, ParentDetailDto updatedParentDetailDto);

	RestApiResponseStatus deleteParentDetail(Long id);

	List<ParentDetailEntity> getparentDetailByChildId(String childId);

	boolean idFound(Long id);

	boolean childExists(String childId);

	boolean isParentDetailExists(Long id);

	boolean isParentDetailExists(String childId);
}
