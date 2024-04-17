package com.sgic.ppms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodDto {
	private Long id;

	@NotBlank
	@Pattern(regexp = "^[A-Za-z]+$", message = "Invalid food name")

	private String name;

}