package com.sgic.ppms.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sgic.ppms.enums.AdmitType;
import com.sgic.ppms.enums.Carer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Admit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "childDetail_id")
	private ChildDetail childDetail;
	@ManyToOne
	@JoinColumn(name = "place_id")
	private Place place;
	@Enumerated(EnumType.STRING)
	private AdmitType admitType;
	private LocalTime time;
	private LocalDate transferDate;
	private LocalDate date;
	private Integer bht;
	@Enumerated(EnumType.STRING)
	private Carer carer;
	private String carerFirstName;
	private String carerLastName;
	private String relationship;
	private String contactNo;
	private String occupation;
}