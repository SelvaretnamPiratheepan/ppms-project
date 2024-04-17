package com.sgic.ppms.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgic.ppms.enums.BloodGroup;
import com.sgic.ppms.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Child_Details")
public class ChildDetail {

	@Id
	@JsonProperty("childId")
	private String childId;
	private String firstName;
	private String lastName;
	@Column(name = "date_of_birth", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	private String school;
	private String address;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	private BloodGroup bloodGroup;
	private Double childHeight;
	private Double childWeight;
	private Double childBmi;
	private String generalPractitionerName;
	private String generalPractitionerPhoneNumber;
	private String generalPractitionerAddress;

	@ManyToOne
	@JoinColumn(name = "Ethnicity_id")
	private Ethnicity ethnicity;

}
