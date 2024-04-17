package com.sgic.ppms.entities;

import com.sgic.ppms.enums.AgeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "vaccineDetails")
@Entity
public class VaccineDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int age;
	@Enumerated(EnumType.STRING)
	private AgeType ageType;
	@Column(unique = true)
	private String vaccineName;
	private String icdCode;

}