package com.sgic.ppms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.AdmitDTO;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.ChildDetail;
import com.sgic.ppms.entities.Place;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.ChildRepository;
import com.sgic.ppms.repositories.PlaceRepository;

@Service
public class AdmitServiceImpl implements AdmitService {

	@Autowired
	private AdmitRepository admitRepository;

	@Autowired
	private ChildRepository childRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Transactional
	public AdmitDTO createAdmit(AdmitDTO admitDTO) {
		Admit admit = new Admit();
		BeanUtils.copyProperties(admitDTO, admit);
		ChildDetail childDetail = childRepository.findById(admitDTO.getChildId()).orElseThrow(
				() -> new IllegalArgumentException("Child Detail is not found with id: " + admitDTO.getChildId()));
		admit.setChildDetail(childDetail);
		Optional.ofNullable(admitDTO.getPlace()).ifPresent(placeDTO -> {
			Place place = placeRepository.findById(placeDTO.getId()).orElseThrow(
					() -> new IllegalArgumentException("Place Detail is not found with id: " + placeDTO.getId()));
			admit.setPlace(place);
			admitDTO.setPlace(place);
		});
		Admit savedAdmit = admitRepository.save(admit);
		admitDTO.setId(savedAdmit.getId());
		return admitDTO;
	}

	public List<Admit> getAllAdmits() {
		return admitRepository.findAll();
	}

	@Transactional(readOnly = true)
	public AdmitDTO getAdmitById(Long id) {
		Admit admit = admitRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admit Not Found with ID: " + id));
		AdmitDTO admitDTO = new AdmitDTO();
		BeanUtils.copyProperties(admit, admitDTO);
		admitDTO.setChildId(admit.getChildDetail().getChildId());
		Optional.ofNullable(admit.getPlace()).ifPresent(placeDTO -> {
			Place place = placeRepository.findById(placeDTO.getId()).orElseThrow(
					() -> new IllegalArgumentException("Place Detail is not found with id: " + placeDTO.getId()));
			admitDTO.setPlace(place);
		});
		return admitDTO;

	}

	@Transactional
	public AdmitDTO updateAdmit(Long id, AdmitDTO admitDTO) {
		Admit admit = admitRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admit Not Found with ID: " + id));
		admitDTO.setId(id);
		BeanUtils.copyProperties(admitDTO, admit);
		ChildDetail childDetail = childRepository.findById(admitDTO.getChildId()).orElseThrow(
				() -> new IllegalArgumentException("Child Detail is not found with id: " + admitDTO.getChildId()));
		admit.setChildDetail(childDetail);
		Optional.ofNullable(admitDTO.getPlace()).ifPresent(placeDTO -> {
			Place place = placeRepository.findById(placeDTO.getId()).orElseThrow(
					() -> new IllegalArgumentException("Place Detail is not found with id: " + placeDTO.getId()));
			admit.setPlace(place);
			admitDTO.setPlace(place);
		});
		admitRepository.save(admit);
		return admitDTO;

	}

	@Transactional
	public void deleteAdmit(Long id) {
		admitRepository.deleteById(id);
	}

	public boolean admitExists(Long id) {
		return admitRepository.existsById(id);
	}

	@Transactional(readOnly = true)
	public List<AdmitDTO> getAdmitsBetweenDates(LocalDate fromDate, LocalDate endDate) {
		List<Admit> admits = admitRepository.findByDateBetween(fromDate, endDate);
		admits.addAll(admitRepository.findByTransferDateBetween(fromDate, endDate));
		List<AdmitDTO> admitDtos = new ArrayList<>();
		for (Admit admit : admits) {
			AdmitDTO admitDTO = new AdmitDTO();
			BeanUtils.copyProperties(admit, admitDTO);
			admitDTO.setChildId(admit.getChildDetail().getChildId());
			Optional.ofNullable(admit.getPlace()).ifPresent(placeDTO -> {
				Place place = placeRepository.findById(placeDTO.getId()).orElseThrow(
						() -> new IllegalArgumentException("Place Detail is not found with id: " + placeDTO.getId()));
				admitDTO.setPlace(place);
			});
			admitDtos.add(admitDTO);
		}
		return admitDtos;
	}

}
