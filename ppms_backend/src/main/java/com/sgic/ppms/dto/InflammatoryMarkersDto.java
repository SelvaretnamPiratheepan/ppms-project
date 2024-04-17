package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InflammatoryMarkersDto {
	@NotNull(message = ValidationMessages.NOT_NULL_FOREIGN_KEY)
	private Long admitId;

	private long id;

	@NotNull(message = ValidationMessages.INVALID_DATE)
	private LocalDate date;

	@JsonProperty("CRP")
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Double crp = 0.0;

	@JsonProperty("ESR")
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Double esr = 0.0;
}
