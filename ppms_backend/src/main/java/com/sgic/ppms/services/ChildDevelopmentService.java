package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.ChildDevelopmentDTO;
import com.sgic.ppms.util.ResponseWrapper;

public interface ChildDevelopmentService {
	ResponseWrapper<ChildDevelopmentDTO> createChildDevelopment(ChildDevelopmentDTO childDevelopmentDTO);

	ResponseWrapper<ChildDevelopmentDTO> updateChildDevelopmentByChildId(String childId,
			ChildDevelopmentDTO childDevelopmentDTO);

	ResponseWrapper<ChildDevelopmentDTO> updateChildDevelopmentById(Long id, ChildDevelopmentDTO childDevelopmentDTO);

	ResponseWrapper<ChildDevelopmentDTO> deleteChildDevelopmentByChildId(String childId);

	ResponseWrapper<ChildDevelopmentDTO> deleteChildDevelopmentById(Long id);

	ResponseWrapper<List<ChildDevelopmentDTO>> getAllChildDevelopments();
}
