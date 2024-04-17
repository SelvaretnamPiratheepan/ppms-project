
package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.GeneralConditionDto;
import com.sgic.ppms.entities.GeneralCondition;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.GeneralConditionService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.GENERAL_CONDITION)
public class GeneralConditionController {
	@Autowired
	private GeneralConditionService generalConditionService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<GeneralCondition>>> getAllGeneralConditions() {
		List<GeneralCondition> generalConditions = generalConditionService.getAllGeneralConditions();
		return new ResponseEntity<>(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
				ValidationMessages.GET_SUCCESS, generalConditions), HttpStatus.OK);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<GeneralConditionDto>> createGeneralCondition(
			@RequestBody GeneralConditionDto generalConditionDto) {
		if (generalConditionDto.getAdmitId() == null) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.WRONG_JSON, generalConditionDto));
		}

		if (generalConditionDto.getGeneral_Condition() != null
				&& !generalConditionDto.getGeneral_Condition().matches("^[A-Za-z]+$")) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.MISMATCH_INPUT, generalConditionDto));
		}

		GeneralConditionDto response = generalConditionService.createGeneralConditionFromDto(generalConditionDto);

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.CREATE_SUCCESS, response));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<GeneralConditionDto>> updateGeneralCondition(@PathVariable("id") Integer id,
			@RequestBody GeneralConditionDto generalConditionDto) {
		if (generalConditionDto.getAdmitId() == null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.WRONG_JSON, generalConditionDto));
		}

		if (generalConditionDto.getGeneral_Condition() != null
				&& !generalConditionDto.getGeneral_Condition().matches("^[A-Za-z]+$")) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.MISMATCH_INPUT, generalConditionDto));
		}

		GeneralConditionDto updatedGeneralConditionDto = generalConditionService.updateGeneralCondition(id,
				generalConditionDto);

		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(),
				ValidationMessages.UPDATE_SUCCESS, updatedGeneralConditionDto));

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<?> getGeneralConditionByAdmitId(@PathVariable("admitId") Long admitId) {

		GeneralConditionDto generalconditiondto = generalConditionService.getGeneralConditionByAdmitId(admitId);
		if (generalconditiondto != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(),
					ValidationMessages.GET_SUCCESS, generalconditiondto));
		} else {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(),
					ValidationMessages.INVALID_ADMITID, admitId));
		}
	}

}
