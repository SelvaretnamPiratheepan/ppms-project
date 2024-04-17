package com.sgic.ppms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.HaematologyDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.Haematology;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.HaematologyRepository;

@Service
public class HaematologyServiceImpl implements HaematologyService {
	@Autowired
	private HaematologyRepository haematologyRepository;
	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public HaematologyDto createHaematology(HaematologyDto haematologyDto) {
		Haematology haematology = new Haematology();
		haematologyDto.setId(0L);
		BeanUtils.copyProperties(haematologyDto, haematology);
		Admit admit = admitRepository.findById(haematologyDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with this Id:  " + haematologyDto.getAdmitId()));
		haematology.setAdmit(admit);
		Haematology savedHaematology = haematologyRepository.save(haematology);
		haematologyDto.setId(savedHaematology.getId());
		return haematologyDto;
	}

	@Transactional
	public HaematologyDto updateHaematology(Long id, HaematologyDto haematologyDto) {
		Haematology existingHaematology = haematologyRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Haematology not found with id: " + id));
		BeanUtils.copyProperties(haematologyDto, existingHaematology);
		existingHaematology.setId(id);
		Admit admit = admitRepository.findById(haematologyDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + haematologyDto.getAdmitId()));
		existingHaematology.setAdmit(admit);
		Haematology updatedHaematology = haematologyRepository.save(existingHaematology);
		BeanUtils.copyProperties(updatedHaematology, haematologyDto);
		return haematologyDto;
	}

	@Transactional(readOnly = true)
	public HaematologyDto getHaematologybyId(Long Id) {
		Haematology haematology = haematologyRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Haematology is not found with given Id:  " + Id));
		HaematologyDto haematologyDto = new HaematologyDto();
		BeanUtils.copyProperties(haematology, haematologyDto);
		haematologyDto.setAdmitId(haematology.getAdmit().getId());
		return haematologyDto;
	}

	@Transactional(readOnly = true)
	public List<HaematologyDto> getHaematologybyAdmitId(Long admitId) {
		List<Haematology> haematologies = haematologyRepository.findByAdmitId(admitId);
		if (haematologies.isEmpty()) {
			throw new IllegalArgumentException("Haematology results not found with Admit ID:" + admitId);
		}
		List<HaematologyDto> haematologyDtos = new ArrayList<>();
		for (Haematology haematology : haematologies) {
			HaematologyDto haematologyDto = new HaematologyDto();
			BeanUtils.copyProperties(haematology, haematologyDto);
			haematologyDto.setAdmitId(admitId);
			haematologyDtos.add(haematologyDto);
		}

		return haematologyDtos;
	}

	public void deleteById(long Id) {
		haematologyRepository.deleteById(Id);
	}

	public boolean haematologyFound(Long id) {
		return haematologyRepository.existsById(id);
	}

}
