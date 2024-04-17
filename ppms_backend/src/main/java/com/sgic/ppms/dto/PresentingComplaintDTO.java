package com.sgic.ppms.dto;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PresentingComplaintDTO {
	private Long id;
	private Long admitId;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String complaintName;
	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private Integer duration;
}
