package com.sgic.ppms.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HaematologyDto {
	@NotNull(message = ValidationMessages.NOT_NULL_FOREIGN_KEY)
	private Long admitId;

	private Long id;
	@NotNull(message = ValidationMessages.NOT_NULL)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double Hb;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double Pit;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double WBC;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double Lym;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double Eos;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double PCV;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double MCV;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double MCH;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double MCHC;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double APTT;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double BTbyCT;

	@DecimalMin(value = "0.0", message = ValidationMessages.POSITIVE)
	private double PTbyINR;
}
