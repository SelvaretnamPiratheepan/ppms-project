package com.sgic.ppms.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sgic.ppms.dto.ICDCategoryDTO;
import com.sgic.ppms.entities.ICDCategory;
import com.sgic.ppms.util.ResponseWrapper;

public interface ICDCategoryService {
	ResponseEntity<ResponseWrapper<ICDCategory>> createICDCategory(ICDCategoryDTO icdCategoryDTO);

	ResponseEntity<ResponseWrapper<List<ICDCategoryDTO>>> getAllICDCategories();

	ResponseEntity<ResponseWrapper<ICDCategoryDTO>> getICDCategoryById(Long id);

	ResponseEntity<ResponseWrapper<ICDCategory>> updateICDCategory(Long id, ICDCategoryDTO icdCategoryDTO);

	ResponseEntity<ResponseWrapper<String>> deleteICDCategory(Long id);
}
