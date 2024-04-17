package com.sgic.ppms.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LiverFunction")
public class LiverFunction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// private Long admitId;
	@ManyToOne
	@JoinColumn(name = "admit_id")
	private Admit admit;

	private LocalDate date;
	private Float sgpt;
	private Float sgot;
	private Float s_chole;
	private Float prot;
	private Float alb;
	private Float glob;
	private Float sbr;
	private Float alp;
	private Float rbs;

}
