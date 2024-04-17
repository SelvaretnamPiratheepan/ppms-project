package com.sgic.ppms.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.sgic.ppms.enums.FollowUpType;
import com.sgic.ppms.enums.typeOfDischarge;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargeDto {
	private Long id;

	private Long causeOfDeathId;

	@NotNull
	private Long admitId;

	private Long placeId;

	@NotBlank
	private typeOfDischarge typeOfDischarge;
	private FollowUpType FollowUpType;

	@NotNull(message = "Date can't be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private Boolean followUp;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate when;

}
