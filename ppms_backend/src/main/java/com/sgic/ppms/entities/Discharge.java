package com.sgic.ppms.entities;

import java.time.LocalDate;

import com.sgic.ppms.enums.FollowUpType;
import com.sgic.ppms.enums.typeOfDischarge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "discharge")
@Getter
@Setter
public class Discharge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "Cause_Death_ID")
	private CauseOfDeath causeOfDeath;
	@OneToOne
	@JoinColumn(name = "Admit_ID")
	private Admit admit;
	@ManyToOne
	@JoinColumn(name = "Place_ID")
	private Place place;
	private typeOfDischarge typeOfDischarge;
	private LocalDate date;
	@Column(name = "discharge_when")
	private LocalDate when;
	private Boolean followUp;
	private FollowUpType FollowUpType;

}
