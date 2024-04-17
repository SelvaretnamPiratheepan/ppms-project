package com.sgic.ppms.dto;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DietAllergyDto {
	private Long id;

	@Positive(message = ValidationMessages.POSITIVE)
	private Integer exclusiveBreastfeedingWeek;

	@Positive(message = ValidationMessages.POSITIVE)
	private Integer formulaMilkWeek;

	@Positive(message = ValidationMessages.POSITIVE)
	private Integer weaningStartedWeek;

	@NotNull(message = ValidationMessages.NOT_NULL_FOREIGN_KEY)
	private String childId;

	@NotNull(message = ValidationMessages.NOT_NULL_FOREIGN_KEY)
	private Long allergyId;

}