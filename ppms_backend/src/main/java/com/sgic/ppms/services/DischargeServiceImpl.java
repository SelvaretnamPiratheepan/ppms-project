package com.sgic.ppms.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.DischargeDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.CauseOfDeath;
import com.sgic.ppms.entities.Discharge;
import com.sgic.ppms.entities.Place;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.CauseOfDeathRepository;
import com.sgic.ppms.repositories.DischargeRepository;
import com.sgic.ppms.repositories.PlaceRepository;

@Service
public class DischargeServiceImpl implements DischargeService {

	@Autowired
	private DischargeRepository dischargeRepository;

	@Autowired
	private AdmitRepository admitRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private CauseOfDeathRepository causeOfDeathRepository;

	@Override
	public boolean existsById(Long id) {
		return dischargeRepository.existsById(id);
	}

	@Override
	public boolean admitIdExists(Long admitId) {
		return !admitRepository.existsById(admitId);
	}

	@Override
	public boolean placeIdExists(Long placeId) {
		return !placeRepository.existsById(placeId);
	}

	@Override
	public boolean causeOfDeathIdExists(Long causeOfDeathId) {
		return !causeOfDeathRepository.existsById(causeOfDeathId);
	}

	@Override
	public boolean admitIdExistsInDiagnosisDetails(Long admitId) {
		return dischargeRepository.existsByAdmitId(admitId);
	}

	@Override
	public Long getAdmitIdByRecordId(Long id) {
		Discharge discharge = dischargeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Discharge record not found for ID: " + id));
		return discharge.getAdmit().getId();
	}

	@Override
	public List<Discharge> getAllDischarges() {
		return dischargeRepository.findAll();
	}

	@Override
	@Transactional
	public DischargeDto createDischargeFromDto(DischargeDto dischargeDto) {
		Discharge discharge = new Discharge();
		BeanUtils.copyProperties(dischargeDto, discharge);

		Admit admit = admitRepository.findById(dischargeDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + dischargeDto.getAdmitId()));
		discharge.setAdmit(admit);

		Place place = placeRepository.findById(dischargeDto.getPlaceId()).orElseThrow(
				() -> new IllegalArgumentException("Place not found with id: " + dischargeDto.getPlaceId()));
		discharge.setPlace(place);

		CauseOfDeath causeOfDeath = causeOfDeathRepository.findById(dischargeDto.getCauseOfDeathId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Cause of Death not found with id: " + dischargeDto.getCauseOfDeathId()));
		discharge.setCauseOfDeath(causeOfDeath);

		if (discharge.getFollowUp()) {
			discharge.setWhen(dischargeDto.getWhen());
			discharge.setFollowUpType(dischargeDto.getFollowUpType());
		} else {
			discharge.setWhen(null);
			discharge.setFollowUpType(null);
		}

		switch (dischargeDto.getTypeOfDischarge()) {
		case Death:
			discharge.setFollowUp(false);
			discharge.setWhen(null);
			discharge.setPlace(null);
			break;
		case Transfer:
			discharge.setFollowUp(false);
			discharge.setWhen(null);
			discharge.setCauseOfDeath(null);
			break;
		case Discharge:
			discharge.setPlace(null);
			discharge.setCauseOfDeath(null);
			break;
		}
		dischargeRepository.save(discharge);
		BeanUtils.copyProperties(discharge, dischargeDto);
		dischargeDto.setId(discharge.getId());
		return dischargeDto;
	}

	@Override
	@Transactional
	public DischargeDto updateDischarge(Long id, DischargeDto dischargeDto) {
		Discharge existingDischarge = dischargeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Discharge not found with id: " + id));

		BeanUtils.copyProperties(dischargeDto, existingDischarge);

		Admit admit = admitRepository.findById(dischargeDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + dischargeDto.getAdmitId()));
		existingDischarge.setAdmit(admit);

		Place place = placeRepository.findById(dischargeDto.getPlaceId()).orElseThrow(
				() -> new IllegalArgumentException("Place not found with id: " + dischargeDto.getPlaceId()));
		existingDischarge.setPlace(place);

		CauseOfDeath causeOfDeath = causeOfDeathRepository.findById(dischargeDto.getCauseOfDeathId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Cause of Death not found with id: " + dischargeDto.getCauseOfDeathId()));
		existingDischarge.setCauseOfDeath(causeOfDeath);
		switch (dischargeDto.getTypeOfDischarge()) {
		case Death:
			existingDischarge.setFollowUp(false);
			existingDischarge.setWhen(null);
			existingDischarge.setPlace(null);
			break;
		case Transfer:
			existingDischarge.setFollowUp(false);
			existingDischarge.setWhen(null);
			existingDischarge.setCauseOfDeath(null);
			break;
		case Discharge:
			existingDischarge.setPlace(null);
			existingDischarge.setCauseOfDeath(null);
			break;

		}
		if (existingDischarge.getFollowUp()) {

			existingDischarge.setWhen(dischargeDto.getWhen());
			existingDischarge.setFollowUpType(dischargeDto.getFollowUpType());
		} else {

			existingDischarge.setWhen(null);
			existingDischarge.setFollowUpType(null);
		}
		Discharge updatedDischarge = dischargeRepository.save(existingDischarge);

		DischargeDto updatedDischargeDto = new DischargeDto();
		BeanUtils.copyProperties(updatedDischarge, updatedDischargeDto);
		updatedDischargeDto.setId(existingDischarge.getId());
		updatedDischargeDto
				.setPlaceId(existingDischarge.getPlace() != null ? existingDischarge.getPlace().getId() : null);
		updatedDischargeDto.setCauseOfDeathId(
				existingDischarge.getCauseOfDeath() != null ? existingDischarge.getCauseOfDeath().getId() : null);
		updatedDischargeDto.setTypeOfDischarge(existingDischarge.getTypeOfDischarge());
		updatedDischargeDto.setDate(existingDischarge.getDate());
		updatedDischargeDto.setFollowUp(existingDischarge.getFollowUp());
		updatedDischargeDto.setWhen(existingDischarge.getWhen());
		updatedDischargeDto.setFollowUpType(existingDischarge.getFollowUpType());
		updatedDischargeDto.setAdmitId(existingDischarge.getAdmit().getId());
		return updatedDischargeDto;
	}

	@Override
	public DischargeDto getDischargeByAdmitId(Long admitId) {
		Discharge discharge = dischargeRepository.findByAdmitId(admitId);

		DischargeDto dischargeDto = new DischargeDto();

		dischargeDto.setId(discharge.getId());
		dischargeDto.setAdmitId(discharge.getAdmit().getId());
		dischargeDto.setPlaceId(discharge.getPlace() != null ? discharge.getPlace().getId() : null);
		dischargeDto
				.setCauseOfDeathId(discharge.getCauseOfDeath() != null ? discharge.getCauseOfDeath().getId() : null);
		dischargeDto.setTypeOfDischarge(discharge.getTypeOfDischarge());
		dischargeDto.setDate(discharge.getDate());
		dischargeDto.setFollowUp(discharge.getFollowUp());
		dischargeDto.setWhen(discharge.getWhen());
		dischargeDto.setFollowUpType(discharge.getFollowUpType());

		return dischargeDto;
	}
}