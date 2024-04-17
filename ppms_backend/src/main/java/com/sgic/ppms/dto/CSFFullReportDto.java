package com.sgic.ppms.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CSFFullReportDto {

	private Long id;
	private Long admitId;
	private LocalDate Date;
	private String appearance;
	private int cSF_sugar;
	private int plasma_sugar;
	private int polymorphonuclear_cells;
	private int lymphocytes;
	private int rBC;

}
