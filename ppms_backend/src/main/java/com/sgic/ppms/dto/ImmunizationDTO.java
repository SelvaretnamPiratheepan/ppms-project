package com.sgic.ppms.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImmunizationDTO {
	private Long id;

	@NotNull(message = "VaccineDetailsId can't be Null")
	private Long vaccineDetailsId;

	@NotBlank(message = "ChildID can't be Empty")
	private String childId;

	@NotNull(message = "DueDate can't be Null")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dueDate;

	private boolean status;
	private String reason;
	private String sideEffects;
}