package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.ICDCategoryDTO;
import com.sgic.ppms.entities.ICDCategory;
import com.sgic.ppms.services.ICDCategoryService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.ICD_CATEGORY)
public class ICDCategoryController {
	private final ICDCategoryService icdCategoryService;

	public ICDCategoryController(ICDCategoryService icdCategoryService) {
		this.icdCategoryService = icdCategoryService;
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<ICDCategory>> createICDCategory(@RequestBody ICDCategoryDTO icdCategoryDTO) {
		return icdCategoryService.createICDCategory(icdCategoryDTO);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<ICDCategoryDTO>> getICDCategoryById(@PathVariable("id") Long id) {
		return icdCategoryService.getICDCategoryById(id);
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<ICDCategoryDTO>>> getAllICDCategories() {
		return icdCategoryService.getAllICDCategories();
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<ICDCategory>> updateICDCategory(@PathVariable("id") Long id,
			@RequestBody ICDCategoryDTO icdCategoryDTO) {
		return icdCategoryService.updateICDCategory(id, icdCategoryDTO);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteICDCategory(@PathVariable("id") Long id) {
		return icdCategoryService.deleteICDCategory(id);
	}

}
