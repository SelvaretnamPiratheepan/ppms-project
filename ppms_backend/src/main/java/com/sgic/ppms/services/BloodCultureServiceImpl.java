package com.sgic.ppms.services;

import com.sgic.ppms.dto.BloodCultureDto;
import com.sgic.ppms.entities.BloodCultureDetailEntity;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.BloodCultureRepository;
import com.sgic.ppms.repositories.ChildRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BloodCultureServiceImpl implements BloodCultureService{
    private static final Logger logger = LoggerFactory.getLogger(BloodCultureServiceImpl.class);

    @Autowired
    private BloodCultureRepository bloodCultureRepository;
    @Autowired
    private ChildRepository childRepository;

    @Transactional
    public BloodCultureDetailEntity createBloodCultureDetail(BloodCultureDto bloodCultureDto) {
        BloodCultureDetailEntity bloodCultureDetailEntity = new BloodCultureDetailEntity();
        Optional<ChildDetail> optionalChildDetail = childRepository.findById(String.valueOf(bloodCultureDto.getChildDetailId()));
        if (optionalChildDetail.isPresent()) {
            ChildDetail childDetail = optionalChildDetail.get();
            bloodCultureDetailEntity.setChildDetail(childDetail);
            bloodCultureDto.setId(0L);
            BeanUtils.copyProperties(bloodCultureDto, bloodCultureDetailEntity);
            return bloodCultureRepository.save(bloodCultureDetailEntity);
        }
        logger.error("ChildDetail with ID {} not found", bloodCultureDto.getChildDetailId());
        return null;
    }


    public List<BloodCultureDetailEntity> getAllBloodCultureDetail() {
        return bloodCultureRepository.findAll();
    }

    @Transactional
    public BloodCultureDetailEntity updateBloodDetail(Long id, BloodCultureDto updatedBloodCulturelDto) {
        Optional<BloodCultureDetailEntity> optionalBloodCultureDetailEntity = bloodCultureRepository.findById(id);
        if (optionalBloodCultureDetailEntity.isPresent()) {
            BloodCultureDetailEntity bloodCultureDetailEntity = optionalBloodCultureDetailEntity.get();
            Optional<ChildDetail> ChildDetail = childRepository.findById(updatedBloodCulturelDto.getChildDetailId());
            if (ChildDetail.isPresent()) {
                ChildDetail childDetailEntity = ChildDetail.get();
                bloodCultureDetailEntity.setChildDetail(childDetailEntity);
                BeanUtils.copyProperties(updatedBloodCulturelDto, bloodCultureDetailEntity);
            logger.info("Parent detail updated successfully: {}", updatedBloodCulturelDto);
            return bloodCultureRepository.save(bloodCultureDetailEntity);}
        }
        return null;
    }

    public RestApiResponseStatus deleteBloodCultureDetail(Long id) {
        Optional<BloodCultureDetailEntity> optionalBloodCultureDetailEntity = bloodCultureRepository.findById(id);

        if (optionalBloodCultureDetailEntity.isPresent()) {
            bloodCultureRepository.deleteById(id);
            return RestApiResponseStatus.OK;
        }
        return RestApiResponseStatus.VALIDATION_FAILURE;
    }


    public boolean isBloodCultureExists(Long id) {
        return !bloodCultureRepository.existsById(id);
    }

    @Override
    public boolean isBloodCultureExists(String byChildId) {
        return childRepository.existsById(byChildId);
    }

    @Override
    public List<BloodCultureDetailEntity> getbloodCultureByChildId(String childId)
    {
        return bloodCultureRepository.findByChildDetailId(childId);
    }

    public boolean childExists(String childId) {
        return childRepository.existsByChildId(childId);
    }


}
