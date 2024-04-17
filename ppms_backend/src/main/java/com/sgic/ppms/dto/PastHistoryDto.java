package com.sgic.ppms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PastHistoryDto {
	private int id;

	@NotNull(message = "Date cannot be null")
	private LocalDate date;

	@NotNull(message = "Child ID cannot be null")
	private String childId;
}
