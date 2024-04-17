package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.EnvironmentalAllergyDTO;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.EnvironmentalAllergyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.ENVIRONMENTAL_ALLERGY)
public class EnvironmentalAllergyController {

	@Autowired
	private EnvironmentalAllergyService environmentalAllergyService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseWrapper<EnvironmentalAllergyDTO> insertEnvironmentalAllergyDetail(
			@RequestBody EnvironmentalAllergyDTO environmentalAllergyDTO) {
		return environmentalAllergyService.insertEnvironmentalAllergyDetail(environmentalAllergyDTO);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public Object getEnvironmentalAllergy(@PathVariable("id") Long id) {
		return environmentalAllergyService.getEnvironmentalAllergy(id);
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseWrapper<List<EnvironmentalAllergyDTO>> getAllEnvironmentalAllergies() {
		return environmentalAllergyService.getAllEnvironmentalAllergies();
	}

	@GetMapping(EndpointBundle.GET_BY_NAME)
	public ResponseWrapper<Long> getEnvironmentalAllergyId(@PathVariable("name") String name) {
		ResponseWrapper<Long> response = environmentalAllergyService.getEnvironmentalAllergyIdByName(name);
		if (response.getStatusCode() == RestApiResponseStatus.NOT_FOUND.getCode()) {
			return response;
		} else {
			return response;
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseWrapper<EnvironmentalAllergyDTO> updateEnvironmentalAllergyDetail(@PathVariable("id") Long id,
			@RequestBody EnvironmentalAllergyDTO environmentalAllergyDTO) {
		return environmentalAllergyService.updateEnvironmentalAllergyDetail(id, environmentalAllergyDTO);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseWrapper<EnvironmentalAllergyDTO> deleteEnvironmentalAllergyDetail(@PathVariable("id") Long id) {
		return environmentalAllergyService.deleteEnvironmentalAllergyDetail(id);
	}
}
