package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.DrugAllergyDto;
import com.sgic.ppms.entities.DrugAllergy;
import com.sgic.ppms.services.DrugAllergyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.DRUG_ALLERGY)
public class DrugAllergyController {

	private final DrugAllergyService drugAllergyService;

	public DrugAllergyController(DrugAllergyService drugAllergyService) {
		this.drugAllergyService = drugAllergyService;
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseWrapper<List<DrugAllergy>> getAllDrugAllergies() {
		return drugAllergyService.getAllDrugAllergies();
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseWrapper<DrugAllergy> getDrugAllergyById(@PathVariable("id") Long id) {
		return drugAllergyService.getDrugAllergyById(id);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseWrapper<DrugAllergy> createDrugAllergy(@RequestBody DrugAllergyDto drugAllergyDto) {
		return drugAllergyService.createDrugAllergy(drugAllergyDto);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseWrapper<DrugAllergy> updateDrugAllergy(@PathVariable("id") Long id,
			@RequestBody DrugAllergyDto drugAllergyDto) {
		return drugAllergyService.updateDrugAllergy(id, drugAllergyDto);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseWrapper<Void> deleteDrugAllergy(@PathVariable("id") Long id) {
		return drugAllergyService.deleteDrugAllergy(id);
	}
}
