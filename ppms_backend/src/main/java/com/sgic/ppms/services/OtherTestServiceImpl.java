package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.OtherTestDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.OtherTest;
import com.sgic.ppms.entities.OtherTestM;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.OtherTestMRepository;
import com.sgic.ppms.repositories.OtherTestRepository;

import jakarta.transaction.Transactional;

@Service
public class OtherTestServiceImpl implements OtherTestService {

	@Autowired
	OtherTestRepository otherTestRepository;
	@Autowired
	AdmitRepository admitRepository;
	@Autowired
	OtherTestMRepository otherTestMRepository;

	@Transactional
	public OtherTest create(OtherTestDto otherTestDto) {

		OtherTest otherTest = new OtherTest();
		Admit admit = admitRepository.findById(otherTestDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id:" + otherTestDto.getAdmitId()));

		otherTest.setAdmit(admit);

		OtherTestM otherTestM = otherTestMRepository.findById(otherTestDto.getOtherTestmId()).orElseThrow(
				() -> new IllegalArgumentException("OtherTestM not found with id:" + otherTestDto.getOtherTestmId()));

		otherTest.setOtherTestM(otherTestM);
		otherTestDto.setId(0L);
		BeanUtils.copyProperties(otherTestDto, otherTest);
		return otherTestRepository.save(otherTest);

	}

	@Transactional
	public OtherTest update(OtherTestDto otherTestDto) {

		OtherTest otherTest = new OtherTest();
		Admit admit = admitRepository.findById(otherTestDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id:" + otherTestDto.getAdmitId()));

		otherTest.setAdmit(admit);

		OtherTestM otherTestM = otherTestMRepository.findById(otherTestDto.getOtherTestmId()).orElseThrow(
				() -> new IllegalArgumentException("otherTest not found with id:" + otherTestDto.getOtherTestmId()));

		otherTest.setOtherTestM(otherTestM);
		BeanUtils.copyProperties(otherTestDto, otherTest);
		return otherTestRepository.save(otherTest);

	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);
	}

	public List<OtherTest> getByAdmitId(long admitId) {
		return otherTestRepository.findByAdmitId(admitId);
	}

	public List<OtherTest> getByTestId(Long testmId) {
		return otherTestRepository.findByOtherTestMId(testmId);
	}

	public Optional<OtherTest> getById(long id) {
		return otherTestRepository.findById(id);
	}

	public boolean deleteById(long id) {
		otherTestRepository.deleteById(id);
		return true;

	}

	public boolean testmExists(Long otherTestMId) {
		return otherTestMRepository.existsById(otherTestMId);
	}

	public boolean existsById(long id) {
		return otherTestRepository.existsById(id);
	}

}
