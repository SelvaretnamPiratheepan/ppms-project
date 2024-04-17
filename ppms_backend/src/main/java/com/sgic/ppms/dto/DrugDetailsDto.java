package com.sgic.ppms.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sgic.ppms.entities.Admit;
import com.sgic.ppms.entities.Dose;
import com.sgic.ppms.entities.Drug;
import com.sgic.ppms.entities.Frequency;
import com.sgic.ppms.entities.Route;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DrugDetailsDto {

	private long id;

	@NotNull(message = "Admit is required")
	private Admit admit;

	@NotNull(message = "Drug is required")
	private Drug drug;

	@NotNull(message = "Dose is required")
	private Dose dose;

	@NotNull(message = "Frequency is required")
	private Frequency frequency;

	@NotNull(message = "Route is required")
	private Route route;

	@NotNull(message = "Start Date is required")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@NotNull(message = "End Date is required")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	@NotNull(message = "Drug at Discharge is required")
	private Boolean drugAtDischarge;

	@NotNull(message = "Number of Days is required")
	private int numberOfDays;

}
