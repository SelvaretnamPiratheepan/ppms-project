package com.sgic.ppms.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.sgic.ppms.dto.UrineTestDto;
import com.sgic.ppms.entities.UrineTest;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.UrineTestService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndpointBundle.URINE_TEST)
public class UrineTestController {
	@Autowired
	private UrineTestService urineTestService;

	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<UrineTestDto>> createUrineTest(
			@RequestBody @Valid UrineTestDto urineTestDto) {
		boolean admitfound = urineTestService.admitExists(urineTestDto.getAdmitId());
		if (!admitfound) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}

		UrineTest createdUrineTest = urineTestService.createUrineTest(urineTestDto);

		UrineTestDto createdUrineTestDTO = new UrineTestDto();
		BeanUtils.copyProperties(createdUrineTest, createdUrineTestDTO);
		createdUrineTestDTO.setAdmitId(createdUrineTest.getAdmit().getId());
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.CREATE_SUCCESS, createdUrineTestDTO));

	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<ResponseWrapper<UrineTest>> getUrineTestById(@PathVariable("id") Long id) {
		UrineTest urineTest = urineTestService.getUrineTestById(id);
		if (urineTest == null) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.NOT_FOUND, null));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.RETRIEVED, urineTest));
	}

	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<List<UrineTest>>> getAllUrineTest() {
		List<UrineTest> urineTest = urineTestService.getAllUrineTest();
		if (urineTest != null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.RETRIEVED, urineTest));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
				ValidationMessages.NO_RECORDS, null));

	}

	@GetMapping(EndpointBundle.GET_BY_ADMIT_ID)
	public ResponseEntity<ResponseWrapper<List<UrineTest>>> getAllUrineTestByAdmitId(
			@PathVariable("admitId") Long admitId) {
		boolean admitfound = urineTestService.admitExists(admitId);
		if (!admitfound) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}

		List<UrineTest> liverFunctions = urineTestService.getAllUrineTestByAdmitId(admitId);
		if (!liverFunctions.isEmpty()) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.RETRIEVED, liverFunctions));
		}
		return ResponseEntity.ok(
				new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.NOT_FOUND, null));
	}

	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<UrineTest>> updateUrineTest(@PathVariable("id") Long id,
			@RequestBody UrineTestDto urineTestDto) {
		boolean admitfound = urineTestService.admitExists(urineTestDto.getAdmitId());
		if (!admitfound) {
			return ResponseEntity.badRequest().body(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ADMITID, null));
		}
		UrineTest urineTestUpdated = urineTestService.updateUrineTest(id, urineTestDto);
		if (urineTestUpdated != null) {

			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.UPDATE_SUCCESS, urineTestUpdated));
		}
		return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
				ValidationMessages.NO_RECORDS, null));

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public ResponseEntity<ResponseWrapper<String>> deleteUrineTest(@PathVariable("id") Long id) {
		UrineTest urineTest = urineTestService.getUrineTestById(id);
		if (urineTest == null) {
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(),
					ValidationMessages.NO_RECORDS, null));
		} else {
			urineTestService.deleteUrineTest(id);
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),
					ValidationMessages.DELETE_SUCCESS, null));
		}
	}

}
