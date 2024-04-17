package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.UrineTestDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.UrineTest;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.UrineTestRepository;

import jakarta.transaction.Transactional;

@Service
public class UrineTestServiceImpl implements UrineTestService {
	@Autowired
	private UrineTestRepository urineTestRepository;
	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public UrineTest createUrineTest(UrineTestDto urineTestDto) {
		UrineTest urineTest = new UrineTest();
		BeanUtils.copyProperties(urineTestDto, urineTest);
		Admit admit = admitRepository.findById(urineTestDto.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException());
		urineTest.setAdmit(admit);
		return urineTestRepository.save(urineTest);
	}

	public UrineTest getUrineTestById(Long id) {
		Optional<UrineTest> optionalUrineTest = urineTestRepository.findById(id);
		return optionalUrineTest.orElse(null);
	}

	public List<UrineTest> getAllUrineTest() {
		return urineTestRepository.findAll();
	}

	public List<UrineTest> getAllUrineTestByAdmitId(Long admitId) {

		List<UrineTest> entities = urineTestRepository.findByAdmitId(admitId);
		return entities;
	}

	@Transactional
	public UrineTest updateUrineTest(Long id, UrineTestDto updatedUrineTestDto) {
		Optional<UrineTest> optionalUrineTest = urineTestRepository.findById(id);

		if (optionalUrineTest.isPresent()) {
			UrineTest urineTest = optionalUrineTest.get();
			updateUrineTestDetails(urineTest, updatedUrineTestDto);
			return urineTestRepository.save(urineTest);
		} else {

			return null;
		}
	}

	private void updateUrineTestDetails(UrineTest urineTest, UrineTestDto updatedUrineTestDto) {
		if (updatedUrineTestDto.getAdmitId() != null) {
			Admit admit = admitRepository.findById(updatedUrineTestDto.getAdmitId())
					.orElseThrow(() -> new IllegalArgumentException());
			urineTest.setAdmit(admit);
		}
		if (updatedUrineTestDto.getAlbumin() != null) {
			urineTest.setAlbumin(updatedUrineTestDto.getAlbumin());
		}
		if (updatedUrineTestDto.getPuscells() != null) {
			urineTest.setPuscells(updatedUrineTestDto.getPuscells());
		}
		if (updatedUrineTestDto.getCast() != null) {
			urineTest.setCast(updatedUrineTestDto.getCast());
		}

		if (updatedUrineTestDto.getCrystals() != null) {
			urineTest.setCrystals(updatedUrineTestDto.getCrystals());
		}
		if (updatedUrineTestDto.getDate() != null) {
			urineTest.setDate(updatedUrineTestDto.getDate());
		}
		if (updatedUrineTestDto.getCulture() != null) {
			urineTest.setCulture(updatedUrineTestDto.getCulture());
		}
		if (updatedUrineTestDto.getEpicells() != null) {
			urineTest.setEpicells(updatedUrineTestDto.getEpicells());
		}
		if (updatedUrineTestDto.getRbs() != null) {
			urineTest.setRbs(updatedUrineTestDto.getRbs());
		}
	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);
	}

	@Transactional

	public RestApiResponseStatus deleteUrineTest(long id) {
		Optional<UrineTest> optionalUrineTest = urineTestRepository.findById(id);

		if (optionalUrineTest.isPresent()) {
			urineTestRepository.deleteById(id);
			return RestApiResponseStatus.DELETED;
		}
		return RestApiResponseStatus.NOT_FOUND;

	}
}
