package com.sgic.ppms.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "radiology-other")
public class RadiologyOther {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private String results;
	@ManyToOne
	@JoinColumn(name = "othertestm_id")
	private OtherTestM otherTestM;
	@ManyToOne
	@JoinColumn(name = "admit_id")
	private Admit admit;

}
