
package com.sgic.ppms.services;

import java.util.List;

import com.sgic.ppms.dto.GeneralConditionDto;
import com.sgic.ppms.entities.GeneralCondition;

public interface GeneralConditionService {
	GeneralConditionDto createGeneralConditionFromDto(GeneralConditionDto generalConditionDto);

	List<GeneralCondition> getAllGeneralConditions();

	GeneralConditionDto getGeneralConditionByAdmitId(Long admitId);

	GeneralConditionDto updateGeneralCondition(Integer id, GeneralConditionDto generalConditionDto);
}
