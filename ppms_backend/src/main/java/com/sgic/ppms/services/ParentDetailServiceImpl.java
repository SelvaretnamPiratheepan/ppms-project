package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.ParentDetailDto;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.entities.ParentDetailEntity;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.ChildRepository;
import com.sgic.ppms.repositories.ParentDetailRepository;

@Service
public class ParentDetailServiceImpl implements ParentDetailService {

	@Autowired
	private ParentDetailRepository parentDetailRepository;

	@Autowired
	private ChildRepository childRepository;

	@Transactional
	public ParentDetailEntity createParentDetail(ParentDetailDto parentDetailDto) {
		ParentDetailEntity parentDetailEntity = new ParentDetailEntity();
		Optional<ChildDetail> optionalChildDetail = childRepository
				.findById(String.valueOf(parentDetailDto.getChildId()));
		if (optionalChildDetail.isPresent()) {
			ChildDetail childDetail = optionalChildDetail.get();
			parentDetailEntity.setChildDetail(childDetail);
			parentDetailDto.setId(0L);
			BeanUtils.copyProperties(parentDetailDto, parentDetailEntity);
			return parentDetailRepository.save(parentDetailEntity);
		}

		return null;
	}

	@Override
	public List<ParentDetailEntity> getAllParentDetails() {
		return parentDetailRepository.findAll();
	}

	@Override
	public ParentDetailEntity getParentDetailById(Long parentId) {
		Optional<ParentDetailEntity> optionalParentDetailEntity = parentDetailRepository.findById(parentId);
		return optionalParentDetailEntity.orElse(null);
	}

	@Override
	public ParentDetailEntity updateParentDetail(Long id, ParentDetailDto updatedParentDetailDto) {
		Optional<ParentDetailEntity> optionalParentDetailEntity = parentDetailRepository.findById(id);
		if (optionalParentDetailEntity.isPresent()) {
			ParentDetailEntity parentDetailEntity = optionalParentDetailEntity.get();
			Optional<ChildDetail> ChildDetail = childRepository.findById(updatedParentDetailDto.getChildId());
			if (ChildDetail.isPresent()) {
				ChildDetail childDetailEntity = ChildDetail.get();
				parentDetailEntity.setChildDetail(childDetailEntity);
				BeanUtils.copyProperties(updatedParentDetailDto, parentDetailEntity);
				return parentDetailRepository.save(parentDetailEntity);
			}
		}
		return null;
	}

	@Override
	public RestApiResponseStatus deleteParentDetail(Long id) {
		Optional<ParentDetailEntity> optionalParentDetailEntity = parentDetailRepository.findById(id);
		if (optionalParentDetailEntity.isPresent()) {
			parentDetailRepository.deleteById(id);
			return RestApiResponseStatus.OK;
		} else {
			return RestApiResponseStatus.VALIDATION_FAILURE;
		}
	}

	public List<ParentDetailEntity> getparentDetailByChildId(String childId) {
		return parentDetailRepository.findByChildDetailChildId(childId);
	}

	public boolean childExists(String childId) {
		return childRepository.existsByChildId(childId);
	}

	@Override
	public boolean isParentDetailExists(Long id) {
		return parentDetailRepository.existsById(id);
	}

	@Override
	public boolean isParentDetailExists(String childId) {
		return childRepository.existsById(childId);
	}

	public boolean idFound(Long id) {

		return parentDetailRepository.existsById(id);

	}

}
