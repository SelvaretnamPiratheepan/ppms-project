package com.sgic.ppms.controller;

import java.util.List;

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

import com.sgic.ppms.dto.ParentDetailDto;
import com.sgic.ppms.entities.ParentDetailEntity;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.services.ParentDetailService;
import com.sgic.ppms.util.EndpointBundle;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndpointBundle.PARENT_DETAIL)
@RequiredArgsConstructor
public class ParentDetailController {

	@Autowired
	ParentDetailService parentDetailService;


	@PostMapping(EndpointBundle.CREATE)
	public ResponseEntity<ResponseWrapper<?>> createParentDetail(@RequestBody ParentDetailDto parentDetailDto) {
		String childId = parentDetailDto.getChildId();
		boolean childFound = parentDetailService.childExists(childId);
		ResponseWrapper<?> noChild = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_CHILD);

		if (!childFound) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noChild);
		}
		ParentDetailEntity createdParentDetailEntity = parentDetailService.createParentDetail(parentDetailDto);
		if(createdParentDetailEntity != null) {			
		ResponseWrapper<ParentDetailEntity> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(),ValidationMessages.CREATE_SUCCESS, createdParentDetailEntity);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);}
		else {
		    ResponseWrapper<ParentDetailEntity> responseWrapper = new ResponseWrapper<>(RestApiResponseStatus.NOT_CREATED.getCode(), ValidationMessages.CREATE_FAILED, null);
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);}}

	
	@GetMapping(EndpointBundle.GET_ALL)
	public ResponseEntity<ResponseWrapper<?>>getAllParentDetails() {
		Iterable<ParentDetailEntity> getparentDetail = parentDetailService.getAllParentDetails();
		
		if (getparentDetail != null) {
			ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(), RestApiResponseStatus.OK.getStatus(), getparentDetail);
	        return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);
		}
		else{
            ResponseWrapper<Iterable<ParentDetailEntity>> notFoundResponse = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null);
        
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse); }	
	}

	@GetMapping(EndpointBundle.GET_BY_ID)
	public ResponseEntity<?> getParentDetailById(@PathVariable("id") Long id) {
		
		boolean idFound = parentDetailService.idFound(id);

        if (!idFound) {
        	ResponseWrapper<Iterable<ParentDetailEntity>> notFoundResponse = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
		
		ParentDetailEntity parentDetailEntity = parentDetailService.getParentDetailById(id);
		if (parentDetailEntity != null) {
			ResponseWrapper<?> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(), RestApiResponseStatus.OK.getStatus(), parentDetailEntity);
			return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);
		} else {
			ResponseWrapper<?> notFoundId = new ResponseWrapper<>(HttpStatus.NOT_FOUND.value(), ValidationMessages.NOT_FOUND, null);
			return ResponseEntity.status(HttpStatus.OK).body(notFoundId);
		}
	}
	
	@GetMapping(EndpointBundle.GET_BY_CHILD_ID)
	public ResponseEntity<ResponseWrapper<List<ParentDetailEntity>>> getparentDetailByChildId(@PathVariable("childId") String childId) {
		List<ParentDetailEntity> parentDetails = parentDetailService.getparentDetailByChildId(childId);
		if (parentDetails.isEmpty()) {
			ResponseWrapper<List<ParentDetailEntity>> noChild = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NO_CHILD, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body( noChild);
		} else {
			ResponseWrapper<List<ParentDetailEntity>> getResponseWrapper = new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), RestApiResponseStatus.RETRIEVED.getStatus(),parentDetails);
			return ResponseEntity.status(HttpStatus.OK).body(getResponseWrapper);
		}
	}
	
	@PutMapping(EndpointBundle.UPDATE_BY_ID)
	public ResponseEntity<ResponseWrapper<?>> updateParentDetail(@PathVariable("id") Long id,
																	  @RequestBody ParentDetailDto updatedParentDetailDto) {
		updatedParentDetailDto.setId(id);
		if (!parentDetailService.isParentDetailExists(id))
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.INVALID_ID, null));
		String childId = updatedParentDetailDto.getChildId();
		boolean childFound = parentDetailService.isParentDetailExists(childId) ;
		ResponseWrapper<?> noChild = new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), RestApiResponseStatus.NOT_FOUND.getStatus(), ValidationMessages.NO_CHILD);
		if (!childFound)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noChild);
		else {
			ParentDetailEntity updatedParentDetail = parentDetailService.updateParentDetail(id, updatedParentDetailDto);
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS, updatedParentDetail));
		}

	}

	@DeleteMapping(EndpointBundle.DELETE_BY_ID)
	public  ResponseEntity<ResponseWrapper<?>> deleteParentDetail(@PathVariable("id") Long id) {
		if (!parentDetailService.isParentDetailExists(id))
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_ID, null));


		RestApiResponseStatus status = parentDetailService.deleteParentDetail(id);
		if (status == RestApiResponseStatus.OK)
			return ResponseEntity.ok(new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(),
					ValidationMessages.DELETE_SUCCESS, null));
		return null;
	}}
