package com.sgic.ppms.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CauseOfDeathDTO {

	@Pattern(regexp = "^[A-Za-z]+$", message = "alphabetic latter")
	private String name;

}
