package com.sgic.ppms.dto;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnthropometryDto {
	private Long id;

	@Positive(message = ValidationMessages.HEIGHT_NOTNULL + "Height" + ValidationMessages.POSITIVE + "\n")
	private double Height;

	@Positive(message = ValidationMessages.WEIGHT_NOTNULL + "Weight" + ValidationMessages.POSITIVE + "\n")
	private double Weight;
}