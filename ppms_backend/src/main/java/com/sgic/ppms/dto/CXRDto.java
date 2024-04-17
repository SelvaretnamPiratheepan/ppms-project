package com.sgic.ppms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CXRDto {

	private Integer id;

	@NotNull(message = "Date cannot be null")
	private LocalDate date;

	@NotBlank(message = "Result cannot be blank")

	private String details;

	@NotNull(message = "Admit ID cannot be null")
	private Long admitId;

	@NotNull(message = "Result cannot be null")
	private Boolean result;
}
