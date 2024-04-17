package com.sgic.ppms.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgic.ppms.dto.DrugDetailsDto;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.Dose;
import com.sgic.ppms.entities.Drug;
import com.sgic.ppms.entities.DrugDetails;
import com.sgic.ppms.entities.Frequency;
import com.sgic.ppms.entities.Route;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.DoseRepository;
import com.sgic.ppms.repositories.DrugDetailsRepository;
import com.sgic.ppms.repositories.DrugRepository;
import com.sgic.ppms.repositories.FrequencyRepository;
import com.sgic.ppms.repositories.RouteRepository;

@Service
public class DrugDetailsServiceImpl implements DrugDetailsService {

	@Autowired
	private DrugDetailsRepository drugDetailsRepository;

	@Autowired
	private AdmitRepository admitRepository;

	@Autowired
	private DrugRepository drugRepository;

	@Autowired
	private DoseRepository doseRepository;

	@Autowired
	private FrequencyRepository frequencyRepository;

	@Autowired
	private RouteRepository routeRepository;

	public DrugDetails CreateDrugDetails(DrugDetailsDto drugDetailsDto) {

		if (Boolean.TRUE.equals(drugDetailsDto.getDrugAtDischarge())
				&& Objects.isNull(drugDetailsDto.getNumberOfDays())) {
			throw new RuntimeException("Number of days is required when drugAtDischarge is true");
		}

		List<DrugDetails> existingDrugDetails = drugDetailsRepository
				.findByAdmitIdAndStartDate(drugDetailsDto.getAdmit().getId(), drugDetailsDto.getStartDate());
		for (DrugDetails existing : existingDrugDetails) {
			if (existing.getDrug().getId() == drugDetailsDto.getDrug().getId()) {
				throw new RuntimeException("This drug has already been added with the same start date.");
			}
		}

		DrugDetails drugDetails = new DrugDetails();

		Admit admit = admitRepository.findById(drugDetailsDto.getAdmit().getId())
				.orElseThrow(() -> new RuntimeException("Admit not found"));
		Drug drug = drugRepository.findById(drugDetailsDto.getDrug().getId())
				.orElseThrow(() -> new RuntimeException("Drug not found"));
		Dose dose = doseRepository.findById(drugDetailsDto.getDose().getId())
				.orElseThrow(() -> new RuntimeException("Dose not found"));
		Route route = routeRepository.findById(drugDetailsDto.getRoute().getId())
				.orElseThrow(() -> new RuntimeException("Route not found"));
		Frequency frequency = frequencyRepository.findById(drugDetailsDto.getFrequency().getId())
				.orElseThrow(() -> new RuntimeException("Frequency not found"));

		drugDetails.setAdmit(admit);
		drugDetails.setDrug(drug);
		drugDetails.setDose(dose);
		drugDetails.setRoute(route);
		drugDetails.setFrequency(frequency);
		drugDetails.setStartDate(drugDetailsDto.getStartDate());
		drugDetails.setEndDate(drugDetailsDto.getEndDate());
		drugDetails.setDrugAtDischarge(drugDetailsDto.getDrugAtDischarge());
		drugDetails.setNumberOfDays(drugDetailsDto.getNumberOfDays());

		return drugDetailsRepository.save(drugDetails);
	}

	public Optional<DrugDetails> getDrugDetailsById(int admit_id) {
		return drugDetailsRepository.findById(admit_id);
	}

	public DrugDetails updateDrugDetails(int admit_id, DrugDetailsDto drugDetailsDto) {

		if (Objects.isNull(drugDetailsDto.getAdmit())) {
			throw new RuntimeException("Admit is required");
		}

		if (Objects.isNull(drugDetailsDto.getAdmit().getId())) {
			throw new RuntimeException("Admit ID is required");
		}

		if (Objects.isNull(drugDetailsDto.getDrug())) {
			throw new RuntimeException("Drug is required");
		}

		if (Objects.isNull(drugDetailsDto.getDrug().getId())) {
			throw new RuntimeException("Drug ID is required");
		}

		if (Objects.isNull(drugDetailsDto.getDose())) {
			throw new RuntimeException("Dose is required");
		}

		if (Objects.isNull(drugDetailsDto.getDose().getId())) {
			throw new RuntimeException("Dose ID is required");
		}

		if (Objects.isNull(drugDetailsDto.getRoute())) {
			throw new RuntimeException("Route is required");
		}

		if (Objects.isNull(drugDetailsDto.getRoute().getId())) {
			throw new RuntimeException("Route ID is required");
		}

		if (Objects.isNull(drugDetailsDto.getFrequency())) {
			throw new RuntimeException("Frequency is required");
		}

		if (Objects.isNull(drugDetailsDto.getFrequency().getId())) {
			throw new RuntimeException("Frequency ID is required");
		}

		if (Objects.isNull(drugDetailsDto.getStartDate())) {
			throw new RuntimeException("Start date is required");
		}

		if (Objects.isNull(drugDetailsDto.getEndDate())) {
			throw new RuntimeException("End date is required");
		}

		if (Objects.isNull(drugDetailsDto.getDrugAtDischarge())) {
			throw new RuntimeException("Drug at discharge is required");
		}

		if (Objects.isNull(drugDetailsDto.getNumberOfDays())) {
			throw new RuntimeException("Number of days is required");
		}

		Optional<DrugDetails> drugDetailsOptional = drugDetailsRepository.findById(admit_id);

		if (drugDetailsOptional.isPresent()) {
			DrugDetails drugDetails = drugDetailsOptional.get();

			Admit admit = admitRepository.findById(drugDetailsDto.getAdmit().getId())
					.orElseThrow(() -> new RuntimeException("Admit not found"));

			Drug drug = drugRepository.findById(drugDetailsDto.getDrug().getId())
					.orElseThrow(() -> new RuntimeException("Drug not found"));

			Dose dose = doseRepository.findById(drugDetailsDto.getDose().getId())
					.orElseThrow(() -> new RuntimeException("Dose not found"));

			Route route = routeRepository.findById(drugDetailsDto.getRoute().getId())
					.orElseThrow(() -> new RuntimeException("Route not found"));

			Frequency frequency = frequencyRepository.findById(drugDetailsDto.getFrequency().getId())
					.orElseThrow(() -> new RuntimeException("Frequency not found"));

			drugDetails.setAdmit(admit);
			drugDetails.setDrug(drug);
			drugDetails.setDose(dose);
			drugDetails.setRoute(route);
			drugDetails.setFrequency(frequency);
			drugDetails.setStartDate(drugDetailsDto.getStartDate());
			drugDetails.setEndDate(drugDetailsDto.getEndDate());
			drugDetails.setDrugAtDischarge(drugDetailsDto.getDrugAtDischarge());
			drugDetails.setNumberOfDays(drugDetailsDto.getNumberOfDays());

			return drugDetailsRepository.save(drugDetails);
		} else {
			return null;
		}
	}

}
