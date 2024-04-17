package com.sgic.ppms.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Diet_Allergy")
public class DietAllergy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer exclusiveBreastfeedingWeek;
	private Integer formulaMilkWeek;
	private Integer weaningStartedWeek;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "childDetail_id")
	private ChildDetail childDetail;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "allergyDetail_id")
	private AllergyDetail allergyDetail;

}