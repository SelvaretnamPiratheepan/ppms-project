package com.sgic.ppms.dto;

import com.sgic.ppms.enums.BloodGroup;
import com.sgic.ppms.enums.Gender;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChildDetailUpdateDto {

	private String childId;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String firstName;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String lastName;
	private String school;
	private String address;
	private Gender gender;
	private BloodGroup bloodGroup;
	private double childHeight;
	private double childWeight;
	private String generalPractitionerName;
	private String generalPractitionerPhoneNumber;
	private String generalPractitionerAddress;
	private long ethnicityId;

}
