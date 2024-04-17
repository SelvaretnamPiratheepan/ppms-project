package com.sgic.ppms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.DoseDto;
import com.sgic.ppms.entities.Dose;
import com.sgic.ppms.services.DoseService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.DOSE)
public class DoseController {

	@Autowired
	private DoseService doseService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<Dose>>> getAllDoses() {
		List<Dose> doses = doseService.getAllDoses();
		ResponseWrapper<List<Dose>> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
				ValidationMessages.GET_SUCCESS, doses);
		return ResponseEntity.ok(responseWrapper);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<Dose>> CreateDose(@RequestBody DoseDto doseDto) {
		String doseName = doseDto.getName();
		int existingDoseId = doseService.getDoseIdByName(doseName);
		if (existingDoseId != -1) {
			Dose existingDose = doseService.getDoseById(existingDoseId).orElse(null);
			ResponseWrapper<Dose> responseWrapper = new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(),
					ValidationMessages.NAME_EXISTS, existingDose);
			return ResponseEntity.badRequest().body(responseWrapper);
		} else {
			Dose dose = doseService.CreateDose(doseDto);
			ResponseWrapper<Dose> responseWrapper = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					ValidationMessages.CREATE_SUCCESS, dose);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteDrug(@PathVariable("id") int id) {
		boolean deleteStatus = doseService.deleteDoseById(id);
		if (deleteStatus) {
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.DELETE_SUCCESS, null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<Dose>> getDoseById(@PathVariable("id") long id) {
		Optional<Dose> doseOptional = doseService.getDoseById(id);
		if (doseOptional.isPresent()) {
			Dose dose = doseOptional.get();
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.GET_SUCCESS, dose));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

}
