package com.sgic.ppms.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgic.ppms.dto.DrugManagementPlanDTO;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.DrugManagementPlan;
import com.sgic.ppms.repositories.AdmitRepository;
import com.sgic.ppms.repositories.DrugManagementPlanRepository;

@Service
public class DrugManagementPlanServiceImpl implements DrugManagementPlanService {

	@Autowired
	private DrugManagementPlanRepository drugManagementPlanRepository;

	@Autowired
	private AdmitRepository admitRepository;

	@Transactional
	public DrugManagementPlanDTO createDrugManagementPlan(DrugManagementPlanDTO drugManagementPlanDTO) {
		DrugManagementPlan drugManagementPlan = new DrugManagementPlan();
		drugManagementPlanDTO.setId(0L);
		BeanUtils.copyProperties(drugManagementPlanDTO, drugManagementPlan);
		Admit admit = admitRepository.findById(drugManagementPlanDTO.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + drugManagementPlanDTO.getAdmitId()));
		drugManagementPlan.setAdmit(admit);
		DrugManagementPlan savedDrugManagementPlan = drugManagementPlanRepository.save(drugManagementPlan);
		drugManagementPlanDTO.setId(savedDrugManagementPlan.getId());
		return drugManagementPlanDTO;
	}

	@Transactional
	public DrugManagementPlanDTO updateDrugManagementPlan(long id, DrugManagementPlanDTO drugManagementPlanDTO) {
		DrugManagementPlan existingDrugManagementPlan = drugManagementPlanRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Drug Management plan not found with id: " + id));
		BeanUtils.copyProperties(drugManagementPlanDTO, existingDrugManagementPlan);
		Admit admit = admitRepository.findById(drugManagementPlanDTO.getAdmitId()).orElseThrow(
				() -> new IllegalArgumentException("Admit not found with id: " + drugManagementPlanDTO.getAdmitId()));
		existingDrugManagementPlan.setAdmit(admit);
		existingDrugManagementPlan.setId(id);
		DrugManagementPlan updatedDrugManagementPlan = drugManagementPlanRepository.save(existingDrugManagementPlan);
		drugManagementPlanDTO.setId(updatedDrugManagementPlan.getId());
		return drugManagementPlanDTO;
	}

	@Transactional(readOnly = true)
	public DrugManagementPlanDTO getDrugManagementPlanByAdmitid(Long admitId) {

		DrugManagementPlan drugManagementPlan = drugManagementPlanRepository.findByAdmitId(admitId);

		if (drugManagementPlan == null) {
			throw new IllegalArgumentException("Drug Management plan not found with admit ID:" + admitId);
		}
		DrugManagementPlanDTO drugManagementPlanDTO = new DrugManagementPlanDTO();
		BeanUtils.copyProperties(drugManagementPlan, drugManagementPlanDTO);
		drugManagementPlanDTO.setAdmitId(admitId);
		return drugManagementPlanDTO;
	}

	@Transactional(readOnly = true)
	public DrugManagementPlanDTO getDrugManagementPlanById(Long Id) {
		DrugManagementPlan drugManagementPlan = drugManagementPlanRepository.findById(Id)
				.orElseThrow(() -> new IllegalArgumentException("Drug Management plan not found with id: " + Id));

		DrugManagementPlanDTO drugManagementPlanDTO = new DrugManagementPlanDTO();
		BeanUtils.copyProperties(drugManagementPlan, drugManagementPlanDTO);
		drugManagementPlanDTO.setAdmitId(drugManagementPlan.getAdmit().getId());
		return drugManagementPlanDTO;
	}

	public boolean admitFoundInDrugManDatabase(Long admitId) {
		return drugManagementPlanRepository.existsByAdmitId(admitId);
	}

	public boolean drugManPlanExists(Long id) {
		return drugManagementPlanRepository.existsById(id);
	}

	public Long getAdmitIdbyId(long id) {
		DrugManagementPlan drugManagementPlan = drugManagementPlanRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Drug Management plan not found with id: " + id));
		return drugManagementPlan.getAdmit().getId();
	}

}
