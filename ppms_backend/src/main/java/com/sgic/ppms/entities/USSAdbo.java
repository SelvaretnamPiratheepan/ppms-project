package com.sgic.ppms.entities;

import java.time.LocalDate;

import com.sgic.ppms.enums.Result;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "uss-adbo")
public class USSAdbo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "admit_id")
	private Admit admit;
	private LocalDate date;
	@Enumerated(EnumType.STRING)
	private Result result;
	private String details;

}
