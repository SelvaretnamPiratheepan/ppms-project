package com.sgic.ppms.entities;

import com.sgic.ppms.enums.GestationalAgeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "birth_history")
public class BirthHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "child_id")
	private String childId;

	@Column(name = "postnatal_history_id")
	private Long postnatalHistoryId;

	@Column(name = "antenatal_history")
	private String antenatalHistory;

	@Column(name = "mode_of_delivery")
	private String modeOfDelivery;

	@Column(name = "gestational_age")
	private int gestationalAge;

	@Enumerated(EnumType.STRING)
	@Column(name = "gestational_age_type")
	private GestationalAgeType gestationalAgeType;

	@Column(name = "birth_weight")
	private float birthWeight;
}
