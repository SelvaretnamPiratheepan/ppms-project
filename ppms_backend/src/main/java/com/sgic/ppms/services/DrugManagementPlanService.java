package com.sgic.ppms.services;

import com.sgic.ppms.dto.DrugManagementPlanDTO;

public interface DrugManagementPlanService {
	DrugManagementPlanDTO createDrugManagementPlan(DrugManagementPlanDTO drugManagementPlanDTO);

	DrugManagementPlanDTO updateDrugManagementPlan(long id, DrugManagementPlanDTO drugManagementPlanDTO);

	DrugManagementPlanDTO getDrugManagementPlanByAdmitid(Long admitId);

	DrugManagementPlanDTO getDrugManagementPlanById(Long Id);

	boolean admitFoundInDrugManDatabase(Long admitId);

	boolean drugManPlanExists(Long admitId);

	Long getAdmitIdbyId(long id);
}
