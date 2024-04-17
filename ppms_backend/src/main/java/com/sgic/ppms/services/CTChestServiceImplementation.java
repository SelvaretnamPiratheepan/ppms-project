package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.CTChestDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.CTChest;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.CTChestRepository;

import jakarta.transaction.Transactional;

@Service
public class CTChestServiceImplementation implements CTChestService {

	@Autowired
	private CTChestRepository ctChestRepository;
	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public CTChest create(CTChestDto ctChestDto) {

		CTChest ctChest = new CTChest();
		Admit admit = admitRepository.findById(ctChestDto.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException("Admit not found with id:" + ctChestDto.getAdmitId()));

		ctChest.setAdmit(admit);
		ctChestDto.setId(0L);
		BeanUtils.copyProperties(ctChestDto, ctChest);
		return ctChestRepository.save(ctChest);

	}

	@Transactional
	public CTChest update(CTChestDto ctChestDto) {

		CTChest ctChest = new CTChest();
		Admit admit = admitRepository.findById(ctChestDto.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException("Admit not found with id:" + ctChestDto.getAdmitId()));

		ctChest.setAdmit(admit);
		BeanUtils.copyProperties(ctChestDto, ctChest);
		return ctChestRepository.save(ctChest);

	}

	public List<CTChest> getByAdmitId(long admitId) {
		return ctChestRepository.findByAdmitId(admitId);
	}

	public Optional<CTChest> getById(Long id) {
		return ctChestRepository.findById(id);

	}

	public boolean delete(Long id) {
		ctChestRepository.deleteById(id);
		return true;
	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);

	}

	public boolean ctChestExist(Long id) {
		return ctChestRepository.existsById(id);

	}

}
