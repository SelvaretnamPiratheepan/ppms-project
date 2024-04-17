package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrineTestDto {

	private Long id;
	private Long admitId;
	private LocalDate date;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String albumin;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Integer puscells;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Integer rbs;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String epicells;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String cast;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String crystals;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String culture;
}
