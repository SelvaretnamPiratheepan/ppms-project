package com.sgic.ppms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherTestDto {

	private Long Id;
	private LocalDate date;
	private String result;
	private Long admitId;
	private Long otherTestmId;

}
