package com.sgic.ppms.dto;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DrugManagementPlanDTO {

	@NotNull(message = ValidationMessages.NOT_NULL_FOREIGN_KEY)
	private Long admitId;

	private long id;

	@NotBlank(message = ValidationMessages.EMPTY_FIELDS)
	private String drugManagementPlan;
}
