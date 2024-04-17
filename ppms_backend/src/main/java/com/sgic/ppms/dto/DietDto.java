package com.sgic.ppms.dto;

import lombok.Data;

@Data
public class DietDto {

	private Long id;
	private String childDetailId;
	private Long foodListId;
	private int quantity_for_week;

}