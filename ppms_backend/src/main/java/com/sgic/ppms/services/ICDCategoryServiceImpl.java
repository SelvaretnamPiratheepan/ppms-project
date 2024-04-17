package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.ICDCategoryDTO;
import com.sgic.ppms.entities.ICDCategory;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.ICDCategoryRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@Service
public class ICDCategoryServiceImpl implements ICDCategoryService {

	private final ICDCategoryRepository icdCategoryRepository;

	public ICDCategoryServiceImpl(ICDCategoryRepository icdCategoryRepository) {
		this.icdCategoryRepository = icdCategoryRepository;
	}

	public ResponseEntity<ResponseWrapper<ICDCategory>> createICDCategory(ICDCategoryDTO icdCategoryDTO) {
		String categoryName = icdCategoryDTO.getName();
		if (icdCategoryRepository.existsByName(categoryName)) {
			ICDCategory existingCategory = icdCategoryRepository.findByName(categoryName);

			ResponseWrapper<ICDCategory> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.VALIDATION_FAILURE.getCode(), ValidationMessages.NAME_EXISTS,
					existingCategory);

			return ResponseEntity.badRequest().body(responseWrapper);
		} else {
			ICDCategory icdCategory = new ICDCategory();
			icdCategory.setName(categoryName);
			icdCategoryRepository.save(icdCategory);

			ResponseWrapper<ICDCategory> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS, icdCategory);

			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		}
	}

	public ResponseEntity<ResponseWrapper<List<ICDCategoryDTO>>> getAllICDCategories() {
		List<ICDCategory> icdCategories = icdCategoryRepository.findAll();
		List<ICDCategoryDTO> icdCategoryDTOs = icdCategories.stream().map(icdCategory -> {
			ICDCategoryDTO dto = new ICDCategoryDTO();
			dto.setName(icdCategory.getName());
			return dto;
		}).collect(Collectors.toList());

		ResponseWrapper<List<ICDCategoryDTO>> responseWrapper = new ResponseWrapper<>(
				RestApiResponseStatus.CREATED.getCode(), ValidationMessages.RETRIEVED, icdCategoryDTOs);

		return ResponseEntity.ok(responseWrapper);
	}

	public ResponseEntity<ResponseWrapper<ICDCategoryDTO>> getICDCategoryById(Long id) {
		Optional<ICDCategory> icdCategoryOptional = icdCategoryRepository.findById(id);
		if (icdCategoryOptional.isPresent()) {
			ICDCategory icdCategory = icdCategoryOptional.get();
			ICDCategoryDTO icdCategoryDTO = new ICDCategoryDTO();
			icdCategoryDTO.setName(icdCategory.getName());

			ResponseWrapper<ICDCategoryDTO> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.CREATED.getCode(), ValidationMessages.RETRIEVED, icdCategoryDTO);

			return ResponseEntity.ok(responseWrapper);
		} else {
			ResponseWrapper<ICDCategoryDTO> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), RestApiResponseStatus.NOT_FOUND.getStatus(), null);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}

	@Transactional
	public ResponseEntity<ResponseWrapper<ICDCategory>> updateICDCategory(Long id, ICDCategoryDTO icdCategoryDTO) {
		Optional<ICDCategory> existingCategoryOptional = icdCategoryRepository.findById(id);
		if (existingCategoryOptional.isPresent()) {
			ICDCategory existingCategory = existingCategoryOptional.get();
			existingCategory.setName(icdCategoryDTO.getName());
			icdCategoryRepository.save(existingCategory);

			ResponseWrapper<ICDCategory> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, existingCategory);

			return ResponseEntity.ok(responseWrapper);
		} else {
			ResponseWrapper<ICDCategory> responseWrapper = new ResponseWrapper<>(
					RestApiResponseStatus.NOT_FOUND.getCode(), RestApiResponseStatus.NOT_FOUND.getStatus(), null);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}

	@Transactional
	public ResponseEntity<ResponseWrapper<String>> deleteICDCategory(Long id) {
		Optional<ICDCategory> icdCategoryOptional = icdCategoryRepository.findById(id);
		if (icdCategoryOptional.isPresent()) {
			ICDCategory deletedCategory = icdCategoryOptional.get();
			String categoryName = deletedCategory.getName();

			icdCategoryRepository.deleteById(id);

			ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					ValidationMessages.DELETE_SUCCESS, "Deleted category: " + categoryName);

			return ResponseEntity.ok(responseWrapper);
		} else {
			ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					RestApiResponseStatus.NOT_FOUND.getStatus(), null);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
		}
	}
}
