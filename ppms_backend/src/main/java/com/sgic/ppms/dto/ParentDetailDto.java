package com.sgic.ppms.dto;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentDetailDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String childId;
	private String name;
	private String ContactNumber;
	private String Relationship;
	private Integer Age;
	private String Habits;
	private Boolean Illness;
	private String DetailsOfIllness;
	private String Occupation;
	private Long Salary;

}
