package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.RadiologyOtherDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.OtherTestM;
import com.sgic.ppms.entities.RadiologyOther;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.OtherTestMRepository;
import com.sgic.ppms.repositories.RadiologyOtherRepository;

import jakarta.transaction.Transactional;

@Service
public class RadiologyOtherServiceImplementation implements RadiologyOtherService {

	@Autowired
	RadiologyOtherRepository radiologyOtherRepository;
	@Autowired
	AdmitRepository admitRepository;
	@Autowired
	OtherTestMRepository otherTestMRepository;

	@Transactional
	public RadiologyOther create(RadiologyOtherDto radiologyOtherDto) {

		RadiologyOther radiologyOther = new RadiologyOther();
		Admit admit = admitRepository.findById(radiologyOtherDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id:" + radiologyOtherDto.getAdmitId()));

		radiologyOther.setAdmit(admit);

		OtherTestM otherTestM = otherTestMRepository.findById(radiologyOtherDto.getOtherTestMId())
				.orElseThrow(() -> new IllegalArgumentException(
						"otherTest not found with id:" + radiologyOtherDto.getOtherTestMId()));

		radiologyOther.setOtherTestM(otherTestM);
		radiologyOtherDto.setId(0L);
		BeanUtils.copyProperties(radiologyOtherDto, radiologyOther);
		return radiologyOtherRepository.save(radiologyOther);

	}

	@Transactional
	public RadiologyOther update(RadiologyOtherDto radiologyOtherDto) {

		RadiologyOther radiologyOther = new RadiologyOther();
		Admit admit = admitRepository.findById(radiologyOtherDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id:" + radiologyOtherDto.getAdmitId()));

		radiologyOther.setAdmit(admit);

		OtherTestM otherTestM = otherTestMRepository.findById(radiologyOtherDto.getOtherTestMId())
				.orElseThrow(() -> new IllegalArgumentException(
						"otherTest not found with id:" + radiologyOtherDto.getOtherTestMId()));

		radiologyOther.setOtherTestM(otherTestM);
		BeanUtils.copyProperties(radiologyOtherDto, radiologyOther);
		return radiologyOtherRepository.save(radiologyOther);

	}

	public List<RadiologyOther> getByAdmitId(Long admitId) {
		return radiologyOtherRepository.findByAdmitId(admitId);
	}

	public List<RadiologyOther> getByTestId(Long testId) {
		return radiologyOtherRepository.findByOtherTestMId(testId);
	}

	public Optional<RadiologyOther> getById(Long id) {
		return radiologyOtherRepository.findById(id);
	}

	public boolean delete(Long id) {
		radiologyOtherRepository.deleteById(id);
		return true;
	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);

	}

	public boolean testExists(Long testId) {
		return otherTestMRepository.existsById(testId);
	}

	public boolean radiologyOtherExist(Long id) {
		return radiologyOtherRepository.existsById(id);
	}

}
