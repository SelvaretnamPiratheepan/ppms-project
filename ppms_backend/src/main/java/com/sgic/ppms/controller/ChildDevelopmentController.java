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

import com.sgic.ppms.dto.ChildDevelopmentDTO;
import com.sgic.ppms.services.ChildDevelopmentService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.CHILD_DEVELOPMENT)
public class ChildDevelopmentController {
	private final ChildDevelopmentService childDevelopmentService;

	public ChildDevelopmentController(ChildDevelopmentService childDevelopmentService) {
		this.childDevelopmentService = childDevelopmentService;
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseWrapper<ChildDevelopmentDTO> createChildDevelopment(
			@RequestBody ChildDevelopmentDTO childDevelopmentDTO) {
		return childDevelopmentService.createChildDevelopment(childDevelopmentDTO);
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<ChildDevelopmentDTO>>> getAllChildDevelopments() {
		ResponseWrapper<List<ChildDevelopmentDTO>> childDevelopmentDTOs = childDevelopmentService
				.getAllChildDevelopments();
		return ResponseEntity.ok(childDevelopmentDTOs);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_CHILD_ID)
	public ResponseWrapper<ChildDevelopmentDTO> updateChildDevelopmentByChildId(@PathVariable("childId") String childId,
			@RequestBody ChildDevelopmentDTO childDevelopmentDTO) {
		return childDevelopmentService.updateChildDevelopmentByChildId(childId, childDevelopmentDTO);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseWrapper<ChildDevelopmentDTO> updateChildDevelopmentById(@PathVariable("id") Long id,
			@RequestBody ChildDevelopmentDTO childDevelopmentDTO) {
		return childDevelopmentService.updateChildDevelopmentById(id, childDevelopmentDTO);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_CHILD_ID)
	public ResponseWrapper<ChildDevelopmentDTO> deleteChildDevelopmentByChildId(
			@PathVariable("childId") String childId) {
		return childDevelopmentService.deleteChildDevelopmentByChildId(childId);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseWrapper<ChildDevelopmentDTO> deleteChildDevelopmentById(@PathVariable("id") Long id) {
		return childDevelopmentService.deleteChildDevelopmentById(id);
	}

}