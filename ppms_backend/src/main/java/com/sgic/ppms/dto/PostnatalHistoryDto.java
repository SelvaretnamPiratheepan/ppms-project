package com.sgic.ppms.dto;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostnatalHistoryDto {

	private Long id;

	@NotBlank(message = ValidationMessages.EMPTY_NAME)
	@Pattern(regexp = "^(?:[A-Za-z]+(?:\\s+[A-Za-z]+)*)?$", message = ValidationMessages.INVALID_NAME)
	private String name;
}