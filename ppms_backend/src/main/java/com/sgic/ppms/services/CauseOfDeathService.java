package com.sgic.ppms.services;

import java.util.List;
import java.util.Optional;

import com.sgic.ppms.entities.CauseOfDeath;

public interface CauseOfDeathService {
	List<CauseOfDeath> getAllCaseOfDeaths();

	Optional<CauseOfDeath> getCauseOfDeathById(Long id);

	CauseOfDeath saveCauseOfDeath(CauseOfDeath causeOfDeath);

	void deleteCauseOfDeathById(Long id);
}
