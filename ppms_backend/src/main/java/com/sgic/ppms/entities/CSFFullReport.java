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
@Table(name = "CsfFullReport")
public class CSFFullReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "admit_id")
	private Admit admit;
	private LocalDate Date;
	private String appearance;
	private int cSF_sugar;
	private int plasma_sugar;
	private int polymorphonuclear_cells;
	private int lymphocytes;
	private int rBC;

}
