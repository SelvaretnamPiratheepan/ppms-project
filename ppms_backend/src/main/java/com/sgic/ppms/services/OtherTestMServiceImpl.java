package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.OtherTestMDto;
import com.sgic.ppms.entities.OtherTestM;
import com.sgic.ppms.repositories.OtherTestMRepository;

import jakarta.transaction.Transactional;

@Service
public class OtherTestMServiceImpl implements OtherTestMService {

	@Autowired
	public OtherTestMRepository otherTestMRepository;

	public OtherTestMServiceImpl(OtherTestMRepository otherTestMRepository) {
		this.otherTestMRepository = otherTestMRepository;
	}

	public List<OtherTestM> getAllDetails() {
		return otherTestMRepository.findAll();
	}

	@Transactional
	public OtherTestM createOtherTestM(OtherTestMDto otherTestMDto) {
		OtherTestM otherTestM = new OtherTestM();
		BeanUtils.copyProperties(otherTestMDto, otherTestM);
		return otherTestMRepository.save(otherTestM);
	}

	public OtherTestM getById(Long id) {
		return otherTestMRepository.findById(id).orElse(null);
	}

	public boolean deleteById(Long id) {
		Optional<OtherTestM> otherTestMOptional = otherTestMRepository.findById(id);
		if (otherTestMOptional.isPresent()) {
			otherTestMRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public boolean existsByTestname(String testname) {
		return otherTestMRepository.existsByTestname(testname);
	}

}
