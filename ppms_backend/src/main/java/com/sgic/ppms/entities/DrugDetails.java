package com.sgic.ppms.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DrugDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "admit_id")
	private Admit admit;

	@ManyToOne
	@JoinColumn(name = "drug_id")
	private Drug drug;

	@ManyToOne
	@JoinColumn(name = "dose_id")
	private Dose dose;

	@ManyToOne
	@JoinColumn(name = "frequency_id")
	private Frequency frequency;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "drugAtDischarge")
	private Boolean drugAtDischarge;

	@Column(name = "numberOfDays")
	private int numberOfDays;

}
