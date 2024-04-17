package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.ChildDevelopmentDTO;
import com.sgic.ppms.entities.ChildDevelopment;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.ChildDevelopmentRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@Service
public class ChildDevelopmentServiceImpl implements ChildDevelopmentService {

	private final ChildDevelopmentRepository childDevelopmentRepository;

	public ChildDevelopmentServiceImpl(ChildDevelopmentRepository childDevelopmentRepository) {
		this.childDevelopmentRepository = childDevelopmentRepository;
	}

	public ResponseWrapper<List<ChildDevelopmentDTO>> getAllChildDevelopmentsByChildId(String childId) {
		List<ChildDevelopment> childDevelopments = childDevelopmentRepository.findByChildId(childId);
		if (childDevelopments.isEmpty()) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_CHILDID,
					null);
		}
		List<ChildDevelopmentDTO> childDevelopmentDTOs = childDevelopments.stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
		return new ResponseWrapper<>(RestApiResponseStatus.RETRIEVED.getCode(), ValidationMessages.RETRIEVED,
				childDevelopmentDTOs);
	}

	public ResponseWrapper<List<ChildDevelopmentDTO>> getAllChildDevelopments() {
		List<ChildDevelopment> childDevelopments = childDevelopmentRepository.findAll();
		List<ChildDevelopmentDTO> childDevelopmentDTOs = childDevelopments.stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
		return new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(), ValidationMessages.RETRIEVED,
				childDevelopmentDTOs);
	}

	@Transactional
	public ResponseWrapper<ChildDevelopmentDTO> createChildDevelopment(ChildDevelopmentDTO childDevelopmentDTO) {
		String childId = childDevelopmentDTO.getChildId();
		Long developmentalStageId = childDevelopmentDTO.getDevelopmentalStageId();

		if (!childExists(childId)) {
			return new ResponseWrapper<ChildDevelopmentDTO>(RestApiResponseStatus.NOT_FOUND.getCode(),
					ValidationMessages.INVALID_CHILDID, null);
		}

		if (childDevelopmentRepository.existsByChildIdAndDevelopmentalStageId(childId, developmentalStageId)) {
			ChildDevelopment existingChildDevelopment = childDevelopmentRepository
					.findByChildIdAndDevelopmentalStageId(childId, developmentalStageId);
			ChildDevelopmentDTO existingChildDevelopmentDTO = convertEntityToDTO(existingChildDevelopment);
			return new ResponseWrapper<ChildDevelopmentDTO>(RestApiResponseStatus.VALIDATION_FAILURE.getCode(),
					ValidationMessages.CHILDID_EXISTS, existingChildDevelopmentDTO);
		}

		ChildDevelopment childDevelopment = convertDTOToEntity(childDevelopmentDTO);
		childDevelopmentRepository.save(childDevelopment);

		ChildDevelopmentDTO savedChildDevelopmentDTO = convertEntityToDTO(childDevelopment);

		return new ResponseWrapper<ChildDevelopmentDTO>(RestApiResponseStatus.CREATED.getCode(),
				ValidationMessages.CREATE_SUCCESS, savedChildDevelopmentDTO);
	}

	@Transactional
	public ResponseWrapper<ChildDevelopmentDTO> updateChildDevelopmentByChildId(String childId,
			ChildDevelopmentDTO childDevelopmentDTO) {
		List<ChildDevelopment> childDevelopments = childDevelopmentRepository.findByChildId(childId);
		if (childDevelopments.isEmpty()) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_CHILDID,
					null);
		}
		for (ChildDevelopment childDevelopment : childDevelopments) {
			childDevelopment.setStatus(childDevelopmentDTO.getStatus());
			childDevelopment.setDevelopmentalStageId(childDevelopmentDTO.getDevelopmentalStageId());
		}
		childDevelopmentRepository.saveAll(childDevelopments);
		ChildDevelopmentDTO updatedChildDevelopmentDTO = convertEntityToDTO(childDevelopments.get(0));
		return new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS,
				updatedChildDevelopmentDTO);
	}

	@Transactional
	public ResponseWrapper<ChildDevelopmentDTO> updateChildDevelopmentById(Long id,
			ChildDevelopmentDTO childDevelopmentDTO) {
		Optional<ChildDevelopment> optionalChildDevelopment = childDevelopmentRepository.findById(id);
		if (optionalChildDevelopment.isPresent()) {
			ChildDevelopment childDevelopment = optionalChildDevelopment.get();
			BeanUtils.copyProperties(childDevelopmentDTO, childDevelopment);
			childDevelopmentRepository.save(childDevelopment);
			ChildDevelopmentDTO updatedChildDevelopmentDTO = convertEntityToDTO(childDevelopment);
			return new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS,
					updatedChildDevelopmentDTO);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_ID,
					null);
		}
	}

	@Transactional
	public ResponseWrapper<ChildDevelopmentDTO> deleteChildDevelopmentByChildId(String childId) {
		Optional<ChildDevelopment> optionalChildDevelopment = childDevelopmentRepository.findByChildId(childId).stream()
				.findFirst();
		if (optionalChildDevelopment.isPresent()) {
			ChildDevelopment childDevelopment = optionalChildDevelopment.get();
			childDevelopmentRepository.deleteByChildId(childId);
			ChildDevelopmentDTO deletedChildDevelopmentDTO = convertEntityToDTO(childDevelopment);
			return new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(), ValidationMessages.DELETE_SUCCESS,
					deletedChildDevelopmentDTO);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_CHILDID,
					null);
		}
	}

	@Transactional
	public ResponseWrapper<ChildDevelopmentDTO> deleteChildDevelopmentById(Long id) {
		Optional<ChildDevelopment> optionalChildDevelopment = childDevelopmentRepository.findById(id);
		if (optionalChildDevelopment.isPresent()) {
			ChildDevelopment childDevelopment = optionalChildDevelopment.get();
			childDevelopmentRepository.deleteById(id);
			ChildDevelopmentDTO deletedChildDevelopmentDTO = convertEntityToDTO(childDevelopment);
			return new ResponseWrapper<>(RestApiResponseStatus.DELETED.getCode(), ValidationMessages.DELETE_SUCCESS,
					deletedChildDevelopmentDTO);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.INVALID_ID,
					null);
		}
	}

	private boolean childExists(String childId) {
		return childDevelopmentRepository.existsByChildId(childId);
	}

	private ChildDevelopmentDTO convertEntityToDTO(ChildDevelopment childDevelopment) {
		ChildDevelopmentDTO childDevelopmentDTO = new ChildDevelopmentDTO();
		BeanUtils.copyProperties(childDevelopment, childDevelopmentDTO);
		return childDevelopmentDTO;
	}

	private ChildDevelopment convertDTOToEntity(ChildDevelopmentDTO childDevelopmentDTO) {
		ChildDevelopment childDevelopment = new ChildDevelopment();
		BeanUtils.copyProperties(childDevelopmentDTO, childDevelopment);
		return childDevelopment;
	}
}
