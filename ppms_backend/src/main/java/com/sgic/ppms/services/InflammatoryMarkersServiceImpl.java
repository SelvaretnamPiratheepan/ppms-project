package com.sgic.ppms.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.InflammatoryMarkersDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.InflammatoryMarkers;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.InflammatoryMarkersRepository;

@Service
public class InflammatoryMarkersServiceImpl implements InflammatoryMarkersService {
	private final Logger logger = LoggerFactory.getLogger(InflammatoryMarkersServiceImpl.class);

	@Autowired
	private InflammatoryMarkersRepository inflammatoryMarkersRepository;

	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public InflammatoryMarkersDto createInflammatoryMarkers(InflammatoryMarkersDto inflammatoryMarkersDto) {
		logger.info("Creating new InflammatoryMarkers: {}", inflammatoryMarkersDto);
		InflammatoryMarkers inflammatoryMarkers = new InflammatoryMarkers();
		inflammatoryMarkers.setDate(inflammatoryMarkersDto.getDate());
		inflammatoryMarkers.setCRP(inflammatoryMarkersDto.getCrp());
		inflammatoryMarkers.setESR(inflammatoryMarkersDto.getEsr());

		Admit admit = admitRepository.findById(inflammatoryMarkersDto.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + inflammatoryMarkersDto.getAdmitId()));

		inflammatoryMarkers.setAdmit(admit);
		InflammatoryMarkers savedInflammatoryMarkers = inflammatoryMarkersRepository.save(inflammatoryMarkers);
		inflammatoryMarkersDto.setId(savedInflammatoryMarkers.getId());
		return inflammatoryMarkersDto;
	}

	@Transactional
	public InflammatoryMarkersDto updateInflammatoryMarkers(Long id, InflammatoryMarkersDto inflammatoryMarkersDto) {
		InflammatoryMarkers existingInflammatoryMarkers = inflammatoryMarkersRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Inflammatory Markers not found with id: " + id));

		existingInflammatoryMarkers.setDate(inflammatoryMarkersDto.getDate());
		existingInflammatoryMarkers.setCRP(inflammatoryMarkersDto.getCrp());
		existingInflammatoryMarkers.setESR(inflammatoryMarkersDto.getEsr());

		Admit admit = admitRepository.findById(inflammatoryMarkersDto.getAdmitId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Admit record not found with id: " + inflammatoryMarkersDto.getAdmitId()));
		existingInflammatoryMarkers.setAdmit(admit);

		InflammatoryMarkers updatedInflammatoryMarkers = inflammatoryMarkersRepository
				.save(existingInflammatoryMarkers);

		inflammatoryMarkersDto.setId(updatedInflammatoryMarkers.getId());
		return inflammatoryMarkersDto;
	}

	@Transactional(readOnly = true)
	public List<InflammatoryMarkersDto> getInflammatoryMarkersByAdmitId(Long admitId) {

		List<InflammatoryMarkers> inflammatories = inflammatoryMarkersRepository.findByAdmitId(admitId);

		if (inflammatories.isEmpty()) {
			throw new IllegalArgumentException(
					"Inflammatory Markers results not found with Child Detail ID:" + admitId);
		}
		List<InflammatoryMarkersDto> inflammatoryMarkersDtos = new ArrayList<>();
		for (InflammatoryMarkers inflammatoryMarkers : inflammatories) {
			InflammatoryMarkersDto inflammatoryMarkersDto = new InflammatoryMarkersDto();
			inflammatoryMarkersDto.setId(inflammatoryMarkers.getId());
			inflammatoryMarkersDto.setDate(inflammatoryMarkers.getDate());
			inflammatoryMarkersDto.setCrp(inflammatoryMarkers.getCRP());
			inflammatoryMarkersDto.setEsr(inflammatoryMarkers.getESR());
			inflammatoryMarkersDto.setAdmitId(admitId);
			inflammatoryMarkersDtos.add(inflammatoryMarkersDto);
		}

		return inflammatoryMarkersDtos;
	}

	@Transactional(readOnly = true)
	public InflammatoryMarkersDto getInflammatoryMarkersById(Long Id) {
		InflammatoryMarkers inflame = inflammatoryMarkersRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Inflammatory Markers not found with given Id: " + Id));
		InflammatoryMarkersDto inflameDto = new InflammatoryMarkersDto();
		inflameDto.setId(inflame.getId());
		inflameDto.setDate(inflame.getDate());
		inflameDto.setCrp(inflame.getCRP());
		inflameDto.setEsr(inflame.getESR());
		inflameDto.setAdmitId(inflame.getAdmit().getId());
		return inflameDto;
	}

	public boolean admitExists(Long admitId) {
		return admitRepository.existsById(admitId);
	}

	public boolean inflammatoryFound(Long id) {
		return inflammatoryMarkersRepository.existsById(id);
	}

	@Transactional
	public void deleteInflammatoryMarkersById(Long id) {
		inflammatoryMarkersRepository.deleteById(id);
	}

}
