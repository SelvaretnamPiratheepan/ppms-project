package com.sgic.ppms.dto;

import com.sgic.ppms.entities.EnvironmentalAllergy;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildEnvAllergyDto {
	private Long Id;
	@NotNull(message = ValidationMessages.NOT_NULL_FOREIGN_KEY)
	private Long AllergyDetailId;
	@NotNull(message = ValidationMessages.NOT_NULL_FOREIGN_KEY)
	private EnvironmentalAllergy envAllergy;
}
