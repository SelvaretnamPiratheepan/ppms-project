package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgic.ppms.dto.CXRDto;
import com.sgic.ppms.entities.CXR;
import com.sgic.ppms.services.CXRService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;

@RestController
@RequestMapping(EndpointBundle.CXR)
public class CXRController {

	@Autowired
	private CXRService cxrService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseWrapper<CXR> create(@Validated @RequestBody CXRDto cxrDto) {
		return cxrService.create(cxrDto);
	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseWrapper<List<CXR>> getByadmitId(@PathVariable("admitId") Long admitId) {
		return cxrService.getByAdmitId(admitId);
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseWrapper<CXR> getById(@PathVariable("id") Integer id) {
		return cxrService.getById(id);
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseWrapper<CXR> update(@PathVariable("id") Integer id, @Validated @RequestBody CXRDto cxrDto) {
		Boolean result = true;
		return cxrService.update(id, result, cxrDto);
	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseWrapper<CXR> delete(@PathVariable("id") Integer id) {
		return cxrService.delete(id);
	}
}
