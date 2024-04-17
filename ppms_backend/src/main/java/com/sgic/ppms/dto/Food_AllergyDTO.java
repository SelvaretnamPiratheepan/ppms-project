package com.sgic.ppms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Food_AllergyDTO {
	private int id;
	@NotBlank
	@Pattern(regexp = "^[A-Za-z]+$", message = "#{ErrorCodes.invalidFoodAllergyName}")
	private String name;

}
