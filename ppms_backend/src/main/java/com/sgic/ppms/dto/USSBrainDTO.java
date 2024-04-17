package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sgic.ppms.enums.Result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class USSBrainDTO {

	private long id;
	private long AdmitId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private Result result;
	private String details;
}
