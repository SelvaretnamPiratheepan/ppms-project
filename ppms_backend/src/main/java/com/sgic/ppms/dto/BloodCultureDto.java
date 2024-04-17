package com.sgic.ppms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BloodCultureDto {
	private Long id;
	private String childDetailId;
	private LocalDate date;
	private String bloodCulture;

}
