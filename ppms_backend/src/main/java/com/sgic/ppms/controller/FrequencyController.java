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

import com.sgic.ppms.dto.FrequencyDto;
import com.sgic.ppms.entities.Frequency;
import com.sgic.ppms.services.FrequencyService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

@RestController
@RequestMapping(EndpointBundle.FREQUENCY)
public class FrequencyController {

	@Autowired
	private FrequencyService frequencyService;

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<Frequency>>> getAllFrequencies() {
		List<Frequency> frequencies = frequencyService.getAllFrequencies();
		ResponseWrapper<List<Frequency>> responseWrapper = new ResponseWrapper<>(HttpStatus.OK.value(),
				ValidationMessages.GET_SUCCESS, frequencies);
		return ResponseEntity.ok(responseWrapper);
	}

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<Frequency>> CreateFrequency(@RequestBody FrequencyDto frequencyDto) {
		String frequencyName = frequencyDto.getName();
		int existingFrequencyId = frequencyService.getFrequencyIdByName(frequencyName);
		if (existingFrequencyId != -1) {
			Frequency existingFrequency = frequencyService.getFrequencyById(existingFrequencyId).orElse(null);
			ResponseWrapper<Frequency> responseWrapper = new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(),
					ValidationMessages.NAME_EXISTS, existingFrequency);
			return ResponseEntity.badRequest().body(responseWrapper);
		} else {
			Frequency frequency = frequencyService.CreateFrequency(frequencyDto);
			ResponseWrapper<Frequency> responseWrapper = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					ValidationMessages.RETRIEVED, frequency);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
		}
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteFrequency(@PathVariable("id") int id) {
		boolean deleteStatus = frequencyService.deleteFrequencyById(id);
		if (deleteStatus) {
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.DELETE_SUCCESS, null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<Frequency>> getFrequencyById(@PathVariable("id") long id) {
		Optional<Frequency> frequencyOptional = frequencyService.getFrequencyById(id);
		if (frequencyOptional.isPresent()) {
			Frequency frequency = frequencyOptional.get();
			return ResponseEntity
					.ok(new ResponseWrapper<>(HttpStatus.OK.value(), ValidationMessages.GET_SUCCESS, frequency));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null));
		}
	}

}