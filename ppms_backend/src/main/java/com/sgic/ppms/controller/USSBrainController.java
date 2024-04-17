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

import com.sgic.ppms.dto.USSBrainDTO;
import com.sgic.ppms.entities.USSBrain;
import com.sgic.ppms.services.USSBrainService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.USSBRAIN)
public class USSBrainController {

	@Autowired
	private USSBrainService ussBrainService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<USSBrain>> saveUSSBrain(@RequestBody USSBrainDTO ussBrainDTO) {
		return ussBrainService.saveUSSBrain(ussBrainDTO);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<USSBrain>> getUSSBrainById(@PathVariable Long id) {
		return ussBrainService.getUSSBrainById(id);
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<USSBrain>> getUSSBrainByAdmitId(@PathVariable Long admitId) {
		return ussBrainService.getUSSBrainByAdmitId(admitId);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<USSBrain>> updateUSSBrain(@PathVariable Long id,
			@RequestBody USSBrainDTO ussBrainDTO) {
		return ussBrainService.updateUSSBrain(id, ussBrainDTO);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteUSSBrain(@PathVariable Long id) {
		return ussBrainService.deleteUSSBrain(id);
	}
}
