package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.BioChemistryDto;
import com.sgic.ppms.entities.BioChemistry;
import com.sgic.ppms.repositories.BioChemistryRepository;

import jakarta.transaction.Transactional;

@Service
public class BioChemistryServiceImpl implements BioChemistryService {
	@Autowired
	private BioChemistryRepository repo;

	@Override
	@Transactional
	public BioChemistry createBioChemistry(BioChemistryDto bioChemistryDto) {
		BioChemistry bioChemistry = new BioChemistry();
		BeanUtils.copyProperties(bioChemistryDto, bioChemistry);
		return repo.save(bioChemistry);
	}

	@Override
	public List<BioChemistry> getAllBioChemistries() {
		return repo.findAll();
	}

	@Override
	public BioChemistry getBioChemistryById(Long id) {
		return repo.findById(id).get();
	}

	@Override
	@Transactional
	public BioChemistry updateBioChemistry(Long id, BioChemistryDto bioChemistryDto) {
		BioChemistry bioChemistry = repo.findById(id).get();
		if (bioChemistry != null) {
			BeanUtils.copyProperties(bioChemistryDto, bioChemistry);
			bioChemistry.setId(id);
			return repo.save(bioChemistry);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean deleteBioChemistry(Long id) {
		Optional<BioChemistry> bioChemistryOptional = repo.findById(id);
		if (bioChemistryOptional.isPresent()) {
			repo.delete(bioChemistryOptional.get());
			return true;
		} else {
			return false;
		}
	}
}
