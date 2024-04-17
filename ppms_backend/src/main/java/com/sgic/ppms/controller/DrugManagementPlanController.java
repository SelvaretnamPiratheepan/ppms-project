package com.sgic.ppms.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.DrugManagementPlanDTO;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.DrugManagementPlanService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.DRUG_MANAGEMENT_PLAN)
public class DrugManagementPlanController {

	@Autowired
	private DrugManagementPlanService drugManagementPlanService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<DrugManagementPlanDTO>> createDrugManagementPlan(
			@RequestBody @Valid DrugManagementPlanDTO drugManagementPlanDTO) {
		DrugManagementPlanDTO createdPlan = drugManagementPlanService.createDrugManagementPlan(drugManagementPlanDTO);
		ResponseWrapper<DrugManagementPlanDTO> Response = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				RestApiResponseStatus.CREATED.getStatus(), createdPlan);
		return ResponseEntity.status(HttpStatus.CREATED).body(Response);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<DrugManagementPlanDTO>> updateDrugManagementPlan(@PathVariable("id") long id,
			@RequestBody @Valid DrugManagementPlanDTO drugManagementPlanDTO) {
		Long admitId = drugManagementPlanDTO.getAdmitId();
		boolean admitFound = drugManagementPlanService.admitFoundInDrugManDatabase(admitId);
		Long admitId2 = drugManagementPlanService.getAdmitIdbyId(id);
		if (admitFound && !Objects.equals(admitId, admitId2)) {
			ResponseWrapper<DrugManagementPlanDTO> Response = new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.ADMITID_EXISTS, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response);
		}
		DrugManagementPlanDTO updatedPlan = drugManagementPlanService.updateDrugManagementPlan(id,
				drugManagementPlanDTO);
		ResponseWrapper<DrugManagementPlanDTO> Response = new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				RestApiResponseStatus.UPDATED.getStatus(), updatedPlan);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response);
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<DrugManagementPlanDTO>> getDrugManagementPlanByAdmitId(
			@PathVariable("admitId") Long admitId) {
		DrugManagementPlanDTO plan = drugManagementPlanService.getDrugManagementPlanByAdmitid(admitId);
		ResponseWrapper<DrugManagementPlanDTO> Response = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
				RestApiResponseStatus.OK.getStatus(), plan);
		return ResponseEntity.status(HttpStatus.FOUND).body(Response);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<DrugManagementPlanDTO>> getDrugManagementPlanById(
			@PathVariable("id") Long Id) {
		DrugManagementPlanDTO plan = drugManagementPlanService.getDrugManagementPlanById(Id);
		ResponseWrapper<DrugManagementPlanDTO> Response = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(),
				RestApiResponseStatus.OK.getStatus(), plan);
		return ResponseEntity.status(HttpStatus.FOUND).body(Response);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<DrugManagementPlanDTO>> deleteDrugPlanById(@PathVariable("id") Long id) {
		boolean drugManPlanExists = drugManagementPlanService.drugManPlanExists(id);
		if (!drugManPlanExists) {
			ResponseWrapper<DrugManagementPlanDTO> Response = new ResponseWrapper<>(
					RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.INVALID_DRUG_MANAGEMENT_ID, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response);
		}
		ResponseWrapper<DrugManagementPlanDTO> Response = new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
				RestApiResponseStatus.DELETED.getStatus(), null);
		return ResponseEntity.status(HttpStatus.OK).body(Response);
	}
}
