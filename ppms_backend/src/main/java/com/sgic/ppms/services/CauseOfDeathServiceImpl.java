package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.entities.CauseOfDeath;
import com.sgic.ppms.repositories.CauseOfDeathRepository;

@Service
public class CauseOfDeathServiceImpl implements CauseOfDeathService {

	@Autowired
	private CauseOfDeathRepository causeOfDeathRepository;

	@Override
	public List<CauseOfDeath> getAllCaseOfDeaths() {

		return causeOfDeathRepository.findAll();
	}

	@Override
	public Optional<CauseOfDeath> getCauseOfDeathById(Long id) {
		return causeOfDeathRepository.findById(id);
	}

	@Override
	public CauseOfDeath saveCauseOfDeath(CauseOfDeath causeOfDeath) {
		return causeOfDeathRepository.save(causeOfDeath);
	}

	@Override
	public void deleteCauseOfDeathById(Long id) {
		causeOfDeathRepository.deleteById(id);
	}
}
