package com.sgic.ppms.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DrugAllergyDto;
import com.sgic.ppms.entities.AllergyDetail;
import com.sgic.ppms.entities.Drug;
import com.sgic.ppms.entities.DrugAllergy;
import com.sgic.ppms.enums.RestApiResponseStatus;
import com.sgic.ppms.repositories.AllergyDetailRepository;
import com.sgic.ppms.repositories.DrugAllergyRepository;
import com.sgic.ppms.repositories.DrugRepository;
import com.sgic.ppms.util.ResponseWrapper;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.transaction.Transactional;

@Service
public class DrugAllergyServiceImpl implements DrugAllergyService {

	private final DrugAllergyRepository drugAllergyRepository;
	private final DrugRepository drugRepository;
	private final AllergyDetailRepository allergyDetailRepository;

	public DrugAllergyServiceImpl(DrugAllergyRepository drugAllergyRepository, DrugRepository drugRepository,
			AllergyDetailRepository allergyDetailRepository) {
		this.drugAllergyRepository = drugAllergyRepository;
		this.drugRepository = drugRepository;
		this.allergyDetailRepository = allergyDetailRepository;
	}

	public ResponseWrapper<List<DrugAllergy>> getAllDrugAllergies() {
		List<DrugAllergy> drugAllergies = drugAllergyRepository.findAll();
		return new ResponseWrapper<>(com.sgic.ppms.enums.RestApiResponseStatus.OK.getCode(),
				com.sgic.ppms.enums.RestApiResponseStatus.OK.getStatus(), drugAllergies);
	}

	public ResponseWrapper<DrugAllergy> getDrugAllergyById(long id) {
		DrugAllergy drugAllergy = drugAllergyRepository.findById(id).orElse(null);
		if (drugAllergy == null) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(),
					com.sgic.ppms.enums.RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		} else {
			return new ResponseWrapper<>(RestApiResponseStatus.OK.getCode(), RestApiResponseStatus.OK.getStatus(),
					drugAllergy);
		}
	}

	@Transactional
	public ResponseWrapper<DrugAllergy> createDrugAllergy(DrugAllergyDto drugAllergyDto) {
		if (drugAllergyDto.getDrugId() <= 0 || drugAllergyDto.getAllergyDetailId() <= 0) {
			return new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.CREATE_FAILED,
					null);
		}

		if (drugAllergyRepository.existsByDrugIdAndAllergyDetailId(drugAllergyDto.getDrugId(),
				drugAllergyDto.getAllergyDetailId())) {
			return new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.CREATE_FAILED,
					null);
		}

		Drug drug = drugRepository.findById(drugAllergyDto.getDrugId()).orElse(null);
		AllergyDetail allergyDetail = allergyDetailRepository.findById(drugAllergyDto.getAllergyDetailId())
				.orElse(null);

		if (drug == null || allergyDetail == null) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NOT_FOUND, null);
		}

		DrugAllergy drugAllergy = new DrugAllergy();
		drugAllergy.setDrug(drug);
		drugAllergy.setAllergyDetail(allergyDetail);

		drugAllergyRepository.save(drugAllergy);
		return new ResponseWrapper<>(RestApiResponseStatus.CREATED.getCode(), ValidationMessages.CREATE_SUCCESS,
				drugAllergy);
	}

	@Transactional
	public ResponseWrapper<DrugAllergy> updateDrugAllergy(Long id, DrugAllergyDto drugAllergyDto) {
		if (drugAllergyDto.getDrugId() <= 0 || drugAllergyDto.getAllergyDetailId() <= 0) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.UPDATE_FAILED,
					null);
		}

		if (drugAllergyRepository.existsByDrugIdAndAllergyDetailIdAndIdNot(drugAllergyDto.getDrugId(),
				drugAllergyDto.getAllergyDetailId(), id)) {
			return new ResponseWrapper<>(RestApiResponseStatus.FAILURE.getCode(), ValidationMessages.FAILED_UPDATE,
					null);
		}

		Drug existingDrug = drugRepository.findById(drugAllergyDto.getDrugId()).orElse(null);
		AllergyDetail existingAllergyDetail = allergyDetailRepository.findById(drugAllergyDto.getAllergyDetailId())
				.orElse(null);
		DrugAllergy existingDrugAllergy = drugAllergyRepository.findById(id).orElse(null);

		if (existingDrug == null || existingAllergyDetail == null || existingDrugAllergy == null) {
			return new ResponseWrapper<>(RestApiResponseStatus.NOT_FOUND.getCode(), ValidationMessages.NOT_FOUND, null);
		}

		existingDrugAllergy.setDrug(existingDrug);
		existingDrugAllergy.setAllergyDetail(existingAllergyDetail);

		drugAllergyRepository.save(existingDrugAllergy);
		return new ResponseWrapper<>(RestApiResponseStatus.UPDATED.getCode(), ValidationMessages.UPDATE_SUCCESS,
				existingDrugAllergy);
	}

	public ResponseWrapper<Void> deleteDrugAllergy(Long id) {

		if (!drugAllergyRepository.existsById(id)) {
			return new ResponseWrapper<>(com.sgic.ppms.enums.RestApiResponseStatus.NOT_FOUND.getCode(),
					com.sgic.ppms.enums.RestApiResponseStatus.NOT_FOUND.getStatus(), null);
		}

		drugAllergyRepository.deleteById(id);
		return new ResponseWrapper<>(com.sgic.ppms.enums.RestApiResponseStatus.DELETED.getCode(),
				com.sgic.ppms.enums.RestApiResponseStatus.DELETED.getStatus(), null);
	}
}
