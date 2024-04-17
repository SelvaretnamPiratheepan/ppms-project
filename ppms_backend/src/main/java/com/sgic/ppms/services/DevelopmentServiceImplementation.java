package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DevelopmentDetailsDto;
import com.sgic.ppms.entities.DevelopmentDetails;
import com.sgic.ppms.repositories.DevelopmentRepository;

import jakarta.transaction.Transactional;

@Service
public class DevelopmentServiceImplementation implements DevelopmentService {

	@Autowired
	private DevelopmentRepository developmentRepository;

	@Transactional
	public DevelopmentDetails addDevelopmentDetails(DevelopmentDetailsDto developmentDetailsDto) {
		DevelopmentDetails developmentDetails = new DevelopmentDetails();
		developmentDetailsDto.setId(0L);
		BeanUtils.copyProperties(developmentDetailsDto, developmentDetails);
		return developmentRepository.save(developmentDetails);

	}

	@Transactional
	public DevelopmentDetails updateDevelopmentDetails(DevelopmentDetailsDto developmentDetailsDto) {
		DevelopmentDetails developmentDetails = new DevelopmentDetails();
		BeanUtils.copyProperties(developmentDetailsDto, developmentDetails);
		return developmentRepository.save(developmentDetails);

	}

	public List<DevelopmentDetails> getAllDevelopmentDetails() {
		return developmentRepository.findAll();
	}

	public Optional<DevelopmentDetails> getDevelopmentDetailsById(long id) {
		return developmentRepository.findById(id);

	}

	public void deleteDevelopmentDetails(long id) {
		developmentRepository.deleteById(id);
	}

	public boolean developmentExist(long id) {
		return developmentRepository.existsById(id);
	}

}
