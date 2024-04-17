package com.sgic.ppms.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BioChemistryDto {

	private Long id;
	@NotNull(message = ValidationMessages.ADMIT_NOTNULL)
	private Long admitId;
	@NotNull(message = ValidationMessages.DATE_NOTNULL + ValidationMessages.INVALID_DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;
	private float Na;
	private float K;
	private float BU;
	private float SCr;
	private float Cal;
	private float Mg;
}