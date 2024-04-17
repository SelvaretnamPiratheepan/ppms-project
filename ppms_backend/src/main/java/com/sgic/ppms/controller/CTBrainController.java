package com.sgic.ppms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.CTBrainDTO;
import com.sgic.ppms.entities.CTBrain;
import com.sgic.ppms.services.CTBrainService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.CT_BRAIN)
public class CTBrainController {

	@Autowired
	private CTBrainService ctBrainService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<CTBrain>> saveCTBrain(@RequestBody CTBrainDTO ctBrainDTO) {
		return ctBrainService.saveCTBrain(ctBrainDTO);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<CTBrain>> getCTBrainById(@PathVariable Long id) {
		return ctBrainService.getCTBrainById(id);
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<CTBrain>> getCTBrainByAdmitId(@PathVariable Long admitId) {
		return ctBrainService.getCTBrainByAdmitId(admitId);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<CTBrain>> updateCTBrain(@PathVariable Long id,
			@RequestBody CTBrainDTO ctBrainDTO) {
		return ctBrainService.updateCTBrain(id, ctBrainDTO);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteCTBrain(@PathVariable Long id) {
		return ctBrainService.deleteCTBrain(id);
	}
}
