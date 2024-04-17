package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.sgic.ppms.entities.VaccineDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VaccineDetailAndDueDateDTO {
	private VaccineDetails vaccineDetail;
	private LocalDate dueDate;
}
