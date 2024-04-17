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

import com.sgic.ppms.entities.Family_And_Social_History;
import com.sgic.ppms.services.FamHisService;
import com.sgic.ppms.util.EndpointBundle;

@RestController
@RequestMapping(EndpointBundle.FAMILY_HISTORY)
public class FamHisController {
	@Autowired
	private FamHisService famHisService;

	@PostMapping(EndpointBundle.CREATE)
	public Family_And_Social_History createFamilyHistory(@RequestBody Family_And_Social_History familyHistory) {
		return famHisService.createFamilyHistory(familyHistory);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public Family_And_Social_History getFamilyHistoryById(@PathVariable("id") Integer id) {
		return famHisService.getFamilyHistoryById(id);
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public List<Family_And_Social_History> getAllFamilyHistories() {
		return famHisService.getAllFamilyHistories();
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public void deleteFamilyHistory(@PathVariable("id") Integer id) {
		famHisService.deleteFamilyHistory(id);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public Family_And_Social_History updateFamilyHistory(@PathVariable("id") Integer id,
			@RequestBody Family_And_Social_History familyHistory) {
		return famHisService.updateFamilyHistory(id, familyHistory);
	}
}
