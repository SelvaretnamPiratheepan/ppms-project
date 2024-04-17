package com.sgic.ppms.dto;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ComplaintDTO {
	private Long id;
	@NotBlank(message = ValidationMessages.EMPTY_NAME)
	@Pattern(regexp = "^(?:[A-Za-z]+(?:\\s+[A-Za-z]+)*)?$", message = ValidationMessages.INVALID_NAME)
	private String name;
}
