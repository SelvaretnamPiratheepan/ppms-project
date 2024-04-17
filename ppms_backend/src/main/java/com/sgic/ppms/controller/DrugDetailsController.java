package com.sgic.ppms.controller;

import java.util.Objects;
import java.util.Optional;

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

import com.sgic.ppms.dto.DrugDetailsDto;
import com.sgic.ppms.entities.DrugDetails;
import com.sgic.ppms.services.DrugDetailsService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.DRUG_DETAILS)
public class DrugDetailsController {

	@Autowired
	private DrugDetailsService drugDetailsService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> createDrugDetails(@RequestBody DrugDetailsDto drugDetailsDto) {
		try {

			if (Objects.isNull(drugDetailsDto.getAdmit())) {
				throw new RuntimeException("Admit is required");
			}

			if (Objects.isNull(drugDetailsDto.getAdmit().getId())) {
				throw new RuntimeException("Admit ID is required");
			}

			if (Objects.isNull(drugDetailsDto.getDrug())) {
				throw new RuntimeException("Drug is required");
			}

			if (Objects.isNull(drugDetailsDto.getDrug().getId())) {
				throw new RuntimeException("Drug ID is required");
			}

			if (Objects.isNull(drugDetailsDto.getDose())) {
				throw new RuntimeException("Dose is required");
			}

			if (Objects.isNull(drugDetailsDto.getDose().getId())) {
				throw new RuntimeException("Dose ID is required");
			}

			if (Objects.isNull(drugDetailsDto.getRoute())) {
				throw new RuntimeException("Route is required");
			}

			if (Objects.isNull(drugDetailsDto.getRoute().getId())) {
				throw new RuntimeException("Route ID is required");
			}

			if (Objects.isNull(drugDetailsDto.getFrequency())) {
				throw new RuntimeException("Frequency is required");
			}

			if (Objects.isNull(drugDetailsDto.getFrequency().getId())) {
				throw new RuntimeException("Frequency ID is required");
			}

			if (Objects.isNull(drugDetailsDto.getStartDate())) {
				throw new RuntimeException("Start date is required");
			}

			if (Objects.isNull(drugDetailsDto.getEndDate())) {
				throw new RuntimeException("End date is required");
			}

			Boolean drugAtDischarge = drugDetailsDto.getDrugAtDischarge();
			if (Objects.isNull(drugAtDischarge)) {
				throw new RuntimeException("Drug at discharge is required");
			}

			if (Boolean.TRUE.equals(drugAtDischarge) && Objects.isNull(drugDetailsDto.getNumberOfDays())) {
				throw new RuntimeException("Number of days is required when drugAtDischarge is true");
			}

			DrugDetails drugDetails = drugDetailsService.CreateDrugDetails(drugDetailsDto);
			ResponseWrapper<DrugDetails> responseWrapper = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					ValidationMessages.CREATE_SUCCESS, drugDetails);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
		}

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<?> getDrugDetailsById(@PathVariable("admitId") int admit_id) {
		try {
			Optional<DrugDetails> drugDetailsOptional = drugDetailsService.getDrugDetailsById(admit_id);
			if (drugDetailsOptional.isPresent()) {
				DrugDetails drugDetails = drugDetailsOptional.get();
				return ResponseEntity.status(HttpStatus.OK).body(
						new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.GET_SUCCESS, drugDetails));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<?>> updateDrugDetailsByAdmitId(@PathVariable("admitId") int admit_id,
			@RequestBody DrugDetailsDto drugDetailsDto) {
		try {
			DrugDetails updatedDrugDetails = drugDetailsService.updateDrugDetails(admit_id, drugDetailsDto);
			if (updatedDrugDetails != null) {
				ResponseWrapper<DrugDetails> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
						ValidationMessages.UPDATE_SUCCESS, updatedDrugDetails);
				return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
		}
	}

}
