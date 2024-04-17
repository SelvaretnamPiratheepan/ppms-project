package com.sgic.ppms.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.format.annotation.DateTimeFormat;

import com.sgic.ppms.entities.Place;
import com.sgic.ppms.enums.AdmitType;
import com.sgic.ppms.enums.Carer;
import com.sgic.ppms.util.ValidationMessages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AdmitDTO {
	private Long id;
	@NotNull(message = ValidationMessages.CHILD_ID_NOTNULL)
	private String childId;
	private Place place;
	@NotNull(message = ValidationMessages.NOT_NULL)
	private AdmitType admitType;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime time;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate transferDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;

	@NotNull(message = ValidationMessages.NOT_NULL)
	private Integer bht;
	private Carer carer;
	private String carerFirstName;
	private String carerLastName;
	private String relationship;
	@Pattern(regexp = "^(?:[0-9\\+]+)$", message = ValidationMessages.INVALID_PHONE_NUMBER)
	private String contactNo;
	private String occupation;

	public boolean isValidEnumValue(Enum<?> value) {
		return value != null && Arrays.stream(AdmitType.values()).anyMatch(enumValue -> enumValue.equals(value));
	}
}
