
package com.sgic.ppms.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralConditionDto {
	private Long id;
	private Long admitId;

	@Pattern(regexp = "^[A-Za-z]", message = "Invalid General Condition")
	private String General_Condition;

	private Float temperature;

	private Integer resp;

	private Integer pulse;

	private Boolean doctorInformed;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime time;

}
