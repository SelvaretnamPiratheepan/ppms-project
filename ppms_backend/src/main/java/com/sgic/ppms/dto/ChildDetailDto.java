package com.sgic.ppms.dto;

import java.time.LocalDate;

import com.sgic.ppms.entities.Ethnicity;
import com.sgic.ppms.enums.BloodGroup;
import com.sgic.ppms.enums.Gender;

import lombok.Data;

@Data
public class ChildDetailDto {

	private String childId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String school;
	private String address;
	private Gender gender;
	private BloodGroup bloodGroup;
	private double childHeight;
	private double childWeight;
	private double childBmi;
	private String generalPractitionerName;
	private String generalPractitionerPhoneNumber;
	private String generalPractitionerAddress;
	private Ethnicity ethnicity;

}
