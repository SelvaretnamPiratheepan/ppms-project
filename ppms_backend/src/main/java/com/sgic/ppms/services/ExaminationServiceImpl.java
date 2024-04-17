package com.sgic.ppms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.AnthropometryDto;
import com.sgic.ppms.dto.ExaminationDto;
import com.sgic.ppms.entities.Anthropometry;
import com.sgic.ppms.entities.Examination;
import com.sgic.ppms.repositories.ExaminationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {

	@Autowired
	private ExaminationRepository examinationRepository;

	@Autowired
	private AnthropometryService anthropometryService;

	@Override
	public Examination createExamination(ExaminationDto examinationDto) {
		AnthropometryDto anthropometryDto = new AnthropometryDto();
		BeanUtils.copyProperties(examinationDto, anthropometryDto);
		Anthropometry savedAnthropometry = anthropometryService.createAnthropometry(anthropometryDto);
		if (savedAnthropometry == null) {
			throw new RuntimeException("Failed to create anthropometry");
		}
		Examination examination = new Examination();
		BeanUtils.copyProperties(examinationDto, examination);
		examination.setAnthropometryId(savedAnthropometry.getId());
		return examinationRepository.save(examination);
	}

	@Override
	public ExaminationDto getExaminationById(Long id) {
		Optional<Examination> optionalExamination = examinationRepository.findById(id);

		if (optionalExamination.isPresent()) {
			Examination examination = optionalExamination.get();
			Long anthropometryID = examination.getAnthropometryId();
			Optional<Anthropometry> optionalAnthropometry = anthropometryService.getAnthropometryById(anthropometryID);

			if (optionalAnthropometry.isPresent()) {
				Anthropometry anthropometry = optionalAnthropometry.get();
				ExaminationDto examinationDTO = new ExaminationDto();
				BeanUtils.copyProperties(examination, examinationDTO);
				BeanUtils.copyProperties(anthropometry, examinationDTO);
				return examinationDTO;
			} else {
				throw new RuntimeException("Anthropometry not found for Examination ID: " + id);
			}
		} else {
			throw new RuntimeException("Examination not found with ID: " + id);
		}
	}

	@Override
	public List<ExaminationDto> getExaminationByChildId(String childId) {
		List<Examination> examinations = examinationRepository.findByChildId(childId);
		if (!examinations.isEmpty()) {
			List<ExaminationDto> examinationDtos = new ArrayList<>();
			for (Examination examination : examinations) {
				Long anthropometryID = examination.getAnthropometryId();
				Optional<Anthropometry> optionalAnthropometry = anthropometryService
						.getAnthropometryById(anthropometryID);
				if (optionalAnthropometry.isPresent()) {
					Anthropometry anthropometry = optionalAnthropometry.get();
					ExaminationDto examinationDTO = new ExaminationDto();
					BeanUtils.copyProperties(examination, examinationDTO);
					BeanUtils.copyProperties(anthropometry, examinationDTO);
					examinationDtos.add(examinationDTO);
				} else {
					throw new RuntimeException("Anthropometry not found for ChildId: " + childId);
				}
			}
			return examinationDtos;
		} else {
			throw new RuntimeException("Examination not found for ChildId: " + childId);
		}
	}

	@Override
	public ExaminationDto getExaminationByAdmitId(Long admitId) {
		Optional<Examination> optionalExamination = examinationRepository.findByAdmitId(admitId);
		if (optionalExamination.isPresent()) {
			Examination examination = optionalExamination.get();
			Long anthropometryID = examination.getAnthropometryId();
			Optional<Anthropometry> optionalAnthropometry = anthropometryService.getAnthropometryById(anthropometryID);
			if (optionalAnthropometry.isPresent()) {
				Anthropometry anthropometry = optionalAnthropometry.get();
				ExaminationDto examinationDTO = new ExaminationDto();
				BeanUtils.copyProperties(examination, examinationDTO);
				BeanUtils.copyProperties(anthropometry, examinationDTO);
				return examinationDTO;
			} else {
				throw new RuntimeException("Anthropometry not found for Admit ID: " + admitId);
			}
		} else {
			throw new RuntimeException("Examination not found for Admit ID: " + admitId);
		}
	}

	@Override
	public Examination updateExamination(Long id, ExaminationDto examinationDTO) {
		Optional<Examination> optionalExamination = examinationRepository.findById(id);
		if (optionalExamination.isPresent()) {
			Examination examination = optionalExamination.get();
			Long anthropometryID = examination.getAnthropometryId();
			Optional<Anthropometry> optionalAnthropometry = anthropometryService.getAnthropometryById(anthropometryID);
			if (optionalAnthropometry.isPresent()) {
				Anthropometry anthropometry = optionalAnthropometry.get();
				if (!Objects.equals(anthropometry.getHeight(), examinationDTO.getHeight())
						|| !Objects.equals(anthropometry.getWeight(), examinationDTO.getWeight())) {
					BeanUtils.copyProperties(examinationDTO, anthropometry);
					AnthropometryDto anthropometryDto = new AnthropometryDto();
					BeanUtils.copyProperties(anthropometry, anthropometryDto);
					anthropometryService.updateAnthropometry(anthropometryID, anthropometryDto);
				}
			} else {
				throw new NoSuchElementException("Anthropometry with ID " + anthropometryID + " not found");
			}
			BeanUtils.copyProperties(examinationDTO, examination);
			examination.setId(id);
			return examinationRepository.save(examination);
		} else {
			throw new NoSuchElementException("Examination with ID " + id + " not found");
		}
	}

	@Override
	public boolean deleteExamination(Long id) {
		Optional<Examination> optionalExamination = examinationRepository.findById(id);
		if (optionalExamination.isPresent()) {
			examinationRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
