
package com.sgic.ppms.entities;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class GeneralCondition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String General_Condition;

	private Float Temperature;

	private Integer Resp;

	private Integer Pulse;

	private Boolean doctorInformed;

	private LocalTime time;

	@OneToOne
	@JoinColumn(name = "admit_id")
	private Admit admit;

}
