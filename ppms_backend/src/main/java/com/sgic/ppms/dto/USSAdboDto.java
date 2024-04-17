package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.sgic.ppms.enums.Result;

import lombok.Data;

@Data
public class USSAdboDto {

	private Long id;
	private LocalDate date;
	private String details;
	private Long admitId;
	private Result result;

}
