package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.USSAdboDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.USSAdbo;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.USSAdboRepository;

import jakarta.transaction.Transactional;

@Service
public class USSAdboServiceImplementation implements USSAdboService {

	@Autowired
	private USSAdboRepository ussAdboRepository;
	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public USSAdbo create(USSAdboDto ussAdboDto) {

		USSAdbo ussAdbo = new USSAdbo();
		Admit admit = admitRepository.findById(ussAdboDto.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException("Admit not found with id:" + ussAdboDto.getAdmitId()));

		ussAdbo.setAdmit(admit);
		ussAdboDto.setId(0L);
		BeanUtils.copyProperties(ussAdboDto, ussAdbo);
		return ussAdboRepository.save(ussAdbo);

	}

	@Transactional
	public USSAdbo update(USSAdboDto ussAdboDto) {

		USSAdbo ussAdbo = new USSAdbo();
		Admit admit = admitRepository.findById(ussAdboDto.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException("Admit not found with id:" + ussAdboDto.getAdmitId()));

		ussAdbo.setAdmit(admit);
		BeanUtils.copyProperties(ussAdboDto, ussAdbo);
		return ussAdboRepository.save(ussAdbo);

	}

	public List<USSAdbo> getByAdmitId(long admitId) {
		return ussAdboRepository.findByAdmitId(admitId);
	}

	public Optional<USSAdbo> getById(Long id) {
		return ussAdboRepository.findById(id);

	}

	public boolean delete(Long id) {
		ussAdboRepository.deleteById(id);
		return true;
	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);

	}

	public boolean ussAdboExist(Long id) {
		return ussAdboRepository.existsById(id);

	}

}
