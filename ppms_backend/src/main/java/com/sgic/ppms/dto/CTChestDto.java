package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.sgic.ppms.enums.Result;

import lombok.Data;

@Data
public class CTChestDto {

	private long id;
	private LocalDate date;
	private String details;
	private Long admitId;
	private Result result;

}
